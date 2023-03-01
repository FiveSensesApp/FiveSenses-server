package fivesenses.server.fivesenses.domain.badge.repository;

import fivesenses.server.fivesenses.domain.badge.enitity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, String> {
}
