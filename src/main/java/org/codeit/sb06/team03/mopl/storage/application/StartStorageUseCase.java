package org.codeit.sb06.team03.mopl.storage.application;

import org.codeit.sb06.team03.mopl.storage.domain.model.StorageCorrelationEventId;
import org.codeit.sb06.team03.mopl.storage.domain.model.StoragePayload;

public interface StartStorageUseCase {
    void start(StorageCorrelationEventId correlationEventId, StoragePayload payload);
}
