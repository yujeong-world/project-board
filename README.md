# 게시판 서비스 - 토이프로젝트
이 토이프로젝트는 스프링 부트를 학습하는 과정에서 개발한 게시판 토이프로젝트입니다.
기본적이고 보편적인 기능을 담은 게시판을 구현하였으며, 1인으로 진행한 프로젝트입니다.

이 프로젝트는 스프링 부트와 관련된 다양한 기술들을 활용하여 구현되었습니다. 게시판 서비스는 웹 애플리케이션으로 실제 사용자들이 글을 작성하고, 상호작용하며, 정보를 검색할 수 있는 편리한 인터페이스를 제공합니다. 앞으로 추가적인 기능 개발과 성능 향상을 위해 지속적으로 유지보수될 예정입니다.
<br>


### 🚀 게시판 서비스 주요 기능 소개
**게시판 글 정렬 기능**: 사용자들이 게시판의 글을 최신순 또는 인기순으로 정렬할 수 있습니다.
**게시판 검색 기능**: 제목, 본문, 유저Id, 닉네임, 해시태그 등으로 게시글을 검색할 수 있습니다.
**해시태그 필터링 기능**: 해시태그를 선택하면 해당 태그가 포함된 게시글들만 따로 볼 수 있습니다.
**게시글 작성**: 사용자들은 로그인 후에 게시글을 작성할 수 있습니다.
**댓글 작성**: 게시글에 댓글을 작성하여 소통할 수 있습니다.
**회원 로그인**: 사용자 인증을 통해 로그인 기능을 제공합니다.
**본인 작성 글과 댓글 삭제**: 사용자는 자신이 작성한 글과 댓글을 삭제할 수 있습니다.
<br>



### 💻 기술 세부 스택
SpringBoot
- spring web
- spring Data JPA
- spring security
- Rest Repositories
- Thymeleaf
- H2 Database
- MySQL Driver
- Lombok
- spring Boot DevTools
- Boot Strap
<br>

## Api 명세서
![image](https://github.com/yujeong-world/project-board/assets/124220083/831a7bad-0345-4c0c-8f2f-aae9f0086b89)
<br>

## ERD 다이어그램
![KakaoTalk_20230723_213459941](https://github.com/yujeong-world/project-board/assets/124220083/f4b355fa-b516-459b-8bf6-734637817f73)
1. 게시글(article) 테이블:
    - 게시글은 고유한 식별자인 'articleId'를 가지며, 이를 기준으로 테이블이 관리됩니다.
    - 게시글 내용과 제목은 'Content'와 'Title' 속성으로 저장되며, 필요에 따라 길이 제한을 설정할 수 있습니다.
    - 게시글은 특정 유저에 의해 작성되므로, 'UserID'라는 외래키를 통해 '유저계정(UserAccount)' 테이블과 연결됩니다.
    - 게시글과 댓글은 1대 다 관계를 가지며, 각 게시글은 여러 개의 댓글을 가질 수 있습니다.
2. 댓글(Comment) 테이블:
    - 댓글은 고유한 식별자인 'CommentID'를 가집니다.
    - 댓글 내용은 'Content' 속성으로 저장되며, 필요에 따라 길이 제한을 설정할 수 있습니다.
    - 댓글은 특정 게시글과 특정 유저에 의해 작성되므로, 'articleId'와 'UserID'라는 외래키를 통해 각각 '게시글(Post)' 테이블과 '유저계정(UserAccount)' 테이블과 연결됩니다.
    - 댓글은 게시글과 유저계정에 대해 다대일 관계를 가지며, 각 댓글은 하나의 게시글과 하나의 유저에 의해 작성됩니다.
3. 유저계정(UserAccount) 테이블:
    - 유저계정은 고유한 식별자인 'UserID'를 가집니다.
    - 유저계정은 사용자의 정보를 저장하기 위해 다양한 속성들을 포함할 수 있으며, 'Username', 'Email', 'Password' 등이 일반적인 속성으로 사용됩니다.
    - 유저계정은 여러 개의 게시글과 댓글을 작성할 수 있으므로, 'UserID'를 외래키로 가지는 '게시글(article)'과 '댓글(Comment)' 테이블과 일대다 관계를 형성합니다.
<br>

## 📌 메인 페이지 미리보기
![Untitled](https://github.com/yujeong-world/project-board/assets/124220083/8f5a1a80-07c1-4348-9487-70fbfa060f09)
프로젝트에서는 **타임리프(Template Engine)**를 통해 동적인 웹 페이지의 뷰를 구현하였습니다. 타임리프는 **서버 사이드에서 실행되며, 데이터와 함께 HTML 템플릿을 렌더링하여 클라이언트에게 전달**합니다. 이를 통해 사용자에게 보여지는 웹 페이지의 내용을 동적으로 생성하고 제공할 수 있습니다.

또한, **부트스트랩(Bootstrap)을 적극적으로 활용하여 사용자 인터페이스(UI)를 구현**하였습니다. 부트스트랩은 모바일 기기와 데스크톱을 포함한 다양한 플랫폼에서 일관된 디자인과 사용자 경험을 제공하는 프론트엔드 프레임워크입니다. 이를 활용하여 웹 애플리케이션의 시각적인 디자인과 레이아웃을 효율적으로 개발하고, 반응형 웹 디자인을 쉽게 구현할 수 있습니다.

이렇게 타임리프와 부트스트랩을 조합하여 프로젝트의 뷰 레이어를 구현함으로써 사용자들에게 보다 편리하고 직관적인 UI를 제공하였습니다. 또한, 서버 사이드와 클라이언트 사이드 간의 데이터 흐름을 원활하게 조율하여 사용자 경험을 최적화하였습니다.
