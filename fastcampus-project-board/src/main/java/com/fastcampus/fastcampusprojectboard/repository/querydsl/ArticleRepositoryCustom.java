package com.fastcampus.fastcampusprojectboard.repository.querydsl;

import java.util.List;

public interface ArticleRepositoryCustom {

    //
    List<String> findAllDistinctHashtag();
}
