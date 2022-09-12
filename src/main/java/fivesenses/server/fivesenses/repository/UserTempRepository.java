package fivesenses.server.fivesenses.repository;

import fivesenses.server.fivesenses.entity.UserTemp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTempRepository extends JpaRepository<UserTemp, Long> {

    UserTemp findByEmail(String email);
}
