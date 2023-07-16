package com.boardProject.fastcampusprojectboard.dto.request;

import com.boardProject.fastcampusprojectboard.dto.ArticleCommentDto;
import com.boardProject.fastcampusprojectboard.dto.UserAccountDto;

public record ArticleCommentRequest(
        Long articleId,
        String content
) {
    public static ArticleCommentRequest of(Long articleId, String content){
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto){
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }
}
