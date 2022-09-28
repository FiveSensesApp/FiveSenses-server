package fivesenses.server.fivesenses.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTemp {

    @Id
    @GeneratedValue
    @Column(name = "user_temp_id")
    private Long id;

    private String email;
    private String emailValidCode;


    public UserTemp(String email){
        this.email = email;
    }

    public void changeEmailValidCode(String emailValidCode){
        this.emailValidCode = emailValidCode;
    }

    @Override
    public String toString() {
        return "UserTemp{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", emailValidCode='" + emailValidCode + '\'' +
                '}';
    }
}
