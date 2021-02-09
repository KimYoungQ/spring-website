# Spring-website

## 로컬에서 구동시

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
    애플리케이션 최초 실행시 `never`를 `always`로 수정해주세요. 그 후 반드시 `never`로 수정 해주셔야 됩니다. (에러시 해당 오라클 계정의 데이터 모두 삭제 후 재실행)
    
