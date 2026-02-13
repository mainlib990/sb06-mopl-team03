package org.codeit.sb06.team03.mopl.content2.core.domain.thumbnail;

import java.util.UUID;

public record ThumbnailId(UUID value) {

    public static ThumbnailId generate() {
        return new ThumbnailId(UUID.randomUUID());
    }
}
