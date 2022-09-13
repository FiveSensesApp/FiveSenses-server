package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.dto.UserBadgeRequestDto;
import fivesenses.server.fivesenses.entity.Badge;
import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.entity.UserBadge;
import fivesenses.server.fivesenses.repository.UserBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserBadgeService {

    private final BadgeService badgeService;

    private final UserService userService;
    private final UserBadgeRepository userBadgeRepository;

    public UserBadge findById(Long id) {
        return userBadgeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 UserBadge입니다."));
    }

    public UserBadge findByUserId(Long userId){
        return userBadgeRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 UserBadge입니다."));
    }

    public List<UserBadge> findListByUserId(Long userId) {
        User user = userService.findById(userId);
        return userBadgeRepository.findListByUser(user);
    }

    @Transactional
    public Long createUserBadge(UserBadgeRequestDto dto) {
        User user = userService.findById(dto.getUserId());
        Badge badge = badgeService.findById(dto.getBadgeId());

        UserBadge userBadge = UserBadge.builder()
                .user(user)
                .badge(badge)
                .build();

        return userBadgeRepository.save(userBadge).getId();
    }

    @Transactional
    public void deleteUserBadge(Long id) {
        UserBadge userBadge = findById(id);
        userBadgeRepository.delete(userBadge);
    }

//    @Transactional
//    public void updateBadge(UserBadgeRequestDto dto) {
//        UserBadge userBadge = findByUserId(dto.getUserId());
//    }


}
