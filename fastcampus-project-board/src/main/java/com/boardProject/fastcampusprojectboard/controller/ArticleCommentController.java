package com.boardProject.fastcampusprojectboard.controller;


import com.boardProject.fastcampusprojectboard.dto.UserAccountDto;
import com.boardProject.fastcampusprojectboard.dto.request.ArticleCommentRequest;
import com.boardProject.fastcampusprojectboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
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
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest){
        //댓글을 넣은 사람의 정보, 인증이 없는 상태에서 넣는 과정
        // TODO : 인증 정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "uno", "pw", "uno@mail.com", null, null
        )));



        return "redirect:/articles/" + articleCommentRequest.articleId();
        //article아이디를 받아서 현재 만들어진 페이지로 리다이렉션
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {

        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
