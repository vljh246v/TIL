# Basic 인증 처리 필터: BasicAuthenticationFilter
- 해당 필터는 Spring Security 설정에 http.httpBasic() 설정을 추가하면 필터체인에 등록됨
- http basic 인증을 지원
- http basic 인증이란? 
  - http 표준에 정의된 가장 단순한 인증 기법
  - 접속시 서버에서 401(Access Denied) 가 떨어지면 Header 에 Authorize: Basic Base64(username:password)를 포함시켜 보낸다.
  - 인코딩은 단순 문자열 인코딩일 뿐이라서 패스워드를 그냥 보내는것과 다르지 않다.
  - 그렇기 때문에 SSH 통신을 필수로 한다.

- 단순하게 curl 명령어를 통해 (ex : curl -u demo:123 http://localhost:8080
) 인증된 정보를 추가해서 보낼 수 있음

    ![curl](https://lh3.googleusercontent.com/pw/ACtC-3f8XuwZwrPFwCa76noINbYgtER8byPwP7_gHCsrGWxjpf8OHjxsPR5bki-opJKs_rBUed5HJ1d82LiOUAs7-fyJw53O-MuFL2jNsft3yEMX_Jz34qAgx-Bb8R-cxEiPUZZix1eQpDr3RjuhsZLwg-fMWw=w929-h275-no?authuser=0)

- form 인증과 다른점은 form 인증 같은경우 인증이 완료 되면 세션에 인증 정보를 저장해두고 다음번 읽을때는 SecurityContextPersistenceFilter 에서 해당 내용을 읽어와서 인증을 검사하는 로직이 있다(stateful)
- 하지만 Basic 인증 같은경우 요청때 마다 헤더를 요구하고 있다.(stateless)
  
    ![stateless](https://lh3.googleusercontent.com/pw/ACtC-3fJIdBcrHxOZ4Cr1dF5erennTArfbbkNwB6lo6PuWgJLj406y33eXLUPKsk_tQlgJT9PaweVkPWoLlUGEyFDoa6r49poHJK8BDYCK7cv1B2l_-9vmEYGTdC5dZiC0VYZwgEaIphq9bBmh86klDPkjFcJA=w964-h281-no?authuser=0)