package org.codeit.sb06.team03.mopl.storage.domain.service;

import org.codeit.sb06.team03.mopl.storage.domain.model.StoragePayload;

public interface FileUploadPort {
    void upload(StoragePayload payload);
}
