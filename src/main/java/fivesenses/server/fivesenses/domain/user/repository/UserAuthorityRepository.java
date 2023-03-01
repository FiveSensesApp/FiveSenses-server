package fivesenses.server.fivesenses.domain.user.repository;

import fivesenses.server.fivesenses.domain.user.entity.User;
import fivesenses.server.fivesenses.domain.user.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    UserAuthority findByUser(User user);

    void deleteByUser(User user);
}
