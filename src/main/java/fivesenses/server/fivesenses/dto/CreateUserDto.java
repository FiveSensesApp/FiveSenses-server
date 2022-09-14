package fivesenses.server.fivesenses.dto;

import fivesenses.server.fivesenses.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    private String password;
    private String nickname;
    private Boolean isAlarmOn;
    private String alarmDate;
    private String email;

    public User toEntityExceptId(){
        return User.builder()
                .password(password)
                .nickname(nickname)
                .isAlarmOn(isAlarmOn)
                .alarmDate(alarmDate)
                .email(email)
                .build();
    }
}
