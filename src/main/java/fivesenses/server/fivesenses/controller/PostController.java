package fivesenses.server.fivesenses.controller;

import fivesenses.server.fivesenses.dto.*;
import fivesenses.server.fivesenses.entity.Category;
import fivesenses.server.fivesenses.entity.Post;
import fivesenses.server.fivesenses.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Result<PostResponseDto>> createPost(@RequestBody PostRequestDto postRequestDto,
                                                              UriComponentsBuilder b) {
        Long postId = postService.createPost(postRequestDto);

        UriComponents uriComponents =
                b.path("/posts/{postId}").buildAndExpand(postId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());

        Post post = postService.findPostById(postId);
        Result<PostResponseDto> result = new Result<>(new Meta(HttpStatus.CREATED.value()), new PostResponseDto(post));
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Result<PostResponseDto>> getPost(@PathVariable Long postId) {
        Post post = postService.findPostById(postId);

        Result<PostResponseDto> result = new Result<>(new Meta(HttpStatus.OK.value()), new PostResponseDto(post));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Result<PostResponseDto>> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        postService.updatePost(postId, requestDto);

        Post post = postService.findPostById(postId);
        Result<PostResponseDto> result = new Result<>(new Meta(HttpStatus.OK.value()), new PostResponseDto(post));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Result<Slice<PostResponseDto>>> getPosts(@RequestParam Long userId,
                                                                   @RequestParam(required = false) Category category,
                                                                   @RequestParam(required = false) Integer star,
                                                                   @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate createdDate,
                                                                   Pageable pageable) {
        Slice<PostResponseDto> postDtos = postService.findSliceByUser(userId, category, star, createdDate, pageable)
                .map(PostResponseDto::new);

        Result<Slice<PostResponseDto>> result = new Result<>(new Meta(HttpStatus.OK.value()), postDtos);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
