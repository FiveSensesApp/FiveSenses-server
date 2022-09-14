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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Badge> findBadgeListByUserId(Long userId) {
        User user = userService.findById(userId);

        List<Badge> badgesPresent = userBadgeRepository.findListByUser(user).stream()
                .map(UserBadge::getBadge)
                .sorted(Comparator.comparingInt(Badge::getSequence))
                .collect(Collectors.toList());

        List<Badge> allBadges = badgeService.findAll().stream()
                .filter(Badge::getIsBefore)
                .sorted(Comparator.comparingInt(Badge::getSequence))
                .collect(Collectors.toList());

        for(Badge b : badgesPresent)
            allBadges.set(b.getSequence() - 1, b);

        return allBadges;
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
