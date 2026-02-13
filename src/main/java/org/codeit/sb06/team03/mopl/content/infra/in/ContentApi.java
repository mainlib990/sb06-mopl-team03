package org.codeit.sb06.team03.mopl.content.infra.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.hibernate.validator.constraints.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "콘텐츠 관리")
@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "401", description = "인증 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
public interface ContentApi {

    @Operation(summary = "콘텐츠 목록 조회 (커서 페이지네이션)")
    @ApiResponse(responseCode = "200", description = "성공")
    ResponseEntity<CursorResponseContentDto> getContents(@ParameterObject @Valid CursorRequestContentDto request);

    @Operation(summary = "[어드민] 콘텐츠 생성")
    @ApiResponse(responseCode = "201", description = "성공")
    @ApiResponse(responseCode = "403", description = "권한 오류")
    ResponseEntity<ContentDto> postContent(
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) @Valid ContentCreateRequest request,
            MultipartFile thumbnail
    );

    @Operation(summary = "콘텐츠 단건 조회")
    @ApiResponse(responseCode = "200", description = "성공")
    ResponseEntity<ContentDto> getContent(@UUID @Schema(format = "uuid") String contentId);

    @Operation(summary = "[어드민] 콘텐츠 삭제")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "403", description = "권한 오류")
    ResponseEntity<Void> deleteContent(@UUID @Schema(format = "uuid") String contentId);

    @Operation(summary = "[어드민] 콘텐츠 수정")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "403", description = "권한 오류")
    ResponseEntity<ContentDto> patchContent(
            @UUID @Schema(format = "uuid")String contentId,
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) @Valid ContentUpdateRequest request,
            @Nullable MultipartFile thumbnail
    );
}
