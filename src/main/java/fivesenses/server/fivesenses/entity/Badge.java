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

    private Integer sequence;
    private String imgUrl;
    private String description;
    private String condition;
    private String conditionShort;
    private Boolean isBefore;

    public void update(String id, Integer sequence, String imgUrl, String description, String condition, String conditionShort, Boolean isBefore) {
        if(id != null) this.id = id;
        if (sequence != null) this.sequence = sequence;
        if(imgUrl != null) this.imgUrl = imgUrl;
        if (description != null) this.description = description;
        if (condition != null) this.condition = condition;
        if (conditionShort != null) this.conditionShort = conditionShort;
        if(isBefore != null) this.isBefore = isBefore;
    }
}
