package org.codeit.sb06.team03.mopl.content2.core.domain.thumbnail;

import java.util.Objects;

public class Thumbnail {

    private final ThumbnailId id;
    private


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Thumbnail thumbnail = (Thumbnail) o;
        return Objects.equals(id, thumbnail.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
