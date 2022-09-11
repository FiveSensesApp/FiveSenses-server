package fivesenses.server.fivesenses.repository;

import fivesenses.server.fivesenses.entity.Category;
import fivesenses.server.fivesenses.entity.Post;
import fivesenses.server.fivesenses.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {

    Slice<Post> findSliceByUser(User user, Pageable pageable);

    Slice<Post> findSliceByUserAndCategory(User user, Category category, Pageable pageable);

    Slice<Post> findSliceByUserAndStar(User user, Integer star, Pageable pageable);

    Slice<Post> findSliceByUserAndCreatedDate(User user, LocalDateTime createdDate, Pageable pageable);

    Slice<Post> findSliceByUserAndCreatedDateBetween(User user, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Long countByUserAndCategory(User user, Category category);

    Long countByUserAndStar(User user, Integer star);

    Long countByUserAndCreatedDateBetween(User user, LocalDateTime start, LocalDateTime end);
}
