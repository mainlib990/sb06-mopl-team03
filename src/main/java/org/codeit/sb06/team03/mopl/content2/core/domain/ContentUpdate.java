package org.codeit.sb06.team03.mopl.content2.core.domain;

import org.springframework.lang.Nullable;

public record ContentUpdate(
        Title title,
        Description description,
        Tags tags,
        @Nullable ThumbnailStorage thumbnailStorage,
        Requester updater
) {
}
