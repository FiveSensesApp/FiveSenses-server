package fivesenses.server.fivesenses.repository;

import fivesenses.server.fivesenses.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
