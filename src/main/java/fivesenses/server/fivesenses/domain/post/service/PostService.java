package fivesenses.server.fivesenses.domain.post.service;

import fivesenses.server.fivesenses.domain.user.service.UserService;
import fivesenses.server.fivesenses.domain.post.dto.PostRequestDto;
import fivesenses.server.fivesenses.domain.post.entity.Category;
import fivesenses.server.fivesenses.domain.post.entity.Post;
import fivesenses.server.fivesenses.domain.user.entity.User;
import fivesenses.server.fivesenses.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


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

    public Post findPostById(Long postId) {
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

    public Long findCountByParam(Long userId, Category category, Integer star, LocalDate createdDate) {
        User user = userService.findById(userId);

        if (category == null && star == null && createdDate == null)
            return postRepository.countByUser(user);
        if (category != null)
            return postRepository.countByUserAndCategory(user, category);
        if (star != null)
            return postRepository.countByUserAndStar(user, star);
        if (createdDate != null)
            return postRepository.countByUserAndCreatedDateBetween(user,
                    LocalDateTime.of(createdDate, LocalTime.of(0, 0, 0)),
                    LocalDateTime.of(createdDate, LocalTime.of(23, 59, 59))
            );

        return 0L;
    }

    public List<Post> listContainsKeywordAndContent(String query) {
        User user = userService.findUserFromToken();
        return postRepository.findListContaining(user, query, query);
    }

    public List<Post> findListByUser(User user) {
        return postRepository.findListByUser(user);
    }
}
