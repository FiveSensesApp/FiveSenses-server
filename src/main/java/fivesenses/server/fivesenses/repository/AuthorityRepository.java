package fivesenses.server.fivesenses.repository;

import fivesenses.server.fivesenses.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
