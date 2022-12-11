package fivesenses.server.fivesenses.dto;

import fivesenses.server.fivesenses.entity.Category;
import fivesenses.server.fivesenses.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    //    private Long id;
    private Category category;
    private String keyword;
    private Integer star;
    private String content;

    public Post toEntityExceptUser() {
        return Post.builder()
                .category(category)
                .keyword(keyword)
                .star(star)
                .content(content)
                .build();
    }
}
