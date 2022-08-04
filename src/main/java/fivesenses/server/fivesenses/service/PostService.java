package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.dto.PostRequestDto;
import fivesenses.server.fivesenses.entity.Post;
import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public Long createPost(PostRequestDto postRequestDto) {
        Post post = postRequestDto.toEntityExceptUser();
        User user = userService.findUserFromToken();
        post.addUser(user);

        postRepository.save(post);

        return post.getId();
    }

    public Post findPostById(Long postId){
        return postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 포스트입니다."));
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = findPostById(postId);
        postRepository.delete(post);
    }
    @Transactional
    public void updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = findPostById(postId);
        post.update(postRequestDto);
    }
}
