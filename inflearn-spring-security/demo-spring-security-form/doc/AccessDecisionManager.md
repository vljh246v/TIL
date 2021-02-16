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

-   AffirmativeBased 내부에서 voter에 기본으로 WebExpressionVoter 가있음

    ![WebExpressionVoter](https://lh3.googleusercontent.com/pw/ACtC-3epOoIQw7fj1tdTlQPV1UXUoIVqLOI9-rx3hvEDu0jPMViQyo8q27NFVUv4d5peSSNFBnkmVyZUGOWWrJxmlwcWsU9fe7qFw2EtjKHLMHPEBB2w7b6ZP0o5-bjfystbRUQafViKEdVJmw7Bbh4B95RmFw=w933-h564-no?authuser=0)

-   WebExpressionVoter 내부에서 Attribute 를 가지고 오는데 해당 Attribute는 permitAll 을 가르킴
    ![Attribute](https://lh3.googleusercontent.com/pw/ACtC-3f3Gg6a0rSx2qUPvw3jG_autAnjyLUgVK2egErv0LroKfSWNi9eiEnR9lQfTuSXzUvlwperr6tGT0OAkXc9OHPzDOQfs00e86tRgMasqmu2aIIyr3isnNejqiMg0oaLT1B0T0VYDZ-T2ggLB_wD6BtsGA=w1110-h30-no?authuser=0)

-   정상적인 인가 정보라면 ACCESS_GRANTED (1) 을 출력함
-   인증 정보가 없는 상태로 로그인이 필요한 페이지에 접근하면 ACCESS_DENIED (-1)을 출력
    ![ACCESS_DENIED](https://lh3.googleusercontent.com/pw/ACtC-3cP-Mgj7cZ4Cijf1_YTE9tW3r3SP8X2KXYCTNTSmczaIuYTXRCZqRG26Ni1iDr8QUd-LsZblaOoYbk6i08pAUXu18ztMUouAgf2F586ypuEcV3ZBLnBOPyCm5AOOm4N_e4hcTscPpw9FpkaWHETzpGWJQ=w978-h133-no?authuser=0)

-   어플리케이션은 사용자가 직접 입력한 role을 이해할 수 없음, 단순 문자열로 판단.
    ![role](https://lh3.googleusercontent.com/pw/ACtC-3fnjFD_ln7J0YlUfYcxaZqGHyWKHKJkD22SofcUM_s3ZlqKBq57UpNb5QcTDBfuxUhE-vay_KpewKwtRS1p6klixD6lukE4iWoqiuRPyyU6H_IvIbr3Q-Xz9JdlGgDjr8c6PR6BJ8MX2Iw6a7j9k7zz5g=w821-h236-no?authuser=0)

-   그렇기 때문에 사용자가 직접 role를 여러개 지정해주는 방법과 AccessDecisionManager가 이러한 role들의 Hierarchy를 이해하게 설정해주는 방법이 있음
-   Hierarchy를 이해하는 AccessDecisionManager를 새로 정의해야함
    ![AccessDecisionManager정의](https://lh3.googleusercontent.com/pw/ACtC-3ekU3ygUISfsikvSdyk6EvxGa38c_DnlX-VVjtZDxFfp21zxc5bEFrBQiVGGZNx3INnfbQFXCbSSJLVhQmioVicKQ-3bSfIlva8ZrY4CuM8joRdJYCDZIiVj_2RYtivnz6eaijQ091zHjITciF-9SAxrg=w752-h664-no?authuser=0)

-   AccessDecisionManager과 Hierarchy를 설정하는 방법이 너무 길고 복잡하다면, voter가 사용하는 ExpressionHandler 만 설정해주는 방법도 있음
    ![ExpressionHandler](https://lh3.googleusercontent.com/pw/ACtC-3caX4avtunilLEb-iTLY6ljT49Ci1kSd9lM2ABuTaUsxhqLWgpCmpsx2Knd5A15-wjpIIYUZOpfP70ccqw213l-QiwPr8ps-Mes9gPA88w0xAaM5HJ3N9ue7TvMQwMZ5KiTfzZoSNW57RQTU2j54TY6Zg=w1181-h725-no?authuser=0)
