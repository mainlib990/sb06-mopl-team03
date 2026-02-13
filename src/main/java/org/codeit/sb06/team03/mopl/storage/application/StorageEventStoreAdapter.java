package org.codeit.sb06.team03.mopl.storage.application;

import jakarta.persistence.PersistenceException;
import org.codeit.sb06.team03.mopl.storage.domain.model.Storage;
import org.codeit.sb06.team03.mopl.storage.domain.model.StorageCorrelationEventId;
import org.codeit.sb06.team03.mopl.storage.domain.model.StorageId;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

import java.util.ConcurrentModificationException;
import java.util.Optional;

@Component
public class StorageEventStoreAdapter implements StorageEventStorePort {

    private final StorageRepository repository;
    private final StorageEventPublisher publisher;

    public StorageEventStoreAdapter(
            StorageRepository repository,
            StorageEventPublisher publisher
    ) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public boolean exists(StorageCorrelationEventId correlationEventId) {
        return repository.existsByCorrelationEventId(correlationEventId);
    }

    @Override
    public void save(Storage storage) {
        try {
            Storage saved = repository.save(storage);
            saved.events().forEach(publisher::publishEvent);
            saved.clearEvents();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException cve) {
                String constraintName = cve.getConstraintName();
                if (constraintName != null) {
                    switch (constraintName) {
                        case "storage_correlation_event_id_uindex" -> {
                            return;
                        }
                        case "storage_storage_id_version_uindex" -> throw new ConcurrentModificationException(cve);
                        default -> throw e;
                    }
                }
            }
            throw e;
        }
    }

    @Override
    public Optional<Storage> load(StorageId id) {
        return repository.findById(id);
    }
}
