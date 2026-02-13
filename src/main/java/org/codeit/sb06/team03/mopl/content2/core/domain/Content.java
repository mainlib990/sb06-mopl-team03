package org.codeit.sb06.team03.mopl.content2.core.domain;

import org.springframework.lang.Nullable;

import java.util.*;

import static org.codeit.sb06.team03.mopl.content2.core.domain.ContentEvent.*;

public class Content {

    private final ContentId id;
    private final ContentType type;
    private Title title;
    private Description description;
    private Tags tags;
    private Thumbnail thumbnail;
    private final List<ContentEvent> events = new ArrayList<>();

    private Content(
            ContentId id,
            ContentType type,
            Title title,
            Description description,
            Tags tags,
            Thumbnail thumbnail
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.thumbnail = thumbnail;
    }

    public static Content register(ContentRegistration registration, ThumbnailStorageService thumbnailStorageService) {
        final ContentType type = registration.type();
        final Title title = registration.title();
        final Description description = registration.description();
        final Tags tags = registration.tags();
        final ThumbnailStorage thumbnailStorage = registration.thumbnailStorage();
        final Requester registrar = registration.registrar();
        return register(type, title, description, tags, thumbnailStorage, registrar, thumbnailStorageService);
    }

    private static Content register(
            ContentType type,
            Title title,
            Description description,
            Tags tags,
            ThumbnailStorage thumbnailStorage,
            Requester registrar,
            ThumbnailStorageService thumbnailStorageService
    ) {
        Thumbnail thumbnail = thumbnailStorageService.store(thumbnailStorage);
        ContentId id = ContentId.generate();
        var event = new ContentRegisteredEvent(id, type, title, description, tags, thumbnail, registrar);
        Content content = event.applyTo(null);
        content.events.add(event);
        return content;
    }

    static Content on(ContentRegisteredEvent event) {
        final Requester registrar = event.registrar();
        if (!registrar.hasAdminRole()) {
            throw accessDenied(registrar);
        }

        final ContentId id = event.id();
        final ContentType type = event.type();
        final Title title = event.title();
        final Description description = event.description();
        final Tags tags = event.tags();
        final Thumbnail thumbnail = event.thumbnail();
        return new Content(id, type, title, description, tags, thumbnail);
    }

    private static ContentAccessDeniedException accessDenied(Requester requester) {
        throw new ContentAccessDeniedException(requester.id());
    }

    public static Content recreate(SequencedSet<? extends ContentEvent> events) {
        if (events.isEmpty()) {
            throw ContentRecreationException.emptyHistory();
        }
        Content content = null;
        for (ContentEvent event : events) {
            content = event.applyTo(content);
        }
        return content;
    }

    public Content delete(ContentDeletion deletion) {
        final Requester deleter = deletion.deleter();
        return delete(deleter);
    }

    private Content delete(Requester deleter) {
        var event = new ContentDeletedEvent(this.id, deleter);
        Content content = event.applyTo(this);
        this.events.add(event);
        return content;
    }

    Content on(ContentDeletedEvent event) {
        final Requester deleter = event.deleter();
        if (!deleter.hasAdminRole()) {
            throw accessDenied(this.id, deleter);
        }
        return this;
    }

    public Content update(ContentUpdate update, ThumbnailStorageService thumbnailStorageService) {
        final Title title = update.title();
        final Description description = update.description();
        final Tags tags = update.tags();
        final ThumbnailStorage thumbnailStorage = update.thumbnailStorage();
        final Requester updater = update.updater();
        return update(title, description, tags, thumbnailStorage, updater, thumbnailStorageService);
    }

    private Content update(
            Title title,
            Description description,
            Tags tags,
            @Nullable ThumbnailStorage thumbnailStorage,
            Requester updater,
            ThumbnailStorageService thumbnailStorageService
    ) {
        Thumbnail thumbnail = null;
        if (thumbnailStorage != null) {
            thumbnail = thumbnailStorageService.store(thumbnailStorage);
        }
        var event = new ContentUpdatedEvent(this.id, title, description, tags, thumbnail, updater);
        Content content = event.applyTo(this);
        this.events.add(event);
        return content;
    }

    Content on(ContentUpdatedEvent event) {
        final Requester updater = event.updater();
        if (!updater.hasAdminRole()) {
            throw accessDenied(this.id, updater);
        }

        this.title = event.title();
        this.description = event.description();
        this.tags = event.tags();
        if (event.thumbnail() != null) {
            this.thumbnail = event.thumbnail();
        }
        return this;
    }

    private static ContentAccessDeniedException accessDenied(ContentId contentId, Requester requester) {
        return new ContentAccessDeniedException(contentId.value(), requester.id());
    }

    public List<ContentEvent> events() {
        return Collections.unmodifiableList(events);
    }

    public void clearEvents() {
        events.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
