# 로그아웃 처리 필터: LogoutFilter
- 로그아웃을 처리하는 필터
- LogoutHandler :  여러개의 logoutHandler 를 감싸고 있는 Composite 객체
- LogoutSuccessHandler : 로그아웃 처리 후 어떻게 할것인지 정의
  ![LogoutFilter](https://lh3.googleusercontent.com/pw/ACtC-3eTg6SrWQe48NFpRA_blogZfo6noJn5tZnIrCfXOgQl9blP35WzH76oNaRMHRn5S754CFLTJcOF1ugyqpmhCv7Meg3JEwBgBdZR8M-5MLH9Tkf4nmzzxiYAFESuO6sBPhy9D9tt-hTrqbu8R9_Tud3OiQ=w757-h328-no?authuser=0)

- 기본 LogoutSuccessHandler는 SimpleUrlLogoutSuccessHandler
- 로그아웃 post 요청시에만 동작함
  ![로그아웃 요청시](https://lh3.googleusercontent.com/pw/ACtC-3cStK4LTW6Y_ob2SqTJR1KkNl-JXw3W2Cojp-MSpucHbRyJT3vWj3xqdKWpE0w0u79WeHBnhbMTl5HlKh_ije7uux6V8xoL92sySCNeFlx8p6kta-KmIZJvLQnG4FWMj_FO753NV6ikF_kKeMivXcikMQ=w843-h287-no?authuser=0)

- 로그아웃 요청시 LogoutHandler에 기본적으로 등록된 logoutHandler들이 존재
  ![LogoutHandler에](https://lh3.googleusercontent.com/pw/ACtC-3dE4HG-V-Df7rajrBi45Ass-K-wZUodKrlhS36wisaz4M4w3iWnNDxfFQf2ohSrg2U1pdRr6iiTziUssfNlae_ZnqeH3x4dXdY3qfQeHaQ_OoI32-ykAUzaJXhQH_9JNMlQLSYGu5HTdqQGwM8T1B_8kA=w764-h385-no?authuser=0)


- 로그아웃 config 설정  
  ![로그아웃 config 설정](https://lh3.googleusercontent.com/pw/ACtC-3f-cZ6YNZP_3829pHYCAhcwNYeaDJTIDKj6O6eFmSV6dnKh-b6Q0cD8AO-JUGz1iBolBeM7m1KmTJ2CCxTN_WtP76JV7GrnS_kX3wftvKa8wwAQICbryLsUX6S-CalGvfJgx8o82SWhj-aNUMxeLqUwoQ=w736-h144-no?authuser=0)