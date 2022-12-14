package fivesenses.server.fivesenses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostExistsByDateDto {

    private LocalDate date;
    private Boolean isPresent;
}
