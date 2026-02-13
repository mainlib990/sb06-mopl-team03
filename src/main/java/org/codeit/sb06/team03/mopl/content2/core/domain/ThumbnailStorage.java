package org.codeit.sb06.team03.mopl.content2.core.domain;

import java.nio.file.Path;

public record ThumbnailStorage(
        Path thumbnailPath,
        String contentType,
        long contentLength
) {
}
