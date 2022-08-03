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

    public UserResponseDto(User user){
        id = user.getId();
        nickname = user.getNickname();
        isAlarmOn = user.getIsAlarmOn();
        alarmDate = user.getAlarmDate();
        email = user.getEmail();
        emailValidCode = user.getEmailValidCode();
        createdDate = user.getCreatedDate();
        modifiedDate = user.getModifiedDate();
    }

}
