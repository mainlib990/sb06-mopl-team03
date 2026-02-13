package org.codeit.sb06.team03.mopl.storage.domain.model;

import org.codeit.sb06.team03.mopl.storage.domain.IllegalStorageStateException;
import org.codeit.sb06.team03.mopl.storage.domain.StorageRecreationException;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.SequencedSet;

public class Storage {

    private final StorageId id;
    private StorageState state;
    private long attempts;
    private final StoragePayload payload;
    private long version;
    private final SequencedSet<StorageEvent> events = new LinkedHashSet<>();

    public Storage(
            StorageId id,
            StorageState state,
            long attempts,
            StoragePayload payload,
            long version
    ) {
        this.id = id;
        this.state = state;
        this.attempts = attempts;
        this.payload = payload;
        this.version = version;
    }

    public static Storage start(
            StorageId id,
            StoragePayload payload,
            StorageEventId eventId,
            StorageCorrelationEventId correlationEventId
    ) {
        Storage start = create(id, payload);
        var event = new StorageEvent.Started(
                eventId,
                start.id,
                correlationEventId,
                start.attempts,
                start.payload,
                start.version
        );
        start.events.add(event);
        return start;
    }

    private static Storage create(StorageId id, StoragePayload payload) {
        final StorageState state = StorageState.STARTED;
        final long attempts = 0;
        final long version = 0;
        return new Storage(id, state, attempts, payload, version);
    }

    public void process(
            long maxAttempts,
            StorageEventId storageEventId,
            StorageCorrelationEventId correlationEventId
    ) {
        if (!(state == StorageState.STARTED || state == StorageState.PROCESSING)) {
            throw new IllegalStorageStateException(id, state);
        }
        if (attempts > maxAttempts) {
            fail(storageEventId, correlationEventId);
            return;
        }
        process(storageEventId, correlationEventId);
    }

    private void fail(StorageEventId storageEventId, StorageCorrelationEventId correlationEventId) {
        Storage fail = fail(this);
        var event = new StorageEvent.Failed(
                storageEventId,
                this.id,
                correlationEventId,
                this.attempts,
                this.payload,
                this.version
        );
        fail.events.add(event);
    }

    private static Storage fail(Storage storage) {
        storage.state = StorageState.FAILED;
        storage.version += 1;
        return storage;
    }

    private void process(StorageEventId storageEventId, StorageCorrelationEventId correlationEventId) {
        Storage process = process(this);
        var event = new StorageEvent.Processing(
                storageEventId,
                this.id,
                correlationEventId,
                this.attempts,
                this.payload,
                this.version
        );
        process.events.add(event);
    }

    private static Storage process(Storage storage) {
        storage.state = StorageState.PROCESSING;
        storage.attempts += 1;
        storage.version += 1;
        return storage;
    }

    public static Storage rehydrate(SequencedSet<? extends StorageEvent> events) {
        if (events.isEmpty()) {
            throw StorageRecreationException.emptyHistory();
        }
        Storage storage = null;
        for (StorageEvent event : events) {
            storage = switch (event) {
                case StorageEvent.Started e -> rehydrate(e);
                case StorageEvent.Processing e -> rehydrate(storage, e);
                case StorageEvent.Failed e -> rehydrate(storage, e);
            };
        }
        return storage;
    }

    private static Storage rehydrate(StorageEvent.Started event) {
        final StorageId id = event.storageId();
        final StoragePayload payload = event.payload();
        long expectedVersion = event.version();
        Storage start = create(id, payload);
        if (expectedVersion != start.version) {
            throw StorageRecreationException.versionMismatch(start.id, expectedVersion, start.version);
        }
        return start;
    }

    private static Storage rehydrate(@Nullable Storage storage, StorageEvent.Processing event) {
        if (storage == null) {
            throw StorageRecreationException.notInitialized();
        }
        long expectedVersion = event.version();
        Storage process = process(storage);
        if (expectedVersion != process.version) {
            throw StorageRecreationException.versionMismatch(process.id, expectedVersion, process.version);
        }
        return process;
    }

    private static Storage rehydrate(@Nullable Storage storage, StorageEvent.Failed event) {
        if (storage == null) {
            throw StorageRecreationException.notInitialized();
        }
        long expectedVersion = event.version();
        Storage fail = fail(storage);
        if (expectedVersion != fail.version) {
            throw StorageRecreationException.versionMismatch(fail.id, expectedVersion, fail.version);
        }
        return fail;
    }

    public SequencedSet<StorageEvent> events() {
        return Collections.unmodifiableSequencedSet(new LinkedHashSet<>(events));
    }

    public void clearEvents() {
        events.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Storage that = (Storage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
