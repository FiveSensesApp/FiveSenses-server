package fivesenses.server.fivesenses.repository;

import fivesenses.server.fivesenses.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, String> {
}
