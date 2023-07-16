package com.fastcampus.fastcampusprojectboard.service;

import com.boardProject.fastcampusprojectboard.domain.Article;
import com.boardProject.fastcampusprojectboard.domain.type.SearchType;
import com.boardProject.fastcampusprojectboard.dto.ArticleDto;
import com.boardProject.fastcampusprojectboard.repository.ArticleRepository;
import com.boardProject.fastcampusprojectboard.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks  //필드 주입
    private ArticleService sut; // 테스트 대상

    @Mock
    private ArticleRepository articleRepository;

    /*
    검색
    각 게시글 페이지로 이동
    페이지네이션
    홈버튼 -> 게시판 페이지로 리다이렉션
    정렬 기능
    */

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameter_whenSearchingArticles_thenReturnsArticleList(){
        //Given

        //When
        Page<ArticleDto> articles=sut.searchArticles(SearchType.TITLE,"search keyword"); // 검색 파라미터 : 제목, 본문, 아이디, 닉네임, 해시태그

        //Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("검색어 없이 게시글을 해시태그 검색하면, 빈 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticlesViaHashtag_thenReturnsEmptyPage(){
        //given
        Pageable pageable = Pageable.ofSize(20);

        //when
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(null, pageable);

        //then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        BDDMockito.then(articleRepository).shouldHaveNoInteractions();
    }

    @DisplayName("게시글을 해시태그 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenHashtage_whenSearchingArticlesViaHashtag_thenReturnsArticlesPage(){
        //given
        String hashtag = "#java";
        Pageable pageable = Pageable.ofSize(20);
        BDDMockito.given(articleRepository.findByHashtag(hashtag, pageable)).willReturn(Page.empty(pageable));

        //when
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(hashtag, pageable);

        //then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        BDDMockito.then(articleRepository).should().findByHashtag(hashtag, pageable);
    }

    @DisplayName("해시태그를 조회하면, 유니크 해시태그 리스트를 반환한다.")
    @Test
    void givenNothing_whenCalling_thenReturnsHashtags(){
        //given
        List<String> expectedHashtag = List.of("#java", "#spring", "#boot");
        BDDMockito.given(articleRepository.findAllDistinctHashtags()).willReturn(expectedHashtag);

        //when
        List<String> actualHashtags = sut.getHashtags();
        //then
        assertThat(actualHashtags).isEqualTo(expectedHashtag);
        BDDMockito.then(articleRepository).should().findAllDistinctHashtags;
    }


    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void giveArticleId_whenSearchingArticles_thenReturnsArticle(){
        //Given

        //When
        ArticleDto articles=sut.searchArticle(1L);

        //Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParamete_whenSearchingArticles_thenReturnsArticleList(){
        //Given

        //When
        Page<ArticleDto> articles=sut.searchArticles(SearchType.TITLE,"search keyword"); // 검색 파라미터 : 제목, 본문, 아이디, 닉네임, 해시태그

        //Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle(){
        //given
        BDDMockito.given(articleRepository.save(ArgumentMatchers.any(Article.class))).willReturn(null);

        //when
        sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "yujeong", "title", "content", "#hamzzi"));

        //then
        BDDMockito.then(articleRepository).should().save(ArgumentMatchers.any(Article.class));

    }

    @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatesArticle_thenUpdatesArticle(){
        //given
        BDDMockito.given(articleRepository.save(ArgumentMatchers.any(Article.class))).willReturn(null);

        //when
        sut.updateArticle(1L, ArticleUpdateDto.of( "title", "content", "#hamzzi"));

        //then
        BDDMockito.then(articleRepository).should().save(ArgumentMatchers.any(Article.class));

    }

    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeleteArticle_thenDeletesArticle(){
        //given
        BDDMockito.willDoNothing.BDDAssumptions.given(articleRepository).delete(ArgumentMatchers.any(Article.class));

        //when
        sut.deleteArticle(1L);

        //then
        BDDMockito.then(articleRepository).should().delete(ArgumentMatchers.any(Article.class));

    }




}