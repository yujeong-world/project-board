package com.fastcampus.fastcampusprojectboard.service;

import com.boardProject.board.domain.Article;
import com.boardProject.board.dto.ArticleCommentDto;
import com.boardProject.board.repository.ArticleCommentRepository;
import com.boardProject.board.repository.ArticleRepository;
import com.boardProject.board.service.ArticleCommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService sut;

    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments(){
        //given
        Long articleId = 1L;

        BDDMockito.given(articleRepository.findById(articleId)).willReturn(Optional.of(Article.of("title", "content", "#hammzi")));

        //when
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);
        //then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글 정보를 입력하면, 댓글 리스트를 반환한다")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments(){
        //given
        Long articleId = 1L;

        BDDMockito.given(articleRepository.findById(articleId)).willReturn(Optional.of(Article.of("title", "content", "#hammzi")));

        //when
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);
        //then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }
}