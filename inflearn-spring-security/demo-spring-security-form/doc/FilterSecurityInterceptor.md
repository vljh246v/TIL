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