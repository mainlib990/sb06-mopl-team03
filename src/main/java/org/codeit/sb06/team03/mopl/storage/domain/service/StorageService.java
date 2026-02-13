package org.codeit.sb06.team03.mopl.storage.domain.service;

import org.codeit.sb06.team03.mopl.storage.domain.model.*;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    private final FileUploadPort fileUploadPort;
    private final StorageRetryPolicy retryPolicy;

    public StorageService(FileUploadPort fileUploadPort, StorageRetryPolicy retryPolicy) {
        this.fileUploadPort = fileUploadPort;
        this.retryPolicy = retryPolicy;
    }

    public Storage start(StorageCorrelationEventId correlationEventId, StoragePayload payload) {
        StorageId id = StorageId.generate();
        StorageEventId storageEventId = StorageEventId.generate();
        return Storage.start(id, payload, storageEventId, correlationEventId);
    }

    public Storage process(StoragePayload payload, Storage storage, StorageCorrelationEventId correlationEventId) {
        fileUploadPort.upload(payload);
        long maxAttempts = retryPolicy.maxAttempts();
        StorageEventId storageEventId = StorageEventId.generate();
        storage.process(maxAttempts, storageEventId, correlationEventId);
        return storage;
    }
}
