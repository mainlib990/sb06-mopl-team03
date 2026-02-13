package org.codeit.sb06.team03.mopl.storage.domain.model;

import java.util.UUID;

public record StorageId(UUID value) {

    public static StorageId generate() {
        return new StorageId(UUID.randomUUID());
    }
}
