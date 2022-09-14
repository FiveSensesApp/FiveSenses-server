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
    private String imgUrl;
    private String description;
    private String condition;
    private String conditionShort;

    public Badge toEntityExceptId(){
        return Badge.builder()
                .imgUrl(imgUrl)
                .description(description)
                .condition(condition)
                .conditionShort(conditionShort)
                .build();
    }
}
