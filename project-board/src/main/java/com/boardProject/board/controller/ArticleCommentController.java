package com.boardProject.board.controller;


import com.boardProject.board.dto.UserAccountDto;
import com.boardProject.board.dto.request.ArticleCommentRequest;
import com.boardProject.board.dto.security.BoardPrincipal;
import com.boardProject.board.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;


    @PostMapping("/new")
    public String postNewArticleComment(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleCommentRequest articleCommentRequest){
        //댓글을 넣은 사람의 정보, 인증이 없는 상태에서 넣는 과정
        // TODO : 인증 정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));


        return "redirect:/articles/" + articleCommentRequest.articleId();
        //article아이디를 받아서 현재 만들어진 페이지로 리다이렉션
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            Long articleId
            ) {

        articleCommentService.deleteArticleComment(commentId,boardPrincipal.getUsername());

        return "redirect:/articles/" + articleId;
    }
}
