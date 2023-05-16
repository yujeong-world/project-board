package com.fastcampus.fastcampusprojectboard.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleComment is a Querydsl query type for ArticleComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleComment extends EntityPathBase<ArticleComment> {

    private static final long serialVersionUID = 287420979L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleComment articleComment = new QArticleComment("articleComment");

    public final QArticle article;

    public final StringPath content = createString("content");

    public final StringPath createBy = createString("createBy");

    public final TimePath<java.time.LocalTime> createdAt = createTime("createdAt", java.time.LocalTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final TimePath<java.time.LocalTime> modifiedAt = createTime("modifiedAt", java.time.LocalTime.class);

    public final StringPath modifiedBy = createString("modifiedBy");

    public QArticleComment(String variable) {
        this(ArticleComment.class, forVariable(variable), INITS);
    }

    public QArticleComment(Path<? extends ArticleComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleComment(PathMetadata metadata, PathInits inits) {
        this(ArticleComment.class, metadata, inits);
    }

    public QArticleComment(Class<? extends ArticleComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new QArticle(forProperty("article")) : null;
    }

}
