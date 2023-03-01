package fivesenses.server.fivesenses.domain.post.dto;

import fivesenses.server.fivesenses.domain.post.entity.Category;
import fivesenses.server.fivesenses.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private Category category;
    private String keyword;
    private Integer star;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostResponseDto(Post p) {
        this.id = p.getId();
        this.category = p.getCategory();
        this.keyword = p.getKeyword();
        this.star = p.getStar();
        this.content = Objects.toString(p.getContent(), "");
        this.createdDate = p.getCreatedDate();
        this.modifiedDate = p.getModifiedDate();
    }
}
