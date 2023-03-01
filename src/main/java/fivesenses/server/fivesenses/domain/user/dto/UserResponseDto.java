package fivesenses.server.fivesenses.domain.user.dto;

import fivesenses.server.fivesenses.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String nickname;
    private Boolean isAlarmOn;
    private String alarmDate;
    private String email;
    private String emailValidCode;
    private String badgeRepresent;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Boolean isMarketingAllowed;

    public UserResponseDto(User u) {
        id = u.getId();
        nickname = u.getNickname();
        isAlarmOn = u.getIsAlarmOn();
        alarmDate = u.getAlarmDate();
        email = u.getEmail();
        emailValidCode = u.getEmailValidCode();
        badgeRepresent = u.getBadgeRepresent();
        createdDate = u.getCreatedDate();
        modifiedDate = u.getModifiedDate();
        isMarketingAllowed = u.getIsMarketingAllowed();
    }

}
