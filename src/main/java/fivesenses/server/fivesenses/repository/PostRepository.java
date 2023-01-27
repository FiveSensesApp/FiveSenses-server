package fivesenses.server.fivesenses.repository;

import fivesenses.server.fivesenses.entity.Category;
import fivesenses.server.fivesenses.entity.Post;
import fivesenses.server.fivesenses.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Slice<Post> findSliceByUser(User user, Pageable pageable);

    Slice<Post> findSliceByUserAndCategory(User user, Category category, Pageable pageable);

    Slice<Post> findSliceByUserAndStar(User user, Integer star, Pageable pageable);

    Slice<Post> findSliceByUserAndCreatedDate(User user, LocalDateTime createdDate, Pageable pageable);

    Slice<Post> findSliceByUserAndCreatedDateBetween(User user, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Long countByUserAndCategory(User user, Category category);

    Long countByUserAndStar(User user, Integer star);

    Long countByUserAndCreatedDateBetween(User user, LocalDateTime start, LocalDateTime end);

    void deleteAllByUser(User user);

//    List<Post> findByKeywordContaining(String query);

    List<Post> findByUserAndKeywordContaining(User user, String query);

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.user = :user AND (p.keyword LIKE %:keyword% OR p.content LIKE %:content%)")
    List<Post> findListContaining(@Param("user") User user, @Param("keyword") String keyword, @Param("content") String content);

    Long countByUser(User user);

    List<Post> findListByUser(User user);

    Boolean existsByUserAndCreatedDateBetween(User user, LocalDateTime start, LocalDateTime end);
}
