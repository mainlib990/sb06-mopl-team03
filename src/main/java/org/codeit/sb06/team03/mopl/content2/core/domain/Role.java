package org.codeit.sb06.team03.mopl.content2.core.domain;

import java.util.Map;

public enum Role {

    ADMIN(true), USER(false);

    private static final Map<String, Role> ROLES = Map.ofEntries(
            Map.entry(ADMIN.name(), ADMIN),
            Map.entry(USER.name(), USER)
    );

    private final boolean isAdmin;

    Role(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public static Role parse(String id) {
        Role role = ROLES.get(id);
        if (role == null) {
            throw new InvalidRoleException(id);
        }
        return role;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
