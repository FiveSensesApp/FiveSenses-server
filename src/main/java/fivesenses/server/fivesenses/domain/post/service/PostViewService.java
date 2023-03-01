package fivesenses.server.fivesenses.domain.post.service;

import fivesenses.server.fivesenses.domain.user.service.UserService;
import fivesenses.server.fivesenses.domain.user.dto.PostExistsByDateDto;
import fivesenses.server.fivesenses.domain.post.entity.Category;
import fivesenses.server.fivesenses.domain.post.entity.Post;
import fivesenses.server.fivesenses.domain.user.entity.User;
import fivesenses.server.fivesenses.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostViewService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Slice<Post> findSliceByUser(Long userId, Category category, Integer star, LocalDate createdDate, Pageable pageable) {
        User user = userService.findById(userId);

        if (category != null)
            return postRepository.findSliceByUserAndCategory(user, category, pageable);
        if (star != null)
            return postRepository.findSliceByUserAndStar(user, star, pageable);
        if (createdDate != null){
            return postRepository.findSliceByUserAndCreatedDateBetween(
                    user,
                    LocalDateTime.of(createdDate, LocalTime.of(0, 0, 0)),
                    LocalDateTime.of(createdDate, LocalTime.of(23, 59, 59)),
                    pageable
            );
        }

        return postRepository.findSliceByUser(user, pageable);
    }

    public List<PostExistsByDateDto> findExistsListByCreatedDateBetween(LocalDate startDate, LocalDate endDate) {
        User user = userService.findUserFromToken();
        return markIfPostOfDatePresent(startDate, endDate, user);
    }

    private List<PostExistsByDateDto> markIfPostOfDatePresent(LocalDate startDate, LocalDate endDate, User user) {
        List<PostExistsByDateDto> postExistsByDateDtoList = new ArrayList<>();
        int cnt = 0;
        while (!startDate.plusDays(cnt).isEqual(endDate.plusDays(1))) {
            LocalDate cmpDate = startDate.plusDays(cnt);
            Boolean isPresent = postRepository.existsByUserAndCreatedDateBetween(user,
                    LocalDateTime.of(cmpDate.getYear(), cmpDate.getMonthValue(), cmpDate.getDayOfMonth(), 0, 0, 0),
                    LocalDateTime.of(cmpDate.getYear(), cmpDate.getMonthValue(), cmpDate.getDayOfMonth(), 23, 59, 59));

            postExistsByDateDtoList.add(new PostExistsByDateDto(cmpDate, isPresent));
            cnt++;
        }
        return postExistsByDateDtoList;
    }
}
