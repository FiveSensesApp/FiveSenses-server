package fivesenses.server.fivesenses.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private Long userId;
    private String nickname;
    private Boolean isAlarmOn;
    private String badgeRepresent;
    private String alarmDate;
    private Boolean isMarketingAllowed;
}
