package org.codeit.sb06.team03.mopl.storage.domain.model;

public sealed interface StorageEvent {

    StorageEventId id();

    StorageId storageId();

    StorageCorrelationEventId correlationEventId();

    long attempts();

    StoragePayload payload();

    long version();

    record Started(
            StorageEventId id,
            StorageId storageId,
            StorageCorrelationEventId correlationEventId,
            long attempts,
            StoragePayload payload,
            long version
    ) implements StorageEvent {
    }

    record Processing(
            StorageEventId id,
            StorageId storageId,
            StorageCorrelationEventId correlationEventId,
            long attempts,
            StoragePayload payload,
            long version
    ) implements StorageEvent {
    }

    record Failed(
            StorageEventId id,
            StorageId storageId,
            StorageCorrelationEventId correlationEventId,
            long attempts,
            StoragePayload payload,
            long version
    ) implements StorageEvent {
    }
}
