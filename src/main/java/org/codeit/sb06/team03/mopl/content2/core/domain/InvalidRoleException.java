package org.codeit.sb06.team03.mopl.content2.core.domain;

public class InvalidRoleException extends ContentException {

    public InvalidRoleException(String id) {
        super("Role not found with id: " + id);
    }
}
