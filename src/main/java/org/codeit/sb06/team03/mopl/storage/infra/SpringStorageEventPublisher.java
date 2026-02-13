package org.codeit.sb06.team03.mopl.storage.infra;

import org.codeit.sb06.team03.mopl.storage.application.StorageEventPublisher;
import org.codeit.sb06.team03.mopl.storage.domain.model.StorageEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringStorageEventPublisher implements StorageEventPublisher {

    private final ApplicationEventPublisher publisher;

    public SpringStorageEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publishEvent(StorageEvent event) {
        publisher.publishEvent(event);
    }
}
