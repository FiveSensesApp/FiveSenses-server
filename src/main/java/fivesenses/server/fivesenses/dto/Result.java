package fivesenses.server.fivesenses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Meta meta;
    private T data;

    public Result(Meta meta) {
        this.meta = meta;
    }
}
