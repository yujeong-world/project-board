package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.domain.QArticle;
import com.fastcampus.fastcampusprojectboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment>

{
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root){
        bindings.excludeUnlistedProperties(true); // 리스팅을 하지 않은 속성에 대해서는 검색이 안되도록 함
        //현재 QuerydslPredicateExecutor 이 기능을 통해 Article의 모든 필드에 대한 검색이 열려 있음, 선택적으로 검색이 가능하도록 함

        //including을 통해서 검색 원하는 필드를 넣어줌
        bindings.including(root.content, root.createdAt, root.createBy);

        //first 검색 파라미터를 1개만 받음
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); //쿼리문 like '%s{v}%'
        bindings.bind(root.createBy).first(StringExpression::containsIgnoreCase);
        /*bindings.bind(root.createdAt).first(DateTimeExpression::eq);*/
    }
}
