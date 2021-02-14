# AccessDecisionManager

-   AccessDecisionManager: 특정한 서버 리소스(웹 요청, 메소드 콜)에 접근하려고 할때 허용할 것인가? 유요한 요청인가 판단하는 인터페이스
-   인가를 할때 사용함
-   인증을 할때는 AuthenticationManager를 사용
-   인터페이스는 아래 3가지 메소드를 구현 강제함
    ![AuthenticationManager를](https://lh3.googleusercontent.com/pw/ACtC-3cQluqn5xh92D3fKkXal6IA-_dnRbOhOFM5l-SIHk7FBHPniw1eJbpfiW49lXNcwyoO2sp_VFuv12LQMrqMFIkhImU-kAyegjW9eVkO5QQ57x1N6W0sG1jxSNmy7ySltdRo3TtNaF4TJQvVrFy6_X6wOA=w1228-h761-no?authuser=0)
    -   decide
    -   supports(ConfigAttribute attribute);
        -   ConfigAttribute 를 지원하는지
        -   ConfigAttribute 는 SecruityConfig 에서 permitAll(), hasRole() 을 가르킴
    -   supports(Class<?> clazz);
-   AccessDecisionManager는 여러개의 Voter을 가질 수 있음
-   여러개의 Voter을 거치면서 유효한지 확인을 함
-   Access Control 결정을 내리는 인터페이스로, 구현체 3가지를
    기본으로 제공한다.

    -   AffirmativeBased

        -   기본 구현체로 사용
        -   여러개의 Voter들 중 한개라도 허용하면 허용

    -   ConsensusBased: 다수결
    -   UnanimousBased: 만장일치
