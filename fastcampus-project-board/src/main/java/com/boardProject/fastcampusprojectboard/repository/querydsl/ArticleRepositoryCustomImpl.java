package com.boardProject.fastcampusprojectboard.repository.querydsl;

import com.boardProject.fastcampusprojectboard.domain.Article;
import com.boardProject.fastcampusprojectboard.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtag() {
        // 쿼리 dsl이 만든 Q 클래스 사용
        QArticle article = QArticle.article;

        //string으로 리턴 타입 일치 시켜줌
        return from(article)
                .distinct()
                .select(article.hashtag)
                .where(article.hashtag.isNotNull())
                .fetch();  // 널 값을 제외
    }


}
