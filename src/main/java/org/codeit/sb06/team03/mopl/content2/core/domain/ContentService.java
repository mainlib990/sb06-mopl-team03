package org.codeit.sb06.team03.mopl.content2.core.domain;

import org.springframework.stereotype.Service;

@Service
public class ContentService {

    private final ThumbnailStorageService thumbnailStorageService;

    public ContentService(ThumbnailStorageService thumbnailStorageService) {
        this.thumbnailStorageService = thumbnailStorageService;
    }

    public Content registerContent(ContentRegistration registration) {
        return Content.register(registration, this.thumbnailStorageService);
    }

    public Content deleteContent(Content content, ContentDeletion deletion) {
        return content.delete(deletion);
    }

    public Content updateContent(Content content, ContentUpdate update) {
        return content.update(update, this.thumbnailStorageService);
    }
}
