package fivesenses.server.fivesenses.dto;

import fivesenses.server.fivesenses.entity.User;
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
    private LocalDateTime alarmDate;
    private String email;
    private String emailValidCode;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public UserResponseDto(User u){
        id = u.getId();
        nickname = u.getNickname();
        isAlarmOn = u.getIsAlarmOn();
        alarmDate = u.getAlarmDate();
        email = u.getEmail();
        emailValidCode = u.getEmailValidCode();
        createdDate = u.getCreatedDate();
        modifiedDate = u.getModifiedDate();
    }

}
