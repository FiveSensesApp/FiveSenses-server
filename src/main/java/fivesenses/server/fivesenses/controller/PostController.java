package fivesenses.server.fivesenses.controller;

import fivesenses.server.fivesenses.dto.*;
import fivesenses.server.fivesenses.entity.Post;
import fivesenses.server.fivesenses.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping()
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

    @GetMapping
    public ResponseEntity<Result<PostResponseDto>> getPost(@RequestParam Long postId) {
        Post post = postService.findPostById(postId);

        Result<PostResponseDto> result = new Result<>(new Meta(HttpStatus.OK.value()), new PostResponseDto(post));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Result<Object>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        Result<Object> result = new Result<>(new Meta(HttpStatus.NO_CONTENT.value()));
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Result<PostResponseDto>> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        postService.updatePost(postId, requestDto);

        Post post = postService.findPostById(postId);
        Result<PostResponseDto> result = new Result<>(new Meta(HttpStatus.OK.value()), new PostResponseDto(post));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
