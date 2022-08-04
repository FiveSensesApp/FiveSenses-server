package fivesenses.server.fivesenses.repository;

import fivesenses.server.fivesenses.entity.Post;
import fivesenses.server.fivesenses.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Slice<Post> findSliceByUser(User user, Pageable pageable);
}
