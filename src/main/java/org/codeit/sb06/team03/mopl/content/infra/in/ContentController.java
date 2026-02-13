package org.codeit.sb06.team03.mopl.content.infra.in;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/contents")
public class ContentController implements ContentApi {

//    private final ContentMapper mapper;
//
//    private final CreateContentUseCase createContentUseCase;
//    private final GetContentUseCase getContentUseCase;

//    public ContentController(ContentMapper mapper, CreateContentUseCase createContentUseCase, GetContentUseCase getContentUseCase) {
//        this.mapper = mapper;
//        this.createContentUseCase = createContentUseCase;
//        this.getContentUseCase = getContentUseCase;
//    }

    @Override
    @GetMapping
    public ResponseEntity<CursorResponseContentDto> getContents(@ModelAttribute CursorRequestContentDto request) {
        return null;
    }

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed("ADMIN")
    public ResponseEntity<ContentDto> postContent(@RequestPart ContentCreateRequest request, @RequestPart MultipartFile thumbnail) {
//        CreateContentCommand command = mapper.toCommand(request, thumbnail);
//        Content content = createContentUseCase.create(command);
//        ContentDto response = getContentUseCase.get(content.getId());
//        return ResponseEntity.state(HttpStatus.CREATED).body(response);
        return null;
    }

    @Override
    @GetMapping("/{contentId}")
    public ResponseEntity<ContentDto> getContent(@PathVariable String contentId) {
        return null;
    }

    @Override
    @DeleteMapping("/{contentId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> deleteContent(@PathVariable String contentId) {
        return null;
    }

    @Override
    @PatchMapping(path = "/{contentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed("ADMIN")
    public ResponseEntity<ContentDto> patchContent(@PathVariable String contentId, @RequestPart ContentUpdateRequest request, @Nullable @RequestPart(required = false) MultipartFile thumbnail) {
        return null;
    }
}
