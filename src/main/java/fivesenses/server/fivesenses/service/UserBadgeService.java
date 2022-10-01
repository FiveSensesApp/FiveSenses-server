package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.dto.UserBadgeRequestDto;
import fivesenses.server.fivesenses.entity.*;
import fivesenses.server.fivesenses.repository.UserBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserBadgeService {

    private final BadgeService badgeService;
    private final PostService postService;
    private final UserService userService;
    private final UserBadgeRepository userBadgeRepository;

    public UserBadge findById(Long id) {
        return userBadgeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 UserBadge입니다."));
    }

    public List<UserBadge> findListByUserId(Long userId){
        return userBadgeRepository.findListByUserId(userId);
    }

    public List<Badge> findBadgeListByUserId(Long userId) {
        User user = userService.findById(userId);

        List<Badge> badgesPresent = userBadgeRepository.findListByUser(user).stream()
                .map(UserBadge::getBadge)
                .sorted(Comparator.comparingInt(Badge::getSeqNum))
                .collect(Collectors.toList());

        List<Badge> allBadges = badgeService.findAll().stream()
                .filter(Badge::getIsBefore)
                .sorted(Comparator.comparingInt(Badge::getSeqNum))
                .collect(Collectors.toList());

        for(Badge b : badgesPresent)
            allBadges.set(b.getSeqNum() - 1, b);

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
    public Long createUserBadge(User user, Badge badge) {
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

    //TDDO: 옵저버 패턴 적용 어떻게 하는지 찾기
    @Transactional
    public List<Badge> checkUpdates() {
        User user = userService.findUserFromToken();
        List<UserBadge> userBadges = findListByUserId(user.getId());
        Set<String> presentBadges = userBadges.stream()
                .map(m -> m.getBadge().getName())
                .collect(Collectors.toSet());

        List<Badge> insertedBadges = new ArrayList<>();

        List<Post> posts = postService.findListByUser(user);
        int postsSize = posts.size();
        if(!presentBadges.contains("첫 감각의 설렘")
                && postsSize > 0){
            Badge badge = badgeService.findById("1_첫 감각의 설렘.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        if(!presentBadges.contains("초급 감각자")
                && postsSize >= 10){
            Badge badge = badgeService.findById("4_초급 감각자.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        if(!presentBadges.contains("중급 감각자")
                && postsSize >= 50){
            Badge badge = badgeService.findById("5_중급 감각자.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        if(!presentBadges.contains("고급 감각자")
                && postsSize >= 100){
            Badge badge = badgeService.findById("6_고급 감각자.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        long withMemoCnt = posts.stream()
                .filter(p -> p.getContent() != null)
                .count();
        if(!presentBadges.contains("투머치토커")
                && withMemoCnt >= 50L){
            Badge badge = badgeService.findById("8_투머치토커.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        long noMemoCnt = postsSize - withMemoCnt;
        if(!presentBadges.contains("미니멀리스트")
                && noMemoCnt >= 50L){
            Badge badge = badgeService.findById("7_미니멀리스트.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        long ambiguousCnt = posts.stream()
                .filter(p -> p.getCategory() == Category.AMBIGUOUS)
                .count();
        if(!presentBadges.contains("물음표쟁이")
                && ambiguousCnt >= 30L){
            Badge badge = badgeService.findById("9_물음표쟁이.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        long sightCnt = posts.stream()
                .filter(p -> p.getCategory() == Category.SIGHT)
                .count();
        if(!presentBadges.contains("프로시각러")
                && sightCnt >= 30L){
            Badge badge = badgeService.findById("10_프로시각러.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        long hearingCnt = posts.stream()
                .filter(p -> p.getCategory() == Category.HEARING)
                .count();
        if(!presentBadges.contains("프로청각러")
                && hearingCnt >= 30L){
            Badge badge = badgeService.findById("11_프로청각러.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        long smellCnt = posts.stream()
                .filter(p -> p.getCategory() == Category.SMELL)
                .count();
        if(!presentBadges.contains("프로후각러")
                && smellCnt >= 30L){
            Badge badge = badgeService.findById("12_프로후각러.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        long tasteCnt = posts.stream()
                .filter(p -> p.getCategory() == Category.TASTE)
                .count();
        if(!presentBadges.contains("프로미각러")
                && tasteCnt >= 30L){
            Badge badge = badgeService.findById("13_프로미각러.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        long touchCnt = posts.stream()
                .filter(p -> p.getCategory() == Category.TOUCH)
                .count();
        if(!presentBadges.contains("프로촉각러")
                && touchCnt >= 30L){
            Badge badge = badgeService.findById("14_프로촉각러.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        if(!presentBadges.contains("프로감각러")
                && ambiguousCnt >= 30L
                && sightCnt >= 30L
                && hearingCnt >= 30L
                && smellCnt >= 30L
                && tasteCnt >= 30L
                && touchCnt >= 30L){
            Badge badge = badgeService.findById("15_프로감각러.svg");
            insertedBadges.add(badge);
            createUserBadge(user,badge);
        }

        return insertedBadges;
    }

    @Transactional
    public Badge checkShareBadge() {
        User user = userService.findUserFromToken();
        Badge badge = badgeService.findById("2_공유하는 기쁨.svg");

        boolean isPresent = userBadgeRepository.existsByBadge(badge);
        if(isPresent)
            throw new IllegalStateException("[공유하는 기쁨] 배지가 존재합니다.");

        createUserBadge(user, badge);
        return badge;
    }

    @Transactional
    public Badge checkThanksBadge() {
        User user = userService.findUserFromToken();
        Badge badge = badgeService.findById("3_개발진의 감사.svg");

        boolean isPresent = userBadgeRepository.existsByBadge(badge);
        if(isPresent)
            throw new IllegalStateException("[개발진의 감사] 배지가 존재합니다.");

        createUserBadge(user, badge);
        return badge;
    }

//    @Transactional
//    public void updateBadge(UserBadgeRequestDto dto) {
//        UserBadge userBadge = findByUserId(dto.getUserId());
//    }


}
