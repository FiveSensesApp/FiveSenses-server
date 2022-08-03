package fivesenses.server.fivesenses.repository;

import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    UserAuthority findByUser(User user);

    void deleteAllByUser(User user);
}
