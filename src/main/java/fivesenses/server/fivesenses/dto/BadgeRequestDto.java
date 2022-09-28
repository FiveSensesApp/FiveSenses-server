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
    private Integer seqNum;
    private String imgUrl;
    private String description;
    private String reqCondition;
    private String reqConditionShort;
    private Boolean isBefore;
    private String name;

    public Badge toEntityExceptId(){
        return Badge.builder()
                .seqNum(seqNum)
                .imgUrl(imgUrl)
                .description(description)
                .reqCondition(reqCondition)
                .reqConditionShort(reqConditionShort)
                .isBefore(isBefore)
                .name(name)
                .build();
    }
}
