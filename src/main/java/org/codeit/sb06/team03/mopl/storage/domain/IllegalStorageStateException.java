package org.codeit.sb06.team03.mopl.storage.domain;

import org.codeit.sb06.team03.mopl.storage.domain.model.StorageId;
import org.codeit.sb06.team03.mopl.storage.domain.model.StorageState;

public class IllegalStorageStateException extends StorageException {

    public IllegalStorageStateException(StorageId id, StorageState state) {
        super("Illegal state: %s for storage: %s".formatted(state, id));
    }
}
