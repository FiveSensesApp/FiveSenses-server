package fivesenses.server.fivesenses.dto;

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
    private LocalDateTime alarmDate;
}
