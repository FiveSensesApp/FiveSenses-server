package fivesenses.server.fivesenses.domain.badge.enitity;

import fivesenses.server.fivesenses.domain.user.entity.User;
import fivesenses.server.fivesenses.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBadge extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_badge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @Override
    public String toString() {
        return "UserBadge{" +
                "id=" + id +
                '}';
    }
}
