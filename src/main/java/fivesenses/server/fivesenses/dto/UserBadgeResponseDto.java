package fivesenses.server.fivesenses.dto;

import fivesenses.server.fivesenses.entity.UserBadge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBadgeResponseDto {

    private Long userBadgeId;
    private Long userId;
    private String badgeId;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public UserBadgeResponseDto(UserBadge ub) {
        this.userBadgeId = ub.getId();
        this.userId = ub.getUser().getId();
        this.badgeId = ub.getBadge().getId();
        this.createDate = ub.getCreatedDate();
        this.modifiedDate = ub.getModifiedDate();
    }
}
