# 폼 인증 처리 필터: UsernamePasswordAuthenticationFilter
- 폼 로그인을 처리하는 인증 필터
    - 사용자가 폼에 입력한 username과 password로 Authentcation을 만들고 AuthentcationManager 를 사용하여 인증을 시도한다.
    - AuthentcationManager는 여러 AuthentcationProvider를 사용하여 인증을 시도하는데, 그 중에 DaoAuthentcationProvider는 UserDetailService를 사용하여 UserDetais정보를 가져와 사용자가 입력한 password와 비교한다.

- UsernamePasswordAuthenticationFilter 내부에서 AuthentcationManager를 통해 authenticate를 진행한다.

    ![UsernamePasswordAuthenticationFilter](https://lh3.googleusercontent.com/pw/ACtC-3dvFVs2wyvbY3nI43EqDZ2_rnL_4JIB4BTdCB0s7pk-4x2PtfaGWXlcaiRFCLG380bJweaW29N925zd2uadaMDu6VW0XZ5_pZLIGmBYUIeg324vbQ-W5U_4LxgN9TZxFnwBUVLf70dN4R6eExoiXs2mtw=w911-h350-no?authuser=0)

- UsernamePasswordAuthenticationFilter의 기본  authenticationManager 구현체인 ProviderManager를 사용하고, ProviderManager를 여러개의 AuthenticationProvider를 사용하여 인증을 위임하고 있다.

    ![ProviderManager](https://lh3.googleusercontent.com/pw/ACtC-3erTptfmps-I8hW_MinLOs_9QRgW0qazQLihLs1mCZhUk9BNT7G-Ext6uLzGcUnPEVIaUnCHPP8YOqvKSbhVsnt2aHE6olEHmjk5AqKSAk7WU8b-Z1aj0PEFPbo_HUBph8aSEqV-4ayzmL84ays1Ago2w=w831-h297-no?authuser=0)

- 여러개의 AuthenticationProvider중 DaoAuthenticationProvider에서는 최종적으로 userDetailsService를 사용하고, UserDetailsService에서 구현을 강제하는 loadUserByUsername를 통해 UserDetails를 가지고 온다.
    ![loadUserByUsername](https://lh3.googleusercontent.com/pw/ACtC-3diWVRp12SHdDvAMlRBuPrSk2ltrBoY74-3zH_beNK9AWEm36hWb98S1M97Uoi5jegTKJpMVJD1Ql8pAOswH9Om1JNDFQzyvUgnREAvXdDZkh7PEO7zuUETtL6J84JzvYmd1Yer77fYx_VLbJweihFjKA=w874-h220-no?authuser=0)


- 잘못된 요청이라고 보여주는 Filter는  UsernamePasswordAuthenticationFilter는 아니다. 인증을 담당하는 역할만한다.