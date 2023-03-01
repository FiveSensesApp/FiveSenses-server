package fivesenses.server.fivesenses.common.service;

import fivesenses.server.fivesenses.domain.user.service.UserService;
import fivesenses.server.fivesenses.domain.user.entity.User;
import fivesenses.server.fivesenses.domain.post.repository.PostRepository;
import fivesenses.server.fivesenses.domain.user.repository.UserAuthorityRepository;
import fivesenses.server.fivesenses.domain.badge.repository.UserBadgeRepository;
import fivesenses.server.fivesenses.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminUserService {

    private final UserService userService;

    private final PostRepository postRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final UserRepository userRepository;

    private final UserBadgeRepository userBadgeRepository;

    @Transactional
    public void deleteUser(Long userId) {
        User user = userService.findById(userId);

        userBadgeRepository.deleteAllByUser(user);

        postRepository.deleteAllByUser(user);
        userAuthorityRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}
