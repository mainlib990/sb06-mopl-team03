package org.codeit.sb06.team03.mopl.storage.infra;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "mopl_storage_events",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "storage_correlation_event_id_uindex",
                        columnNames = "correlation_event_id"
                ),
                @UniqueConstraint(
                        name = "storage_storage_id_version_uindex",
                        columnNames = {"storage_id", "version"}
                ),
        }
)
public class JpaStorageEvent {

    @NotNull
    @Id
    @Column(name = "id")
    private UUID id;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "storage_id")
    private UUID storageId;

    @NotNull
    @Column(name = "correlation_event_id")
    private UUID correlationEventId;

    @NotNull
    @Column(name = "event_type")
    private String eventType;

    @Column(name = "attempts")
    private long attempts;

    @NotNull
    @Lob
    @Column(name = "payload")
    private String payload;

    @Column(name = "version")
    private long version;

    public UUID id() {
        return id;
    }

    public JpaStorageEvent setId(UUID id) {
        this.id = id;
        return this;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public JpaStorageEvent setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UUID storageId() {
        return storageId;
    }

    public JpaStorageEvent setStorageId(UUID storageId) {
        this.storageId = storageId;
        return this;
    }

    public UUID correlationEventId() {
        return correlationEventId;
    }

    public JpaStorageEvent setCorrelationEventId(UUID correlationEventId) {
        this.correlationEventId = correlationEventId;
        return this;
    }

    public String eventType() {
        return eventType;
    }

    public JpaStorageEvent setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public long attempts() {
        return attempts;
    }

    public JpaStorageEvent setAttempts(long attempts) {
        this.attempts = attempts;
        return this;
    }

    public String payload() {
        return payload;
    }

    public JpaStorageEvent setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public long version() {
        return version;
    }

    public JpaStorageEvent setVersion(long version) {
        this.version = version;
        return this;
    }
}
