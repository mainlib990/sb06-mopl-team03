package org.codeit.sb06.team03.mopl.storage.infra;

import java.util.UUID;

public record StorageStartRequestedEvent(
        UUID id,
        String fileKey,
        String filePath,
        String contentType,
        long contentLength
) {
}
