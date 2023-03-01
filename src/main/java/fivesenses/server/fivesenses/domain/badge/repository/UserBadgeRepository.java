package fivesenses.server.fivesenses.domain.badge.repository;

import fivesenses.server.fivesenses.domain.badge.enitity.Badge;
import fivesenses.server.fivesenses.domain.user.entity.User;
import fivesenses.server.fivesenses.domain.badge.enitity.UserBadge;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findListByUser(User user);

    @EntityGraph(attributePaths = ("badge"))
    List<UserBadge> findListByUserId(Long userId);

    boolean existsByBadge(Badge badge);

    void deleteAllByUser(User user);

    boolean existsByBadgeAndUser(Badge badge, User user);
}
