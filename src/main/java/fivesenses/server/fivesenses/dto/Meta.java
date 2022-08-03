package fivesenses.server.fivesenses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meta {

    private String msg;
    private Integer code;

    public Meta(Integer code){
        this.code = code;
    }
}
