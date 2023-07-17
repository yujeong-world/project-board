package com.boardProject.board.repository.querydsl;

import java.util.List;

public interface ArticleRepositoryCustom {

    //
    List<String> findAllDistinctHashtag();
}
