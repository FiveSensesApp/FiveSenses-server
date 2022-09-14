package fivesenses.server.fivesenses.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge {

    @Id
    @Column(name = "badge_id")
    private String id;

    private Integer seqNum;
    private String imgUrl;
    private String description;
    private String reqCondition;
    private String reqConditionShort;
    private Boolean isBefore;

    public void update(String id, Integer seqNum, String imgUrl, String description, String condition, String conditionShort, Boolean isBefore) {
        if(id != null) this.id = id;
        if (seqNum != null) this.seqNum = seqNum;
        if(imgUrl != null) this.imgUrl = imgUrl;
        if (description != null) this.description = description;
        if (condition != null) this.reqCondition = condition;
        if (conditionShort != null) this.reqConditionShort = conditionShort;
        if(isBefore != null) this.isBefore = isBefore;
    }
}
