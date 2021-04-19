# **ExceptionTranslationFilter**

-   FilterSecurityInterceptor의 상위 클래스인 AbstractSecurityInterceptor 에서 발생한 AccessDeniedException, AuthenticationException 을 처리하는 필터
-   AbstractSecurityInterceptor에서 발생하는 예외만 처리
-   AuthenticationException 발생 시
    -   AuthenticationEntryPoint 실행
-   AccessDeniedException 발생 시
    -   Authentication이 익명 사용자라면 AuthenticationEntryPoint 를 실행해서 사용자가 로그인 할 수 있도록 함(로그인 페이지 출력 등)
    -   Authentication이 익명 사용자가 아니라면 AccessDeniedHandler 에게 위임


- FilterSecurityInterceptor 보다 이전에 있어야함
- ExceptionTranslationFilter가 FilterSecurityInterceptor를 try-catch 블럭으로 감싼다음 필터 처리를 한다.

- FilterSecurityInterceptor는 실제 인가 처리는 AccessDecisionManager(구현체 : AffirmativeBased)를 통해 함
- 이때 두 가지 에러가 발생함
  - AuthenticationException : 인증
    - AuthenticationEntryPoint를 통해 인증할 수 있는 페이지로 보냄
  - AccessDeniedException : 권한
    - AccessDeniedHandler를 가지고 기본 처리를 함 
    - 기본은 403

    ![사용 설정](https://lh3.googleusercontent.com/pw/ACtC-3dSInzq30qo6x83--VjSaJ9pwyXtk2NgWXJNVNvVQPPPpQXxLefeX2lnhhno7I3QU7hkmEzLg97nwMUi0ZQbHG4sQ6qqAFDMvAa3DyZtlEVhcJThk3FPN5ehT6pho39nLKq3ovYzy2uPtbUeNNCKPnicw=w454-h65-no?authuser=0)

    ![사용 설정2](https://lh3.googleusercontent.com/pw/ACtC-3fNQKaAr1-ne88pK-DDvDHFv1Q4VR3uqd83o8QyAZDEupeP3Vabybqb5bpcWgUVPyDCLXfBsk_mdbAITPW5VcP9gYNeJRXUnMy1b2HCr6ziAQ1UHb0pO_mTmwSRl6VZyTji5VjonFwuHfpws2NJdOvKZQ=w769-h289-no?authuser=0)