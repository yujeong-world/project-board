package com.fastcampus.fastcampusprojectboard.service;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.UserAccount;
import com.fastcampus.fastcampusprojectboard.domain.type.SearchType;
import com.fastcampus.fastcampusprojectboard.dto.ArticleDto;
import com.fastcampus.fastcampusprojectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.fastcampusprojectboard.repository.ArticleRepository;
import com.fastcampus.fastcampusprojectboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor  // 필수 필드에 대한 생성자를 자동으로 만들어주는 롬복 어노테이션
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        //검색어가 있을 때와 없는 경우를 분리해서 생각.

        //검색을 안하는 경우 ! 키워드가 널인경우, 전체 데이터를 보여줌
        if(searchKeyword == null || searchKeyword.isBlank()){
            return articleRepository.findAll(pageable).map(ArticleDto :: from); //ArticleDto에 있는 from메소드를 보내줌
        }

        //검색 키워드 타입 별로 다르게 처리하기
        return switch (searchType){
            //pageable은 입력 파라미터임, 그걸 맵핑해서 ArticleDto로 넘겨주면 됨
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#"+searchKeyword, pageable).map(ArticleDto::from);
        };

    }


    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(()->new EntityNotFoundException("게시글이 없습니다 -articleId:"+articleId));
    }

    //게시글 단건 조회
    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    //게시글 정보를 입력 하면 게시글을 생성한다.
    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(Long.valueOf(dto.userAccountDto().userId()));
        articleRepository.save(dto.toEntity(userAccount));
    }

    //게시글 수정하는 경우
    public void updateArticle(Long articleId,ArticleDto dto) {
        //예외처리: 없는 게시글의 수정정보를 입력했을 때!
        try {
            Article article= articleRepository.getReferenceById(articleId);
            // 해시태그는 null값이 가능한 필드이므로 입력받은 내용을 그대로 넣음
            //내용과 제목은 null불가함
            if (dto.title() != null){ article.setHashtag(dto.title()); }
            if (dto.content() != null){ article.setHashtag(dto.content()); }
            article.setHashtag(dto.hashtag());
        } catch (EntityNotFoundException e){
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다. - dto: {}", dto);
        }

    }

    //게시글 삭제
    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount(){
        return articleRepository.count();
    }


    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        //해시태그가 안들어 온 경우 -> 검색을 할 필요가 없음
        if (hashtag==null || hashtag.isBlank()){
            return Page.empty(pageable);
        }


        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from
        );
    }

    public List<String> getHashtags(){
        return articleRepository.findAllDistinctHashtag();
    }

}
