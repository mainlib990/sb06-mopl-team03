package org.codeit.sb06.team03.mopl.content.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

public record CursorRequestContentDto(
        @Schema(description = "콘텐츠 타입", allowableValues = {"movie", "tvSeries", "sport"})
        String typeEqual,

        @Schema(description = "검색 키워드")
        String keywordLike,

        @Schema(description = "태그 목록")
        List<String> tagsIn,

        @Schema(description = "커서")
        String cursor,

        @Schema(description = "보조 커서", format = "uuid")
        @UUID
        String idAfter,

        @Schema(description = "한 번에 가져올 개수", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer limit,

        @Schema(description = "정렬 방향", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"ASCENDING", "DESCENDING"})
        String sortDirection,

        @Schema(description = "정렬 기준", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"createdAt", "watcherCount, rate"})
        String sortBy
) {
}
