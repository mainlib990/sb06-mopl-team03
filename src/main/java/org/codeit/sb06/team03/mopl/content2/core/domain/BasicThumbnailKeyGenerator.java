package org.codeit.sb06.team03.mopl.content2.core.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BasicThumbnailKeyGenerator implements ThumbnailKeyGenerator {

    private final String namespace;

    public BasicThumbnailKeyGenerator(@Value("${mopl.content.thumbnail-namespace}") String namespace) {
        this.namespace = namespace;
    }

    @Override
    public ThumbnailKey generate() {
        ThumbnailId id = ThumbnailId.generate();
        return ThumbnailKey.of(this.namespace, id);
    }
}
