# **FilterSecurityInterceptor**

-   AccessDecisionManager 를 사용하여 Access Contorl 또는 예외 처리 하는 필터
-   시큐리티 필터중 대부분의 경우 가장 마지막에 위치해있다.
-   인증을 다 거친 후 설정해둔 ConfigAttribute를 확인해야 하는 단계에서 AccessDecisionManager를 통해 확인하는 필터이다.
-   아래는 AccessDecisionManager를 사용해서 access control 하는 영역
    ![FilterSecurityInterceptor](https://lh3.googleusercontent.com/pw/ACtC-3dCup0aYKyat3s4y94-4ejZOQc8yc2dW-GDGu8e9wu4bHGNhsteUaynASRjJWE7sge7Ju4_Gx-1Qt0AQk5wsyK8GwjcNqq0zKNhf5slWjGtJJ2Xk3RKTtJUMj78CZAWWVOLJ79AGDuxm_0j7e5BHaUjRQ=w890-h122-no?authuser=0)

-   특정 object(경로) 에 특정 authenticated(사용자) 의 attributes 를 허용 하는지 확인하는 과정
-   아래 사진에는 object("/") 에 대해서는 authenticated("anonymousUser") 의 접근을 attributes("permitAll") 허용하는 상황이다.
    ![permitAll](https://lh3.googleusercontent.com/pw/ACtC-3dTHofgcskSiDQmKD0Z7OcwEPWcgCLLOHPm0sXVm08LcaEbcuV_kWXYlYWgZxN-lpXbYboBtL_VofA0g_kEKvHv_wGEgjs0MsbdWdWQ-P7vGz5rT1XM9rT7WxY0ejdfW1XSQeyPyTf6n1kMMKf9t1SHhw=w945-h613-no?authuser=0)

-   attributes 값이 "authenticated" 일때는 "anonymousUser" authenticated의 접근을 허락하지 않고 exception (AccessDeniedException) 을 발생시킨다.
    ![](https://lh3.googleusercontent.com/pw/ACtC-3fDo9khgVXb0VVxx0MS-vSIQt2K8GDuto9JB3jasSN5L0hkWr_BA_cTy1iaQ2kOSztQ8Ziv4mfZIWHIlQ9bDYllc-yVbXXriTUNWTDR7WfUwq3PD2hn2SMm6Hc5G9IR7ZjGyidykaFaj1BrdGS7npOPzg=w931-h877-no?authuser=0)

-   exception을 처리하는 필터에서 login 화면을 호출한다.


- HTTP 리소스 시큐리티 처리를 담당하는 필터 AccessDecisionManager를 사용하여 인가를 처리한다.
- http.authorizeRequests() 에서 설정하는 내용을 가지고 확인
- matchers 
  ```jav
     http.authorizeRequests() // 어떤 식으로 '인가' 할지
        .mvcMatchers("/", "/info", "/account/**", "/signup").permitAll()
        .mvcMatchers("/admin").hasRole("ADMIN")
        .mvcMatchers("/user").hasRole("USER")
  ```

- hasRole, hasAuthority : ROLE_ 를 접두어로 쓰기 때문에 hasRole을 사용
- anyRequest().anonymous() 를 통해 익명 사용자만 접근가능하게 한다.
- anyRequest().rememberMe() : rememberMe 기능을 사용한 사용자를 인증하겠다.
- anyRequest().fullyAuthenticated() : rememberMe 인증이 된 사용자 같은 경우 다시 인증을 요구함