package org.codeit.sb06.team03.mopl.storage.infra;

import org.codeit.sb06.team03.mopl.storage.application.StartStorageUseCase;
import org.codeit.sb06.team03.mopl.storage.domain.model.StorageCorrelationEventId;
import org.codeit.sb06.team03.mopl.storage.domain.model.StoragePayload;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Async
public class SpringStorageConsumer {

    private final StartStorageUseCase startStorageUseCase;

    public SpringStorageConsumer(StartStorageUseCase startStorageUseCase) {
        this.startStorageUseCase = startStorageUseCase;
    }

    @EventListener(StorageStartRequestedEvent.class)
    public void handle(StorageStartRequestedEvent event) {
        final UUID id = event.id();
        final String fileKey = event.fileKey();
        final String filePath = event.filePath();
        final String contentType = event.contentType();
        final long contentLength = event.contentLength();
        handle(id, fileKey, filePath, contentType, contentLength);
    }

    private void handle(
            UUID id,
            String fileKey,
            String filePath,
            String contentType,
            long contentLength
    ) {
        final StorageCorrelationEventId correlationEventId = new StorageCorrelationEventId(id);
        final StoragePayload payload = new StoragePayload(fileKey, filePath, contentType, contentLength);
        startStorageUseCase.start(correlationEventId, payload);
    }
}
