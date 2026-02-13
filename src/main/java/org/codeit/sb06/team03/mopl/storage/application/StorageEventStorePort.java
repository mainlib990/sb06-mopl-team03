package org.codeit.sb06.team03.mopl.storage.application;

import org.codeit.sb06.team03.mopl.storage.domain.model.Storage;
import org.codeit.sb06.team03.mopl.storage.domain.model.StorageCorrelationEventId;
import org.codeit.sb06.team03.mopl.storage.domain.model.StorageId;

import java.util.Optional;

public interface StorageEventStorePort {

    boolean exists(StorageCorrelationEventId correlationEventId);

    void save(Storage storage);

    Optional<Storage> load(StorageId id);
}
