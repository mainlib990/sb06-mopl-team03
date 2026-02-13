package org.codeit.sb06.team03.mopl.storage.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.codeit.sb06.team03.mopl.storage.application.StorageRepository;
import org.codeit.sb06.team03.mopl.storage.domain.model.Storage;
import org.codeit.sb06.team03.mopl.storage.domain.model.StorageCorrelationEventId;
import org.codeit.sb06.team03.mopl.storage.domain.model.StorageId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Component
@Transactional(readOnly = true)
public class JpaStorageRepository implements StorageRepository {

    private final JpaStorageEventMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    public JpaStorageRepository(JpaStorageEventMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean existsByCorrelationEventId(StorageCorrelationEventId correlationEventId) {
        long count = entityManager.createQuery("""
                        SELECT COUNT(e)
                        FROM JpaStorageEvent e
                        WHERE e.correlationEventId = :correlationEventId
                        """, Long.class)
                .setParameter("correlationEventId", correlationEventId.value())
                .getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional
    public Storage save(Storage storage) {
        storage.events()
                .stream()
                .map(mapper::toJpaEvent)
                .forEachOrdered(entityManager::persist);
        return storage;
    }

    @Override
    public Optional<Storage> findById(StorageId id) {
        return entityManager.createQuery("""
                        SELECT e
                        FROM JpaStorageEvent e
                        WHERE e.storageId = :storageId
                        ORDER BY e.version
                        """, JpaStorageEvent.class)
                .setParameter("storageId", id.value())
                .getResultStream()
                .map(mapper::toDomainEvent)
                .collect(collectingAndThen(
                        toCollection(LinkedHashSet::new),
                        events -> Optional.of(Storage.rehydrate(events))
                ));
    }
}
