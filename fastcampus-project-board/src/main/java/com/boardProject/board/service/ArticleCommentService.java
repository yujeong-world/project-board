package com.boardProject.board.service;


import com.boardProject.board.domain.Article;
import com.boardProject.board.domain.ArticleComment;
import com.boardProject.board.dto.ArticleCommentDto;
import com.boardProject.board.repository.ArticleRepository;
import com.boardProject.board.domain.UserAccount;
import com.boardProject.board.repository.ArticleCommentRepository;
import com.boardProject.board.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;

    private final ArticleCommentRepository articleCommentRepository;

    private final UserAccountRepository userAccountRepository;


    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {

        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .toList();
    }

    //ArticleCommentDto 객체를 사용하여 댓글을 저장하는 메서드
    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            //먼저 게시글 객체와 사용자 객체를 사용하여 댓글을 저장하기 위해 Repository를 가져와 줌
            Article article = articleRepository.getReferenceById(dto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            articleCommentRepository.save(dto.toEntity(article, userAccount));
        }catch (EntityNotFoundException e){
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다. - {}", e.getLocalizedMessage());
        }
    }

    public void updateArticleComment(ArticleCommentDto dto) {
        try {
            ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.id());
            if (dto.content() != null){ articleComment.setContent(dto.content());}
        } catch (EntityNotFoundException e){
            log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다. - dto : {}", dto);
        }
    }

    public void deleteArticleComment(Long articleCommentId) {
        articleCommentRepository.deleteById(articleCommentId);
    }

}
