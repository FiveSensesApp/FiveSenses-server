package fivesenses.server.fivesenses.dto;

import fivesenses.server.fivesenses.entity.Badge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeResponseDto {

    private String id;
    private Integer sequence;
    private String imgUrl;
    private String description;
    private String condition;
    private String conditionShort;
    private Boolean isBefore;

    public BadgeResponseDto(Badge badge) {
        this.id = badge.getId();
        this.sequence = badge.getSequence();
        this.imgUrl = badge.getImgUrl();
        this.description = badge.getDescription();
        this.condition = badge.getCondition();
        this.conditionShort = badge.getConditionShort();
        this.isBefore = badge.getIsBefore();
    }
}
