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

    private String imgUrl;
    private String description;
    private String condition;
    private String conditionShort;

    public void update(String id, String imgUrl, String description, String condition, String conditionShort) {
        if(id != null) this.id = id;
        if(imgUrl != null) this.imgUrl = imgUrl;
        if (description != null) this.description = description;
        if (condition != null) this.condition = condition;
        if (conditionShort != null) this.conditionShort = conditionShort;

    }
}
