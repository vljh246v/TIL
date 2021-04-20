# 토큰 기반 인증 필터 : RememberMeAuthenticationFilter

- 세션이 사라지거나 만료가 되더라도 쿠키 또는 DB를 사용하여 저장된 토큰 기반으로 인증을 지원하는 필터

- 기본 파라미터 이름은 'remember-me', 
    ```html
    <form>
        <p>Remember : <input type="checkbox" name="remember-me"></p>
    </form>
    ```

- remember-me 기능을 키고 로그인을 하면 아래와 같이 또다른 쿠키가 생성된다.
    ![remember-me 쿠키](https://lh3.googleusercontent.com/pw/ACtC-3dAepAV0Kz-TfwLR5tmVoRzY1rZEm-GgxIFEhtw12Ye-gONoA8ysKQQMVaUQ8Yw4K7zL_wyiVgzq7q_LV4GPVFo6J9zvt_GwAJjEj_cNlQENehcVo-G-6tN66HPwyni-V7r1LbDbZhrvwx0oaMFWJ3WkA=w559-h243-no?authuser=0)
-  해당 쿠키에는 username, 유효기간등의 정보가 들어 있음
-  이때 JSESSIONID쿠키를 삭제해도 로그인이 필요 없음

- remember-me 기능을 추가하는 방법은 아래와 같다.
    ```java
        http.rememberMe()
        .userDetailsService(accountService)
        .key("remeber-me-sample");
    ```
- 추가를 하면 아래와 같이 필터 목록에 RememberMeAuthenticationFilter가 추가된걸 볼 수 있다.
    ![필터 추가](https://lh3.googleusercontent.com/pw/ACtC-3c7wX5e5Y108QEjIJrLouUPHFWTKcLPtXHk6cfGJPmmxiRnoovfYtTNZe5p5-ewvE-qHJgWypJUpeKjnZz6HjyWogIDcxHRC7J-AW0yHkm2uGNvtIi4cwLGEe-593q2PczK4xs1sd4goZBVo2OevSK0BQ=w786-h697-no?authuser=0)