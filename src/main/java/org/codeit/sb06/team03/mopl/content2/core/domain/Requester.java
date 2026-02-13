package org.codeit.sb06.team03.mopl.content2.core.domain;

import java.util.UUID;

public record Requester(UUID id, boolean hasAdminRole) {

    public Requester(UUID id, Role role) {
        this(id, role.isAdmin());
    }
}
