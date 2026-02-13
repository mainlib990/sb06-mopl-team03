package org.codeit.sb06.team03.mopl.content2.core.domain;

public record ContentRegistration(
        ContentType type,
        Title title,
        Description description,
        Tags tags,
        ThumbnailStorage thumbnailStorage,
        Requester registrar
) {
}
