# 스프링 시큐리티 필터와 FilterChainProxy

- 스프링 시큐리티가 제공하는 필터들

1. WebAsyncManagerIntergrationFilter
2. SecurityContextPersistenceFilter
3. HeaderWriterFilter
4. CsrfFilter
5. LogoutFilter
6. UsernamePasswordAuthenticationFilter
7. DefaultLoginPageGeneratingFilter
8. DefaultLogoutPageGeneratingFilter
9. BasicAuthenticationFilter
10. RequestCacheAwareFtiler
11. SecurityContextHolderAwareReqeustFilter
12. AnonymouseAuthenticationFilter
13. SessionManagementFilter
14. ExeptionTranslationFilter
15. FilterSecurityInterceptor


- FilterChainProxy.doFilterInternal 실행시 필터 목록을 들고온다.
  ![doFilterInternal](https://lh3.googleusercontent.com/pw/ACtC-3em-ug5M73Ey23NjMJaNQ-vsI64U__z2XCXKME_KmHDGSsh6VQQPjJ34Yh_-YnG5ApgrEGQDWsX_yREbrsZRhZxlqo_jYbQz9Ajk-XfJwpGavhDfTrq09hPRRvCTJkVypQ7PY8Daw2OKk9ojdVPulzMLA=w1060-h414-no?authuser=0)

  ![getFilters](https://lh3.googleusercontent.com/pw/ACtC-3dZByzTI-WnAAZh3rhsBYTuthsD2JMf7mYPV1Es1HuqnLj9IPM5QRj5pz_6_lLbpZhTF8UbLgxWN83WEV_tfVTTpGjRvnVeElwbeR1xE9qZH2kTTqemHV_kPt6OOgRy_NI3gMHDMFEZVoEXz9AzxLWgzQ=w1098-h584-no?authuser=0)

- SecurityFilterChain중 특정한 요청(url 패턴)이 매치가 되면 거기에 매칭하는 chain에 들어있는 필터 목록을 들고옴
- 가지고 왔다면 해당 필터들을 하나씩 순회 하면서 순차적으로 실행함

  ![필터목록](https://lh3.googleusercontent.com/pw/ACtC-3dsc5mPIA4nejI8Stk73VPpyoKNZkT-lcRa-0Pn9af-p1LRFxpLv8wHtmSvbSkBXxg5DQF_Ry6XEsHGtXR9ki2p1MiRkGwhSNDfAozi4Ovg4XBkTRJBSQIk5be7sRIT9IpRVrjKf1oG8cU7Ep-vZTZfAQ=w934-h718-no?authuser=0)

  ![doFilter](https://lh3.googleusercontent.com/pw/ACtC-3e03BiK8VpIBCaAcV-75w09Ld5Qz7myzSp6CfLIbUhzwwNgOiusl6gU44yJE4JUhbiVq-OmmjOqA_5-kWC1oKMnfmh2702tRfh5PJ6H-1244a6BPEs845j-CcwlsIvm74mBpZ6X5B1QCHRF9DoqNQhdJA=w1680-h720-no?authuser=0)

- FilterChainProxy 클래스가 여러가지 필터 목록을 순차적으로 실행해준다.

  ![FilterChainProxy](https://lh3.googleusercontent.com/pw/ACtC-3cr26K_CW4-Sqqq8JRvKyoQMq9dk835scJVOXnzFo829Q6Bp4hvMLniVBPhNFhaijqs_5PhLUFPrdAZMWJMxDjxVZj0I5XOCeNwUe0IUEvF5QFDc5WwmKZfEGazh7x6zrj5BCrg3sLFGhPXyPeTktsqNQ=w1120-h734-no?authuser=0)

- chain 을 선택할때 SecurityFilterChain 목록중에서 선택을 한다.

- 그렇다면 FilterChain이 만들어 지고 커스터마이징 되는 지점은 우리가
  개발했던 [SecurityConfig](../src/main/java/til/demo/demospringsecurityform/config/SecurityConfig.java)
  다.

- [SecurityConfig](../src/main/java/til/demo/demospringsecurityform/config/SecurityConfig.java) 가 큰
  FilterChain 이라고 생각하면 된다.
- 그리고 그 FilterChain들 중에서 매칭이되는 것들을 사용하는 것이므로, SecurityConfig를 여러개 작성해도 된다.

- 만약 두개 SecurityConfig가 상충되는 조건이라면 SecurityFilterChain 에서는 어떤 FilterChain을 가지고 올까?

  ![SecurityConfig](https://lh3.googleusercontent.com/pw/ACtC-3fig6ZJWih7mOPTOVHMnPMSmaiX0bA0DbIPPZ2T4CmtguXRRqoqWmUwqq7sMXT1lupQjPnugc8Bgtd4huC7itaM8YKPKi3xmG_8zwDxxgUTFZyNNRjepGFzGZzxwZabEJoj5qhdbNwszXYzok21TeHctA=w1046-h568-no?authuser=0)

  ![AnotherSecurityConfig](https://lh3.googleusercontent.com/pw/ACtC-3dpbuTUGkXKEdELq6vsDlq5FBqFXdhtdDg_m_7XupWucpDJgKet3pBZFtSxzLsifqHbllSENXsnvc3r1VHiCY4GXvT8HtgaMG3De35rWMoBlj38ya0EwVDXU-YHf6Y0m7NGAXr7yPFwR0CaoTAXm_wW8Q=w1092-h628-no?authuser=0)

- 위 그림은 SecurityConfig 는 모든 요청을 허용하고, AnotherSecurityConfig는 모든 요청에 인증을 요구한다.

  ![error](https://lh3.googleusercontent.com/pw/ACtC-3eWRV9a-c73bxCuE4JUEu1EmGfglw2sZ2XcjNMvO2Bk5Xj0LlzO3oAIMu399rkF3HsJrOYUbFg-MjrFVq6iy5RGkuaizfY4BNggpxcYlCeUkEOjx07xjbZR348lLo18TxChn5RJwFnlMYsKnsJoUzXtMw=w1680-h180-no?authuser=0)

- Spring Security 에서 이러한 경우에는 상충하기 때문에 순서가 중요함. 그래서 에러가 발생한다.

- 그렇다면 순서를 준다면 어떤일이 발생할까?

    ```java
    @Configuration
    @Order(Ordered.LOWEST_PRECEDENCE - 10)
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
    }

    @Configuration
    @Order(Ordered.LOWEST_PRECEDENCE - 15)
    public class AnotherSecurityConfig extends WebSecurityConfigurerAdapter {
    }
    ```

- 실제로 필터 체인을 가지고 올때 목록이 2개가 된것을 볼 수 있다.
  ![filterChains](https://lh3.googleusercontent.com/pw/ACtC-3fMwTIXmBaNQJsvwTX60ZzWT4MKyICQ8ODDhdqL3wWSjXroeyf7TL8DiZzdUSYGYNVYKlrzw6jq09rIBRFntQJv5z1GONv8r3Q4MHRW4BWCXkZZ0fGsqN8N0L2LkJlxMNikgJyxNDRF1uK6tJMZakB36A=w1680-h834-no?authuser=0)

- 첫번째 순서로 올라온 설정이 AnotherSecurityConfig 이므로 모든것에 인증을 필요로 하는 chain 이다.

- 그리고 form 인증과 httpBasic 설정을 사용하지 않는다면 filter chain list 수는 더 적어진다.

    ```java
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        // 특정 url 기반 접근 설정을 하는 영역
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests() // 어떤 식으로 '인가' 할지
                .mvcMatchers("/", "/info", "/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin() // form 로그인을 사용
                .and()
            .httpBasic();
        }
    }

    @Configuration
    @Order(Ordered.LOWEST_PRECEDENCE - 15)
    public class AnotherSecurityConfig extends WebSecurityConfigurerAdapter {

        // 특정 url 기반 접근 설정을 하는 영역
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .antMatcher("/account/**")
                .authorizeRequests() // 어떤 식으로 '인가' 할지
                .anyRequest().authenticated();
        }
    }
    ```

  ![chain list size](https://lh3.googleusercontent.com/pw/ACtC-3dZuRbw5m4Ctr8pxcjuNGK97imnJ9ngVBaG-IL-1NHZCj9gUYe8sXVLmjKrOAmE7TLXuUwPXhugORWpy5zReWElSLisVtgdYe2EHEPZMkqpZ7z2CydjrgCBCJLqweUV71AejpSxFCgsOOFtKfFqE0iQGQ=w1160-h314-no?authuser=0)