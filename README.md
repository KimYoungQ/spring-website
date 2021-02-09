# Spring-website

## 스프링부트와 Mybatis기반 웹 애플리케이션입니다.

![홈페이지화면](https://user-images.githubusercontent.com/45932388/107321246-88cb1e00-6ae5-11eb-9e60-141b62be0a6c.PNG)

    이 웹 애플리케이션은 주로 회원관리와 여러 게시판 관리를 구현합니다. 
    
    다루는 툴 :     
        * IntelliJ IDEA
        * 부트스트랩
        * Thymeleaf
        * 스프링 부트
        * 스프링 시큐리티
        * 오라클
        * 마이바티스
        * junit5
        * 메이븐
        
    
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
    
