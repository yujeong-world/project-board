package com.boardProject.board.controller;


import com.boardProject.board.domain.constant.FormStatus;
import com.boardProject.board.domain.type.SearchType;
import com.boardProject.board.dto.UserAccountDto;
import com.boardProject.board.dto.request.ArticleRequest;
import com.boardProject.board.dto.security.BoardPrincipal;
import com.boardProject.board.response.ArticleResponse;
import com.boardProject.board.response.ArticleWithCommentResponse;
import com.boardProject.board.service.ArticleService;
import com.boardProject.board.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;


/*
/articles
/articles/{article-id}
/articles/search
/articles/search-hashtag
*/


@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumber = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumber);
        map.addAttribute("searchTypes", SearchType.values());
        return "articles/index";
    }

    //게시글 상세 페이지 (게시글 단건 조회)
    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentResponse article = ArticleWithCommentResponse.from(articleService.getArticleWithComments(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentResponses());

        return "articles/detail";
    }

    //해시태그 검색
    @GetMapping("/search-hashtag")
    public String searchArticleHashtag(
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ){
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumber = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        List<String> hashtags = articleService.getHashtags();

        map.addAttribute("articles", articles);
        map.addAttribute("hashtags", hashtags);
        map.addAttribute("paginationBarNumbers", barNumber);
        map.addAttribute("searchType", SearchType.HASHTAG);

        return "articles/search-hashtag";
    }

    @GetMapping("/form")
    public String articleForm(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "articles/form";
    }


    @PostMapping ("/form")
    public String postNewArticle(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleRequest articleRequest
    ) {
        // TODO: 인증 정보를 넣어줘야 한다.
        articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles";
    }


    @GetMapping("/{articleId}/form")
    public String updateArticleForm(
            @PathVariable Long articleId, ModelMap map
    ) {
        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

        map.addAttribute("article", article);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "articles/form";
    }


    @PostMapping ("/{articleId}/form")
    public String updateArticle(
            @PathVariable Long articleId,
            ArticleRequest articleRequest,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal
    ) {
        //인증정보
        articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleId;
    }


    @PostMapping("/{articleId}/delete")
    public String deleteArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal
            ) {

        // TODO: 인증 정보를 넣어줘야 한다.
        articleService.deleteArticle(articleId, boardPrincipal.getUsername());

        return "redirect:/articles";
    }


}