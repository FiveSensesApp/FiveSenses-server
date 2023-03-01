package fivesenses.server.fivesenses.domain.badge.dto;

import fivesenses.server.fivesenses.domain.badge.enitity.Badge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeResponseDto {

    private String id;
    private Integer seqNum;
    private String imgUrl;
    private String description;
    private String reqCondition;
    private String reqConditionShort;
    private Boolean isBefore;
    private String name;

    public BadgeResponseDto(Badge badge) {
        this.id = badge.getId();
        this.seqNum = badge.getSeqNum();
        this.imgUrl = badge.getImgUrl();
        this.description = badge.getDescription();
        this.reqCondition = badge.getReqCondition();
        this.reqConditionShort = badge.getReqConditionShort();
        this.isBefore = badge.getIsBefore();
        this.name = badge.getName();
    }
}
