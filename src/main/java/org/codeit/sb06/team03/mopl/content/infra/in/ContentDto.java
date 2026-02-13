package org.codeit.sb06.team03.mopl.content.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

public record ContentDto(
        @Schema(description = "콘텐츠 ID")
        UUID id,

        @Schema(description = "콘텐츠 타입")
        String type,

        @Schema(description = "콘텐츠 제목")
        String title,

        @Schema(description = "콘텐츠 설명")
        String description,

        @Schema(description = "썸네일 이미지 URL")
        String thumbnailUrl,

        @Schema(description = "콘텐츠 태그 목록")
        List<String> tags,

        @Schema(description = "평균 평점")
        Double averageRating,

        @Schema(description = "리뷰 개수")
        Integer reviewCount,

        @Schema(description = "시청자 수")
        Long watcherCount
) {
}
