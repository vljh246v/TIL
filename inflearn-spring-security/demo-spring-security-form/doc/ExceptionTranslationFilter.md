# **ExceptionTranslationFilter**

-   FilterSecurityInterceptor의 상위 클래스인 AbstractSecurityInterceptor 에서 발생한 AccessDeniedException, AuthenticationException 을 처리하는 필터
-   AbstractSecurityInterceptor에서 발생하는 예외만 처리
-   AuthenticationException 발생 시
    -   AuthenticationEntryPoint 실행
-   AccessDeniedException 발생 시
    -   Authentication이 익명 사용자라면 AuthenticationEntryPoint 를 실행해서 사용자가 로그인 할 수 있도록 함(로그인 페이지 출력 등)
    -   Authentication이 익명 사용자가 아니라면 AccessDeniedHandler 에게 위임
