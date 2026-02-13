package org.codeit.sb06.team03.mopl.storage.application;

import org.codeit.sb06.team03.mopl.storage.domain.model.StorageEvent;

public interface StorageEventPublisher {
    void publishEvent(StorageEvent event);
}
