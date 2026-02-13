package org.codeit.sb06.team03.mopl.content2.core.domain;

public record Title(String value) {

    public Title {
        value = value.trim();
    }
}
