package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.domain.post.service.PostService;
import fivesenses.server.fivesenses.domain.user.service.UserService;
import fivesenses.server.fivesenses.domain.post.dto.PostRequestDto;
import fivesenses.server.fivesenses.domain.post.entity.Category;
import fivesenses.server.fivesenses.domain.post.entity.Post;
import fivesenses.server.fivesenses.domain.user.entity.User;
import fivesenses.server.fivesenses.domain.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    private Post post;
    private User user;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .id(1L)
                .nickname("nickname")
                .email("email")
                .build();

        post = Post.builder()
                .id(2L)
                .user(user)
                .category(Category.AMBIGUOUS)
                .content("content")
                .keyword("keyword")
                .star(5)
                .build();
    }

    @Test
    void createPost() {
        //given
        given(userService.findUserFromToken()).willReturn(user);
        given(postRepository.save(any())).willReturn(post);

        //when
        postService.createPost(new PostRequestDto());

        //then
        then(postRepository).should(times(1)).save(any());
    }

    @Test
    void deletePost() {
        //given
        given(postRepository.findById(any())).willReturn(Optional.of(post));

        //when
        postService.deletePost(post.getId());

        //then
        then(postRepository).should(times(1)).delete(post);
    }

    @Test
    void updatePost() {
        //given
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .content("content2")
                .keyword("keyword2")
                .star(4)
                .build();
        given(postRepository.findById(any())).willReturn(Optional.of(post));

        //when
        postService.updatePost(post.getId(), postRequestDto);

        //then
        assertThat(post.getId()).isEqualTo(2L);
        assertThat(post.getCategory()).isEqualTo(Category.AMBIGUOUS);

        assertThat(post.getContent()).isEqualTo(postRequestDto.getContent());
        assertThat(post.getKeyword()).isEqualTo(postRequestDto.getKeyword());
        assertThat(post.getStar()).isEqualTo(postRequestDto.getStar());
    }
}