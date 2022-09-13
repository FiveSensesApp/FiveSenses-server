package fivesenses.server.fivesenses.dto;

import fivesenses.server.fivesenses.entity.Badge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeResponseDto {

    private String id;
    private String imgUrl;

    public BadgeResponseDto(Badge badge) {
        this.id = badge.getId();
        this.imgUrl = badge.getImgUrl();
    }
}
