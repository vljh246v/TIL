# Authentication과 SecurityContextHodler

- 인증이된 Authentication 객체는 SecurityContextHodler에 들어가서 애플리케이션 전반에 거쳐서 사용할 수 있게 됨
    ```java
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    ```
- Authentication은 언제 SecurityContextHodler에 들어갈까?
- 크게 두가지 Filter 가 Authentication 객체를 SecurityContextHodler에 넣어줌


**UsernamePasswordAuthenticationFilter**
- 폼 인증을 처리하는 시큐리티 필터
- 폼 로그인을 처리할때 AuthenticationManager 에 authenticate를 실행함
    ![authenticate](https://lh3.googleusercontent.com/pw/ACtC-3eXZhsLUnH4SXHoSeohp9NF-2X_FNK-HCT7nejNTL7HAg17-I-2GKWkbwCjGoksmS9MwiShVWiBRGhiWyxG7m5tIihz_33-tpR0nZEytQ_bAXEmSAy-MzWH2OC96nFO7OfNvJKimWue1uCEDlg8ZfnFQA=w1680-h662-no?authuser=0)

- 실제 attemptAuthentication 메소드는 부모인 AbstractAuthenticationProcessingFilter.doFilter() 내부에서 한 부분이다.
    ![doFilter()](https://lh3.googleusercontent.com/pw/ACtC-3f1etIomm5uRgKidrOjTSqy9U8wPbflz-WA60KBSOW8ZCDhDzpQayGVj7PoAucCH6xG-3RqnHF6xuHw5XNCZMsJWD_NaUsP480EBKfCWqJSElpfTLJ_OkQBNjZW44tPE18o4W0mCg34uModbzRCy3J2fQ=w1624-h816-no?authuser=0)

- 그리고 AbstractAuthenticationProcessingFilter.doFilter() 내부에서 successfulAuthentication 으로 접근해보면 해당 메소드 내부에서 드디어 인증된 Authentication를 집어넣고 있다.
    ![](https://lh3.googleusercontent.com/pw/ACtC-3ftICG4A5uaNLBX5Dy9F4hM5VXKEfhNd5eroPvgoXrAOllMOSzC5xkoaorwVXJ6204s09Qr4kIhrKcdh6suzJN1YN-FX7sqvHvjoCbbdBREJ_LlX2PDoVAMDF_e30mBHNGlB-k-Sk3B0lBwf6Lyu_jckQ=w1680-h474-no?authuser=0)
- 인증이 종료되고 나면 우리가 최초 가고자 했던 요청 (여기서는 /dashboard)으로 다시 보냄
    ![dashboard](https://lh3.googleusercontent.com/pw/ACtC-3dMgRza2J1KzWqytrS4JbFa0FrbrHYMCSOYdsN7s5xxm1g-61BQoh105QANbQav5fTdVAI8AW07nDDZNSuIH5bsX6V9vpbQ-Z6HcKPdpNwbqg13tLm7-8jUhCpwKb83w4_ZRE2F90H9dhAW9mPGFYApHg=w1124-h334-no?authuser=0)


**SecurityContextPersistenceFilter**
- 한번 인증 처리된 Authentication은 다른 어떻게 다른 요청에도 동일하게 사용할 수 있는걸까?
- 매번 요청마다 SecurityContextHolder 에 기존에 캐싱된 SecurityContext 를 복구하려고 함
    ![SecurityContext](https://lh3.googleusercontent.com/pw/ACtC-3fAoBwhzimf7BfxAKKygkC7OpvHqqFzlFj--0hHe8dQbgGQfQOpo--IV_R4XUxosV_SuSZLz9L9n5AIYZBg-P9onYN7F0yz74JPXHGFTX2A2mZiJDvpM6ffwQubHMtXfxy6cyUwi3cY2ejSxWhvNcIzRA=w1606-h326-no?authuser=0)
- 최초 인증시에는 인증 정보가 없음
    ![Null authentication](https://lh3.googleusercontent.com/pw/ACtC-3emVK3g_OnI_VE9CbexRSXGkeqbiuj8nMplt5hEtLyFvfY7owq4331yz3VYaLEfhEEtwTZlOCp2MoS_sLuv6FqXY7H76TbrSoeFekgy-X0l3yCh34VPqzq1GnUOv7U01Za1pfD8OcIEVSdJ6FTS5CUBIQ=w1598-h138-no?authuser=0)

- 인증을 확인하기 위해 HttpRequestResponseHolder 에서 컨텍스트를 가지고 옴
- 이때 사용되는 repo는 HttpSessionSecurityContextRepository가 사용됨
    ![HttpSessionSecurityContextRepository가](https://lh3.googleusercontent.com/pw/ACtC-3dpt0OHpCcTtVX_UIQdTsmW8O_c-VP3-kqbzdQ_dtndUrJlDeUvrMvFRnsutJKsmOIK_7wmYqkWRLvYOiEsywVQHqxoUErUAl8mLuNACOskRUYSaEsrIfrCvbJl89i364CPR1sRagkZyNdUMnqVWlFtIg=w1498-h198-no?authuser=0)

- HttpSessionSecurityContextRepository은 세션에 저장된 Authentication 객체를 가지고 옴
- 매 요청마다 Authentication를 채워주고 세션에 저장하고 비워주고 하는 역할을 SecurityContextPersistenceFilter가 하게됨
- 결국 그 뜻은 session 단위로 인증 정보를 관리한다는 말