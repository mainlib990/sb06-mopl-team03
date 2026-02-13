package org.codeit.sb06.team03.mopl.content2.core.domain;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.SequencedSet;
import java.util.stream.Collectors;

public record Tags(SequencedSet<TagItem> tags) {

    public static Tags of(TagItem... tags) {
        SequencedSet<TagItem> tagsSet = Arrays.stream(tags).collect(Collectors.toCollection(LinkedHashSet::new));
        return new Tags(tagsSet);
    }
}
