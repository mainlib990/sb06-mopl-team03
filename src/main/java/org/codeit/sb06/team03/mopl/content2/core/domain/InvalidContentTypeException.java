package org.codeit.sb06.team03.mopl.content2.core.domain;

public class InvalidContentTypeException extends ContentException {

    public InvalidContentTypeException(String id) {
        super("Content Type not found with id: " + id);
    }
}
