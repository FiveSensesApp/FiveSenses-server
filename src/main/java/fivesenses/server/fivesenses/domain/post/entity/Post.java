package fivesenses.server.fivesenses.domain.post.entity;

import fivesenses.server.fivesenses.domain.user.entity.User;
import fivesenses.server.fivesenses.domain.post.dto.PostRequestDto;
import fivesenses.server.fivesenses.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String keyword;
    private Integer star;
    private String content;

    public void addUser(User user) {
        this.user = user;
    }

    public void update(PostRequestDto pr) {
        if (pr.getCategory() != null)
            category = pr.getCategory();
        if (pr.getKeyword() != null)
            keyword = pr.getKeyword();
        if (pr.getStar() != null)
            star = pr.getStar();
        if (pr.getContent() != null)
            content = pr.getContent();
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", category=" + category +
                ", keyword='" + keyword + '\'' +
                ", star=" + star +
                ", content='" + content + '\'' +
                '}';
    }
}
