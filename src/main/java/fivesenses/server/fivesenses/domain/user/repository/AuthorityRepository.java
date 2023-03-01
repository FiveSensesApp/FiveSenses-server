package fivesenses.server.fivesenses.domain.user.repository;

import fivesenses.server.fivesenses.domain.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
