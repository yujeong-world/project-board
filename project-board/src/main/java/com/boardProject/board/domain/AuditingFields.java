package com.boardProject.board.domain;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.LocalTime;
// Article과 ArticleComment에서 중복된 컬럼들을 빼와서 만듦, 상속으로 연결시켜줌

// 세터를 안넣는 이유는 외부에서 값을 넣는것이 아니라 얘내는 자동적으로 생성되는 애들이기 때문에
//인덱스 생성부분도 중복되지만 따로 빼서 만들지는 않는다..
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditingFields {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false) //updatable = false 이필드는 업데이트 불가하다, 최초한번만 생성가능
    private LocalDateTime createdAt; //생성일시
    @CreatedBy
    @Column(nullable = false, length = 100, updatable = false)
    private String createdBy; //생성자
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; //수정일시
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자
}
