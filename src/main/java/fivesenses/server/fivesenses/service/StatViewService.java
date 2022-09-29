package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.dto.CountByDayDto;
import fivesenses.server.fivesenses.dto.CountByMonthDto;
import fivesenses.server.fivesenses.dto.MonthlyMostCategoryDto;
import fivesenses.server.fivesenses.dto.StatResponseDto;
import fivesenses.server.fivesenses.entity.Category;
import fivesenses.server.fivesenses.entity.Post;
import fivesenses.server.fivesenses.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatViewService {

    private final PostService postService;
    private final UserService userService;


    public StatResponseDto getStatViewByUser(Long userId) {
        final User user = userService.findById(userId);
        final List<Post> posts = postService.findListByUser(user);

        final int totalPost = posts.size();
        final Map<LocalDate, List<Post>> postsOfMonth = getPostsOfMonth(posts);

        final Map<Category, Integer> percentageOfCategory = getPercentageOfCategory(posts, totalPost);
        final List<MonthlyMostCategoryDto> monthlyMostCategoryDtos = getMonthlyMostCategoryDtos(postsOfMonth);
        final List<CountByDayDto> countByDayDtos = getCountByDayDtos(posts);
        final List<CountByMonthDto> countByMonthDtos = getCountByMonthDtos(postsOfMonth);

        return StatResponseDto.builder()
                .totalPost(totalPost)
                .percentageOfCategory(percentageOfCategory)
                .monthlyCategoryDtoList(monthlyMostCategoryDtos)
                .countByDayDtoList(countByDayDtos)
                .countByMonthDtoList(countByMonthDtos)
                .build();
    }



    private List<MonthlyMostCategoryDto> getMonthlyMostCategoryDtos(Map<LocalDate, List<Post>> postsOfMonth) {
        final List<MonthlyMostCategoryDto> monthlyMostCategoryDtos = new ArrayList<>();
        for (Map.Entry<LocalDate, List<Post>> e : postsOfMonth.entrySet()) {
            LocalDate localDate = e.getKey();
            List<Post> postList = e.getValue();

            final Map<Category, Long> countByCategory = countByCategory(postList);
            List<Category> categories = new ArrayList<>(Arrays.asList(Category.values()));
            Collections.sort(categories, (c1, c2) ->
                    Long.compare(countByCategory.get(c2), countByCategory.get(c1)
                    ));

            monthlyMostCategoryDtos.add(new MonthlyMostCategoryDto(localDate, categories.get(0)));
        }

        return monthlyMostCategoryDtos;
    }

    private Map<LocalDate, List<Post>> getPostsOfMonth(List<Post> posts) {
        final LocalDate nowDate = LocalDate.now();

        Map<LocalDate, List<Post>> postPerMonth = new TreeMap<>();
        for (int i = 0; i < 12; i++){
            LocalDate minusMonths = nowDate.minusMonths(i);
            postPerMonth.put(LocalDate.of(minusMonths.getYear(), minusMonths.getMonthValue(), 1), new ArrayList<>());
        }

        for (Post post : posts) {
            LocalDate createdDate = post.getCreatedDate().toLocalDate();
            LocalDate createdYearAndMonth =
                    LocalDate.of(createdDate.getYear(),createdDate.getMonthValue(), 1);

            if (!postPerMonth.containsKey(createdYearAndMonth))
                continue;

            postPerMonth.get(createdYearAndMonth).add(post);
        }

        return postPerMonth;
    }

    //TODO: 아래 메소드랑 코드중복. 클래스 타입을 파라미터로 줘서 해결해보기
    private List<CountByMonthDto> getCountByMonthDtos(Map<LocalDate, List<Post>> postsOfMonth) {
        final List<CountByMonthDto> countByMonthDtos = new ArrayList<>();
        for (Map.Entry<LocalDate, List<Post>> e : postsOfMonth.entrySet()) {
            LocalDate localDate = e.getKey();
            List<Post> postList = e.getValue();

            countByMonthDtos.add(new CountByMonthDto(localDate, (long) postList.size()));
        }

        return countByMonthDtos;
    }

    private List<CountByDayDto> getCountByDayDtos(List<Post> posts) {
        final Map<LocalDate, List<Post>> postsOfDay = getPostsOfDay(posts);

        final List<CountByDayDto> countByDayDtos = new ArrayList<>();
        for (Map.Entry<LocalDate, List<Post>> e : postsOfDay.entrySet()) {
            LocalDate localDate = e.getKey();
            List<Post> postList = e.getValue();

            countByDayDtos.add(new CountByDayDto(localDate, (long) postList.size()));
        }

        return countByDayDtos;

    }

    private Map<LocalDate, List<Post>> getPostsOfDay(List<Post> posts) {
        final LocalDate nowDate = LocalDate.now();

        Map<LocalDate, List<Post>> postPerDay = new TreeMap<>();
        for (int i = 0; i < 12; i++)
            postPerDay.put(nowDate.minusDays(i), new ArrayList<>());

        for (Post post : posts) {
            LocalDate createdDate = post.getCreatedDate().toLocalDate();

            if (!postPerDay.containsKey(createdDate))
                continue;

            postPerDay.get(createdDate).add(post);
        }

        return postPerDay;
    }

    private Map<Category, Integer> getPercentageOfCategory(List<Post> posts, int totalPost) {
        final Map<Category, Long> countByCategory = countByCategory(posts);

        Map<Category, Integer> percentageOfCategory = new HashMap<>();
        for (Map.Entry<Category, Long> e : countByCategory.entrySet()) {
            Category category = e.getKey();
            Long cnt = e.getValue();

            int percentage = (int) ((cnt / (double) totalPost) * 100);
            percentageOfCategory.put(category, percentage);
        }

        return percentageOfCategory;
    }

    private Map<Category, Long> countByCategory(List<Post> posts) {
        final Map<Category, Long> countByCategory = new HashMap<>();
        for (Category category : Category.values())
            countByCategory.put(category, 0L);

        for (Post p : posts) {
            countByCategory.put(p.getCategory(), countByCategory.get(p.getCategory()) + 1L);
        }

        return countByCategory;
    }

}