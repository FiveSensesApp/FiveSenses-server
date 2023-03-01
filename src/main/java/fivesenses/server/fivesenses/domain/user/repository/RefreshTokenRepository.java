package fivesenses.server.fivesenses.domain.user.repository;

import fivesenses.server.fivesenses.domain.user.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}