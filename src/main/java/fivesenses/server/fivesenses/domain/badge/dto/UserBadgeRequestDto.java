package fivesenses.server.fivesenses.domain.badge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBadgeRequestDto {

    private Long userId;
    private String badgeId;
}
