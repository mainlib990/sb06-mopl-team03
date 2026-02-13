package org.codeit.sb06.team03.mopl.storage.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codeit.sb06.team03.mopl.storage.domain.model.*;
import org.springframework.stereotype.Component;

import java.io.UncheckedIOException;

@Component
public class JpaStorageEventMapper {

    private final ObjectMapper objectMapper;

    public JpaStorageEventMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JpaStorageEvent toJpaEvent(StorageEvent event) {
        try {
            return new JpaStorageEvent()
                    .setId(event.id().value())
                    .setStorageId(event.storageId().value())
                    .setCorrelationEventId(event.correlationEventId().value())
                    .setEventType(event.getClass().getSimpleName())
                    .setAttempts(event.attempts())
                    .setPayload(objectMapper.writeValueAsString(event.payload()))
                    .setVersion(event.version());
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    public StorageEvent toDomainEvent(JpaStorageEvent event) {
        try {
            final var id = new StorageEventId(event.id());
            final var storageId = new StorageId(event.storageId());
            final var correlationEventId = new StorageCorrelationEventId(event.correlationEventId());
            final String eventType = event.eventType();
            final long attempts = event.attempts();
            final StoragePayload payload = objectMapper.readValue(event.payload(), StoragePayload.class);
            final long version = event.version();
            return toDomainEvent(id, storageId, correlationEventId, eventType, attempts, payload, version);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private StorageEvent toDomainEvent(
            StorageEventId id,
            StorageId storageId,
            StorageCorrelationEventId correlationEventId,
            String eventType,
            long attempts,
            StoragePayload payload,
            long version
    ) {
        return switch (eventType) {
            case "Started" -> new StorageEvent
                    .Started(id, storageId, correlationEventId, attempts, payload, version);
            case "Processing" -> new StorageEvent
                    .Processing(id, storageId, correlationEventId, attempts, payload, version);
            case "Failed" -> new StorageEvent
                    .Failed(id, storageId, correlationEventId, attempts, payload, version);
            default -> throw new IllegalArgumentException("Unknown event type: " + eventType);
        };
    }
}
