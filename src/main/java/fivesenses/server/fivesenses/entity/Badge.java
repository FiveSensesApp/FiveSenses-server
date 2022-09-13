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

    public void update(String id, String imgUrl) {
        if(id != null) this.id = id;
        if(imgUrl != null) this.imgUrl = imgUrl;
    }
}
