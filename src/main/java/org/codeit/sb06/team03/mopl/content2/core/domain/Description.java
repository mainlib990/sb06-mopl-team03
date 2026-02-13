package org.codeit.sb06.team03.mopl.content2.core.domain;

public record Description(String value) {

    public Description {
        value = value.trim();
    }
}
