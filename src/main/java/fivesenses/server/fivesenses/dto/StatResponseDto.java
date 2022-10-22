package fivesenses.server.fivesenses.dto;

import fivesenses.server.fivesenses.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatResponseDto {

    private int totalPost;
    private Map<Category, Integer> percentageOfCategory = new HashMap<>();
    private Map<Category, Long> cntOfCategory = new HashMap<>();

    private List<MonthlyMostCategoryDto> monthlyCategoryDtoList = new ArrayList<>();
    private List<CountByDayDto> countByDayDtoList = new ArrayList<>();
    private List<CountByMonthDto> countByMonthDtoList = new ArrayList<>();


}
