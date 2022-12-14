package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.entity.Post;
import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.entity.UserAuthority;
import fivesenses.server.fivesenses.entity.UserBadge;
import fivesenses.server.fivesenses.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class AdminUserServiceTest {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;
    @Autowired
    private UserBadgeRepository userBadgeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTempRepository userTemp;

    private User user;
    private UserBadge userBadge;
    private UserAuthority userAuthority;
    private Post post;

    @DisplayName("유저삭제시_순서가_지켜져야함")
    @Test
    void deleteUser() {
        //given
        saveEntities();
        //then
        assertIsPresent();
        //when
        adminUserService.deleteUser(user.getId());
        //then
        assertIsEmpty();
    }

    private void assertIsEmpty() {
        assertThat(userBadgeRepository.findById(userBadge.getId()))
                .isEmpty();
        assertThat(postRepository.findById(post.getId()))
                .isEmpty();
        assertThat(userAuthorityRepository.findById(userAuthority.getId()))
                .isEmpty();
        assertThat(userRepository.findById(user.getId()))
                .isEmpty();
    }

    private void assertIsPresent() {
        assertThat(userBadgeRepository.findById(userBadge.getId()))
                .isPresent();
        assertThat(postRepository.findById(post.getId()))
                .isPresent();
        assertThat(userAuthorityRepository.findById(userAuthority.getId()))
                .isPresent();
        assertThat(userRepository.findById(user.getId()))
                .isPresent();
    }

    private void saveEntities() {
        user = User.builder().build();
        userRepository.save(user);

        userAuthority = UserAuthority.builder()
                .user(user)
                .build();
        userAuthorityRepository.save(userAuthority);

        post = Post.builder()
                .user(user)
                .build();
        postRepository.save(post);

        userBadge = UserBadge.builder()
                .user(user)
                .build();
        userBadgeRepository.save(userBadge);
    }
}