package org.codeit.sb06.team03.mopl.content2.core.domain;

public record ThumbnailKey(String namespace, ThumbnailId id) {

    public static ThumbnailKey of(String namespace, ThumbnailId id) {
        return new ThumbnailKey(namespace, id);
    }

    public String value() {
        return "%s/%s".formatted(namespace, id.value());
    }
}
