package fivesenses.server.fivesenses.domain.user.dto;

import fivesenses.server.fivesenses.domain.post.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatResponseDto {

    private int totalPost;
    private Map<Category, Double> percentageOfCategory;
    private Map<Category, Long> cntOfCategory;

    private List<MonthlyMostCategoryDto> monthlyCategoryDtoList;
    private List<CountByDayDto> countByDayDtoList;
    private List<CountByMonthDto> countByMonthDtoList;


}
