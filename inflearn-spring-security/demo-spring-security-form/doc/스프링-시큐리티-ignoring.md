# 스프링 시큐리티 ignoring

-   특정 요청에 대해서 인증 과정 없이 보여주고 싶을때가 있다.
-   예를 들면 favicon 요청이라던지 정적 리소스에 대한 요청 같은경우 인증이 필요가 없을 때가 있다.
-   적용하고 싶지 않은 요청에 대해서는 WebSecurity를 파라미터로 받는 메소드를 오버라이드 해서 설정을 진행한다.
-   내부에서 ignoring() 을 사용해서 설정을 한다.
    ```java
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        ...
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().mvcMatchers("/favicon.ico");
        }
        ...
    }
    ```
-   매번 다양한 정적 리소스에 대한 설정을 해주기는 힘들다.
-   스프링부트에서 이런 설정을 한번에 지정하는 방법을 제공한다.

    ```java
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }
    ```

-   위 처럼 설정을 하면 정적 리소스에 대한 Security Chain 은 존재하지 않는다.

-   최초 root 요청 ("/") 에 필터는 15개가 적용이 된다.
    ![root 요청 ("/")](https://lh3.googleusercontent.com/pw/ACtC-3df5Vabp2NwE5VJ1u4iujOOyK6-7C9FyumYOuyUx4Mu5cx_476Sc5LiuTgndvipGDiDVs5HTJfwYo3RtyCLFg7GRX0PuwfxhMHPbihG8TQ0_hVXHv6EbEZLbLV8IXuubA4OfYg5ymS3yVZa71uKZBCDrw=w1011-h638-no?authuser=0)

-   정적 리소스에 대한 필터는 0개이다.
    ![정적 리소스에 대한 필터](https://lh3.googleusercontent.com/pw/ACtC-3dKZtLEML45C2WdKgVosMJrXM79CHhQwIfGZHObRSxX7K7UtmXverdwNehrPV5GWo6u8HRUGkjcbyKiFcSX-TE1D4L81M9Opr-e3EPRmSf3Ffkj2XVQs8vtC3BBsKpLILAt-l8R-MNfZ3K_epYlsY12VA=w1007-h635-no?authuser=0)

-   그렇다면 configure(HttpSecurity http) 메서드 내부에서 http.authorizeRequests() 에다가 정적 리소스를 permitAll() 하는 것과, ignoring() 하는 것과 무엇이 다른가?

    ```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
    }
    ...
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    ```

-   실제 permitAll() 설정 한다면 이미 http.authorizeRequests() 설정안에 들어온 것이기 때문에 security filter 들을 타게 됨.
-   그리고 마지막에 AccessDecisionManager 에서 permitAll 설정을 기반으로 검사하게 됨.
-   이러한 과정들을 불필요 하게 사용하는 것이기 때문에 ignoring() 을 추천
