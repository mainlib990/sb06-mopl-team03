package org.codeit.sb06.team03.mopl.content2.core.domain;

import org.springframework.lang.Nullable;

public sealed interface ContentEvent {

    Content applyTo(@Nullable Content content);

    record ContentRegisteredEvent(
            ContentId id,
            ContentType type,
            Title title,
            Description description,
            Tags tags,
            Thumbnail thumbnail,
            Requester registrar
    ) implements ContentEvent {

        @Override
        public Content applyTo(@Nullable Content content) {
            if (content != null) {
                throw ContentRecreationException.alreadyInitialized();
            }
            return Content.on(this);
        }
    }

    record ContentDeletedEvent(ContentId id, Requester deleter) implements ContentEvent {

        @Override
        public Content applyTo(@Nullable Content content) {
            if (content == null) {
                throw ContentRecreationException.notInitialized();
            }
            return content.on(this);
        }
    }

    record ContentUpdatedEvent(
            ContentId id,
            Title title,
            Description description,
            Tags tags,
            @Nullable Thumbnail thumbnail,
            Requester updater
    ) implements ContentEvent {

        @Override
        public Content applyTo(@Nullable Content content) {
            if (content == null) {
                throw ContentRecreationException.notInitialized();
            }
            return content.on(this);
        }
    }
}
