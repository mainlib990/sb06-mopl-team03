package org.codeit.sb06.team03.mopl.storage.domain.model;

import java.util.UUID;

public record StorageEventId(UUID value) {

    public static StorageEventId generate() {
        return new StorageEventId(UUID.randomUUID());
    }
}
