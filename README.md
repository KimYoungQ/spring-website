# Spring-website

## 스프링부트와 Mybatis기반 웹 애플리케이션입니다.

![회원가입화면](https://user-images.githubusercontent.com/45932388/109648124-93854a00-7b9d-11eb-9760-706daf8ce0cb.PNG)
![메인](https://user-images.githubusercontent.com/45932388/109648263-c3345200-7b9d-11eb-91f4-dad94d70cca0.PNG)
![게시판main_페이지](https://user-images.githubusercontent.com/45932388/108797721-34d53480-75cf-11eb-8dc1-4bfbd61d648e.PNG)
![게시글read_페이지](https://user-images.githubusercontent.com/45932388/108797728-3b63ac00-75cf-11eb-8690-8a556f893ba0.PNG)

    사용자 인증을 통한 게시판 CRUD 웹 애플리케션입니다.
    
    - Web backend(Java, Spring framework, Oracle)
    - Web frontend(HTML, CSS, Bootstrap, Thymeleaf)
    - Spring Boot사용, MyBatis Oracle연동
    - Spring Security
        · 사용자 인증 및 권한 부여
        · 로그인 커스텀 페이지
        · 권한에 따른 페이지 접근 제한
        · 현재 로그인 사용자 정보 참조 게시판 CRUD 기능 제한
    - 메일 인증을 통한 가입 관리
    - 게시판
        · CRUD 및 이미지 첨부
        · 페이징
    - Junit5 단위 테스트 구현
        
    
## 로컬에서 실행

### application.properties에서 설정

1) 포트설정
    ```
    server.port = 8090
    ```

2) Oracle 계정 설정 (Oracle 미설치 사용자는 설치가 필요합니다.)

    ```
    spring.datasource.url = jdbc:oracle:thin:@localhost:1521:xe
    spring.datasource.username = ${ORACLE_USER:hr}
    spring.datasource.password = ${ORACLE_PASSWORD:hr}
    ```
    
    오라클 계정을 생성하신 후 해당 계정을 기입해주시면 됩니다.
    
3) 스키마, 데이터 초기화
    ```
    spring.datasource.initialization-mode = never
    ```
    애플리케이션 최초 실행시에만 `never`를 `always`로 수정해주세요. 그 후 반드시 `never`로 수정 해주셔야 됩니다. (에러시 해당 오라클 계정의 데이터 모두 삭제 후 재실행)
    
