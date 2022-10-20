package fivesenses.server.fivesenses.dto;

import fivesenses.server.fivesenses.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyMostCategoryDto {

    private LocalDate month;
    private String category;
    private Long cnt;
}
