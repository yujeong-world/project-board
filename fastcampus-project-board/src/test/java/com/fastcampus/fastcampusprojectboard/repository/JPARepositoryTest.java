package com.fastcampus.fastcampusprojectboard.repository;

import com.boardProject.board.domain.Article;
import com.boardProject.board.repository.ArticleCommentRepository;
import com.boardProject.board.repository.ArticleRepository;
import com.boardProject.board.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DisplayName("JPA 연결 테스트")
@Import(JPARepositoryTest.TestJpaConfig.class)
@DataJpaTest
class JPARepositoryTest {


    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;



    public JPARepositoryTest(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository,
                             @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        //given

        //when
        List<Article> articles =articleRepository.findAll();
        //then
        assertThat(articles)
                .isNotNull()
                .hasSize(0);

    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFind(){
        Article article =articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        //when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount)
    }

    //컨피규레이션 어노테이션으로 등록하되, 테스트 할 때만 적용, 일반적으로 서비스 스캔에는 보이지 않음
    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig{
        @Bean
        public AuditorAware<String> auditorAware(){
            return () -> Optional.of("uno");
        }
    }
}
