package fivesenses.server.fivesenses.domain.user.repository;

import fivesenses.server.fivesenses.domain.user.entity.UserTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTempRepository extends JpaRepository<UserTemp, Long> {

    UserTemp findByEmail(String email);
}