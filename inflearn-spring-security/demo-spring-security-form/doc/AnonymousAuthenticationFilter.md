# 익명 인증 필터: AnonymousAuthenticationFilter

- 현재 SecurityContext에 Authentication이 null 이면 '익명 Authentication'을 만들어 넣어주고, null이 아니면 아무일도 하지 않는다.
- 아래와 같이 기본 설정값을 유저가 설정할 수 있다.
    ```java
        http.anonymous()
        .principal("anonymousUser");
    ```
- 기본값으로 principal은 anonymousUser, authoritie는 ROLE_ANONYMOUS 으로 설정
    ![기본값](https://lh3.googleusercontent.com/pw/ACtC-3da_VHV0vCGFPjVPurOdHhUgLF5xgspepd99wonTY8ZaJftO7alW2s6qOGYKdLp4lbnah-dvmrUjZWMSDjBz5MLGW2c4OBPB20iyK077Zxjzq-_aeTrBDzSubGe5Ivm8oFdKSl7BC83Y_Y3sCfar3XlNw=w948-h147-no?authuser=0)