package org.codeit.sb06.team03.mopl.content2.core.domain;

import java.util.Map;

public enum ContentType {

    MOVIE, TV_SERIES, SPORT;

    private static final Map<String, ContentType> CONTENT_TYPES = Map.ofEntries(
            Map.entry("movie", MOVIE),
            Map.entry("tvSeries", TV_SERIES),
            Map.entry("sport", SPORT)
    );

    public static ContentType parse(String id) {
        ContentType contentType = CONTENT_TYPES.get(id);
        if (contentType == null) {
            throw new InvalidContentTypeException(id);
        }
        return contentType;
    }
}
