package org.codeit.sb06.team03.mopl.content2.core.application;

import org.codeit.sb06.team03.mopl.content2.core.domain.*;
import org.springframework.lang.Nullable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.UUID;

@Service
@Transactional
@Retryable
public class ContentCommandService implements CreateContentUseCase, DeleteContentUseCase, UpdateContentUseCase {

    private final ContentService service;
    private final SaveContentPort savePort;
    private final LoadContentPort loadPort;

    public ContentCommandService(ContentService service, SaveContentPort savePort, LoadContentPort loadPort) {
        this.service = service;
        this.savePort = savePort;
        this.loadPort = loadPort;
    }

    @Override
    public Content create(CreateContentCommand command) {
        final ContentType type = ContentType.parse(command.type());
        final var title = new Title(command.title());
        final var description = new Description(command.description());
        final Tags tags = Tags.of(command.tags().stream().map(TagItem::new).toArray(TagItem[]::new));
        final ThumbnailStorage thumbnailStorage = thumbnailStore(command.createThumbnailCommand());
        final Requester registrar = requester(command.requesterId(), command.requesterRole());
        return create(type, title, description, tags, thumbnailStorage, registrar);
    }

    private static ThumbnailStorage thumbnailStore(CreateThumbnailCommand command) {
        final Path thumbnailPath = command.thumbnailPath();
        final String contentType = command.contentType();
        final long contentLength = command.contentLength();
        return new ThumbnailStorage(thumbnailPath, contentType, contentLength);
    }

    private static Requester requester(UUID requesterId, String requesterRole) {
        return new Requester(requesterId, Role.parse(requesterRole));
    }

    private Content create(
            ContentType type,
            Title title,
            Description description,
            Tags tags,
            ThumbnailStorage thumbnailStorage,
            Requester registrar
    ) {
        var registration = new ContentRegistration(type, title, description, tags, thumbnailStorage, registrar);
        Content content = service.registerContent(registration);
        return savePort.save(content);
    }

    @Override
    public void delete(DeleteContentCommand command) {
        final UUID id = UUID.fromString(command.id());
        final Requester deleter = requester(command.requesterId(), command.requesterRole());
        delete(id, deleter);
    }

    private void delete(UUID id, Requester deleter) {
        Content content = loadPort.load(id);
        var contentDeletion = new ContentDeletion(deleter);
        Content deleted = service.deleteContent(content, contentDeletion);
        savePort.save(deleted);
    }

    @Override
    public Content update(UpdateContentCommand command) {
        final UUID id = UUID.fromString(command.id());
        final var title = new Title(command.title());
        final var description = new Description(command.description());
        final Tags tags = Tags.of(command.tags().stream().map(TagItem::new).toArray(TagItem[]::new));
        final ThumbnailStorage thumbnailStorage = command.createThumbnailCommand() == null ? null : thumbnailStore(command.createThumbnailCommand());
        final Requester updater = requester(command.requesterId(), command.requesterRole());
        return update(id, title, description, tags, thumbnailStorage, updater);
    }

    private Content update(
            UUID id,
            Title title,
            Description description,
            Tags tags,
            @Nullable ThumbnailStorage thumbnailStorage,
            Requester updater
    ) {
        Content content = loadPort.load(id);
        var update = new ContentUpdate(title, description, tags, thumbnailStorage, updater);
        Content updated = service.updateContent(content, update);
        return savePort.save(updated);
    }
}
