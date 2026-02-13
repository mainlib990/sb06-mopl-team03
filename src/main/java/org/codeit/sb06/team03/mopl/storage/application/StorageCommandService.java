package org.codeit.sb06.team03.mopl.storage.application;

import org.codeit.sb06.team03.mopl.storage.domain.IllegalStorageStateException;
import org.codeit.sb06.team03.mopl.storage.domain.StorageRecreationException;
import org.codeit.sb06.team03.mopl.storage.domain.model.*;
import org.codeit.sb06.team03.mopl.storage.domain.service.StorageService;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.util.NoSuchElementException;

@Service
@Retryable(retryFor = RuntimeException.class)
public class StorageCommandService implements StartStorageUseCase {

    private final StorageService service;
    private final StorageEventStorePort eventStorePort;

    public StorageCommandService(StorageService service, StorageEventStorePort eventStorePort) {
        this.service = service;
        this.eventStorePort = eventStorePort;
    }

    @Override
    @Retryable(noRetryFor = {
            UncheckedIOException.class
    })
    public void start(StorageCorrelationEventId correlationEventId, StoragePayload payload) {
        if (eventStorePort.exists(correlationEventId)) {
            return;
        }
        Storage storage = service.start(correlationEventId, payload);
        eventStorePort.save(storage);
    }

    @EventListener({StorageEvent.Started.class, StorageEvent.Processing.class})
    @Async
    @Retryable(noRetryFor = {
            StorageRecreationException.class,
            NoSuchElementException.class,
            IllegalStorageStateException.class,
            UncheckedIOException.class
    })
    public void process(StorageEvent event) {
        final StorageCorrelationEventId correlationEventId = event.correlationEventId();
        final StorageId id = event.storageId();
        final StoragePayload payload = event.payload();
        process(correlationEventId, id, payload);
    }

    private void process(StorageCorrelationEventId correlationEventId, StorageId id, StoragePayload payload) {
        if (eventStorePort.exists(correlationEventId)) {
            return;
        }
        Storage storage = eventStorePort.load(id)
                .orElseThrow(() -> new NoSuchElementException("Storage not found for ID: " + id.value()));
        Storage processed = service.process(payload, storage, correlationEventId);
        eventStorePort.save(processed);
    }

    @EventListener(StorageEvent.Failed.class)
    @Async
    public void fail(StorageEvent.Failed event) {
        final StorageCorrelationEventId correlationEventId = event.correlationEventId();
        final StorageId id = event.storageId();
        fail(correlationEventId, id);
    }

    private void fail(StorageCorrelationEventId correlationEventId, StorageId id) {
        if (eventStorePort.exists(correlationEventId)) {
            return;
        }
        Storage storage = eventStorePort.load(id)
                .orElseThrow(() -> new NoSuchElementException("Storage not found for ID: " + id.value()));
    }
}
