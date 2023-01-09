package fivesenses.server.fivesenses.repository;

import fivesenses.server.fivesenses.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}