package fivesenses.server.fivesenses.domain.user.dto;

import fivesenses.server.fivesenses.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    private String password;
    private String nickname;
    private Boolean isAlarmOn;
    private String alarmDate;
    private String email;
    private Boolean isMarketingAllowed;

    public User toEntityExceptId() {
        return User.builder()
                .password(password)
                .nickname(nickname)
                .isAlarmOn(isAlarmOn)
                .alarmDate(alarmDate)
                .email(email)
                .isMarketingAllowed(isMarketingAllowed)
                .build();
    }
}
