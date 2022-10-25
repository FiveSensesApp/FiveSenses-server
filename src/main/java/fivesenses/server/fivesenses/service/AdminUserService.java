package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.repository.PostRepository;
import fivesenses.server.fivesenses.repository.UserAuthorityRepository;
import fivesenses.server.fivesenses.repository.UserBadgeRepository;
import fivesenses.server.fivesenses.repository.UserRepository;
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
    public void deleteUser(Long userId){
        User user = userService.findById(userId);

        userBadgeRepository.deleteAllByUser(user);

        postRepository.deleteAllByUser(user);
        userAuthorityRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}
