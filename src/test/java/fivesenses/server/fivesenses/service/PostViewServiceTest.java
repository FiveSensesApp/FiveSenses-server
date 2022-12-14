package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.entity.Category;
import fivesenses.server.fivesenses.entity.Post;
import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostViewServiceTest {

    @InjectMocks

    private PostViewService postViewService;
    @Mock
    private static UserService userService;
    @Mock
    private static PostRepository postRepository;

    private static User user;
    private static Post post1, post2, post3, post4, post5;

    @BeforeAll
    static void setup() {
        user = User.builder()
                .id(1L)
                .nickname("nickname")
                .email("email")
                .build();

        //star: 5점 2개, 4점 2개, 3점 1개
        //category: AMBIGUOUS 1개, HEARING 2개, SIGHT 1개, SMELL 1개
        initPosts();
    }

    private static void initPosts() {
        post1 = Post.builder()
                .id(2L)
                .user(user)
                .category(Category.AMBIGUOUS)
                .content("content")
                .keyword("keyword")
                .star(5)
                .build();

        post2 = Post.builder()
                .id(3L)
                .user(user)
                .category(Category.HEARING)
                .content("content2")
                .keyword("keyword2")
                .star(5)
                .build();

        post3 = Post.builder()
                .id(4L)
                .user(user)
                .category(Category.HEARING)
                .content("content3")
                .keyword("keyword3")
                .star(4)
                .build();

        post4 = Post.builder()
                .id(5L)
                .user(user)
                .category(Category.SIGHT)
                .content("content4")
                .keyword("keyword4")
                .star(4)
                .build();

        post5 = Post.builder()
                .id(6L)
                .user(user)
                .category(Category.SMELL)
                .content("content5")
                .keyword("keyword5")
                .star(3)
                .build();
    }

    @Test
    void findSliceByUser_category_기준() {
        //given
        Long userId = 1L;
        Category category = Category.HEARING;
        Integer star = null;
        LocalDate createdDate = null;

        Pageable pageable = PageRequest.of(0, 10);
        given(postRepository.findSliceByUserAndCategory(any(), any(), any()))
                .willReturn(new SliceImpl<>(
                        List.of(post2, post3), pageable, false));

        //when
        Slice<Post> sliceByUser = postViewService.findSliceByUser(userId, category, star, createdDate, pageable);

        //then
        assertThat(sliceByUser.getContent().stream()
                .filter(p -> p.getCategory().equals(Category.HEARING))
                .count())
                .isEqualTo(2);
    }

    @Test
    void findSliceByUser_star_기준() {
        //given
        Long userId = 1L;
        Category category = null;
        Integer star = 4;
        LocalDate createdDate = null;

        Pageable pageable = PageRequest.of(0, 10);
        given(postRepository.findSliceByUserAndStar(any(), any(), any()))
                .willReturn(new SliceImpl<>(
                        List.of(post3, post4), pageable, false));
        //when
        Slice<Post> sliceByUser = postViewService.findSliceByUser(userId, category, star, createdDate, pageable);

        //then
        assertThat(sliceByUser.getContent().stream()
                .filter(p -> p.getStar() == 4)
                .count())
                .isEqualTo(2);
    }

    @Test
    void findSliceByUser_star_기준없음() {
        //given
        Long userId = 1L;
        Category category = null;
        Integer star = null;
        LocalDate createdDate = null;

        Pageable pageable = PageRequest.of(0, 10);
        given(postRepository.findSliceByUser(any(), any()))
                .willReturn(new SliceImpl<>(
                        List.of(post1, post2,post3,post4,post5), pageable, false));
        //when
        Slice<Post> sliceByUser = postViewService.findSliceByUser(userId, category, star, createdDate, pageable);

        //then
        assertThat(sliceByUser.getContent().size()).isEqualTo(5);
    }
}