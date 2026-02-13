package org.codeit.sb06.team03.mopl.storage.domain.model;

public record StoragePayload(
        String fileKey,
        String filePath,
        String contentType,
        long contentLength
) {
}
