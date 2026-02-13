package org.codeit.sb06.team03.mopl.content2.core.domain;

public record TagItem(String name) {

    public TagItem {
        name = name.trim();
    }
}
