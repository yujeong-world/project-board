package com.fastcampus.fastcampusprojectboard.domain;

import java.time.LocalTime;

public class Article {

    private long id;
    private String title; //제목
    private String content; // 본문
    private String hashtag; //해시태그

    private LocalTime createdAt; //생성일시
    private String createBy; //생성자
    private LocalTime modifiedAt; //수정일시
    private String modifiedBy; // 수정자
}
