package fivesenses.server.fivesenses.repository;

import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findListByUser(User user);

    Optional<UserBadge> findByUserId(Long userId);
}
