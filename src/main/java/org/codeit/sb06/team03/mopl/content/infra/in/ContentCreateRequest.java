package org.codeit.sb06.team03.mopl.content.infra.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ContentCreateRequest(
        @Schema(description = "콘텐츠 타입")
        Type type,

        @Schema(description = "콘텐츠 제목")
        String title,

        @Schema(description = "콘텐츠 설명")
        String description,

        @Schema(description = "콘텐츠 태그 목록")
        List<String> tags
) {

        enum Type {
                @JsonProperty("movie")
                MOVIE,
                @JsonProperty("tvSeries")
                TV_SERIES,
                @JsonProperty("sport")
                SPORT
        }
}
