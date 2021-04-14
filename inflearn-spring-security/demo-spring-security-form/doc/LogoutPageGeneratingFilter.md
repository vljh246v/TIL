# 로그인/로그아웃 폼 페이지 생성해주는 필터: DefaultLogin/LogoutPageGeneratingFilter


- 기본 로그인 폼 페이지를 생성해 주는 필터
  - GET /login 요청을 처리하는 필터.

- username/password 파라미터를 우리가 원하는 파라미터 명으로 바꾸는 작업도 가능

    ```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ...

        http.formLogin()
            .usernameParameter("my-username")
            .passwordParameter("my-password");

        ...

    }
    ```

- 로그인 실패/성공시 어디로 보낼지 설정도 가능


- 사용자가 직접 로그인 페이지를 처리하겠다고 설정하면 해당 필터가 필터체인에 등록되지 않음
- 필터에서 빠진걸 볼 수 있다.

    ```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ...

        http.formLogin()
            .loginPage("'/login");

        ...

    }
    ```
    ![DefaultLoginPageGeneratingFilter](https://lh3.googleusercontent.com/pw/ACtC-3fvRovjGW7ymnlunEUwtWJqOM30uI_x4b4i_3TQXCAQZt5NcyOZdUYCqbu2rQEHVjDQ4VbVz5swDdWlWrsS14fsmn0hWbQqEQpf_tuDdTu2qBYnWx2amuSJRU6Pou-lJYUVotgBzLUDN-fKRlo1ixdFGA=w536-h753-no?authuser=0)