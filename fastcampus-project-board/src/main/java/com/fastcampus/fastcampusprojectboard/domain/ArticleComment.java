package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false) //optional로 필수 값으로 지정
    private Article article; //게시글 (ID)

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 댓글 본문

    @CreatedDate
    @Column(nullable = false)
    private LocalTime createdAt; //생성일시
    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createBy; //생성자
    @LastModifiedDate
    @Column(nullable = false)
    private LocalTime modifiedAt; //수정일시
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자

    protected ArticleComment() {}

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content){
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment)) return false;
        ArticleComment that = (ArticleComment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
