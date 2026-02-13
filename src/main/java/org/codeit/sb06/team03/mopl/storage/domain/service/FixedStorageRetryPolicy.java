package org.codeit.sb06.team03.mopl.storage.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FixedStorageRetryPolicy implements StorageRetryPolicy {

    private final long maxAttempts;

    public FixedStorageRetryPolicy(@Value("${mopl.storage.max-attempts}") long maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Override
    public long maxAttempts() {
        return maxAttempts;
    }
}
