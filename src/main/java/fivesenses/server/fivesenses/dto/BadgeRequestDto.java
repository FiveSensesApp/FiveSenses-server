package fivesenses.server.fivesenses.dto;

import fivesenses.server.fivesenses.entity.Badge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeRequestDto {

    private String id;
    private Integer sequence;
    private String imgUrl;
    private String description;
    private String condition;
    private String conditionShort;
    private Boolean isBefore;

    public Badge toEntityExceptId(){
        return Badge.builder()
                .sequence(sequence)
                .imgUrl(imgUrl)
                .description(description)
                .condition(condition)
                .conditionShort(conditionShort)
                .isBefore(isBefore)
                .build();
    }
}
