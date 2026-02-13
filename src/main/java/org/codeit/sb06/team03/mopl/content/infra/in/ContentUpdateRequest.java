package org.codeit.sb06.team03.mopl.content.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ContentUpdateRequest(
        @Schema(description = "콘텐츠 제목")
        String title,

        @Schema(description = "콘텐츠 설명")
        String description,

        @Schema(description = "콘텐츠 태그 목록")
        List<String> tags
) {
}
