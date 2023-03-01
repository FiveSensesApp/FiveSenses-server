package fivesenses.server.fivesenses.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePwDto {
    private String ogPw;
    private String newPw;
}
