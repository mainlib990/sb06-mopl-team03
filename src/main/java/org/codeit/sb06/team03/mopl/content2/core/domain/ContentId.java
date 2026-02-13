package org.codeit.sb06.team03.mopl.content2.core.domain;

import java.util.UUID;

public record ContentId(UUID value) {

    public static ContentId generate() {
        return new ContentId(UUID.randomUUID());
    }
}
