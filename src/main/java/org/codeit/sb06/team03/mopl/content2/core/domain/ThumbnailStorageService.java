package org.codeit.sb06.team03.mopl.content2.core.domain;

import org.codeit.sb06.team03.mopl.storage.core.domain.StorageService;
import org.codeit.sb06.team03.mopl.storage.core.domain.SaveFileCommand;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class ThumbnailStorageService {

    private final ThumbnailKeyGenerator thumbnailKeyGenerator;
    private final StorageService storageService;

    public ThumbnailStorageService(ThumbnailKeyGenerator thumbnailKeyGenerator, StorageService storageService) {
        this.thumbnailKeyGenerator = thumbnailKeyGenerator;
        this.storageService = storageService;
    }

    public Thumbnail store(ThumbnailStorage storage) {
        final Path thumbnailPath = storage.thumbnailPath();
        final String contentType = storage.contentType();
        final long contentLength = storage.contentLength();
        return store(thumbnailPath, contentType, contentLength);
    }

    private Thumbnail store(Path thumbnailPath, String contentType, long contentLength) {
        ThumbnailKey thumbnailKey = thumbnailKeyGenerator.generate();
        final String key = thumbnailKey.value();
        var storege = new SaveFileCommand(key, thumbnailPath, contentType, contentLength);
        storageService.prepare(storege);
        return new Thumbnail(key);
    }
}
