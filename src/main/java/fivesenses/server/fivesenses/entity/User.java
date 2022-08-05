package fivesenses.server.fivesenses.entity;

import fivesenses.server.fivesenses.entity.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String password;
    private String nickname;
    private Boolean isAlarmOn;
    private LocalDateTime alarmDate;
    private String email;
    private String emailValidCode;


    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserAuthority> userAuthorityList = new ArrayList<>();

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePw(String password) {
        this.password = password;
    }
    public void changeEmailValidCode(String emailValidCode) {
        this.emailValidCode = emailValidCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isAlarmOn=" + isAlarmOn +
                ", alarmDate=" + alarmDate +
                ", email='" + email + '\'' +
                ", emailValidCode='" + emailValidCode + '\'' +
                ", userAuthorityList=" + userAuthorityList +
                '}';
    }
}