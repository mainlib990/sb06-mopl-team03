package org.codeit.sb06.team03.mopl.storage.domain;

import org.codeit.sb06.team03.mopl.storage.domain.model.StorageId;

public class StorageRecreationException extends StorageException {

    private StorageRecreationException(String message) {
        super(message);
    }

    public static StorageRecreationException emptyHistory() {
        return new StorageRecreationException("History is empty.");
    }

    public static StorageRecreationException notInitialized() {
        return new StorageRecreationException("Storage not initialized.");
    }

    public static StorageRecreationException versionMismatch(StorageId id, long expectedVersion, long actualVersion) {
        return new StorageRecreationException(
                "Expected version %d for storage %s, but got %d.".formatted(expectedVersion, id, actualVersion)
        );
    }
}
