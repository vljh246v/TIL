# AuthenticationManager 와 Authentication


- AuthenticationManager :  Authentication을 만들고 인증을 처리하는 인터페이스

- SecurityContextHolder : Authentication을 정보를 담고 있는곳

    ```java
    public interface AuthenticationManager {
        Authentication authenticate(Authentication authentication)
                            throws AuthenticationException;
    }
    
    ```
- AuthenticationManager 인터페이스는 authenticate api 하나만 존재
- 인자로 받은 Authentication에 유저에게서 받은 인증 정보가 담겨져 있음, form 인증 같은 경우 username과 password 가 담겨져 있음
- 인자로 받은 Authentication이 유요한지 확인을 하고, 유요하다면 실제로 인증된 Principal을 포함하고 있는 Authentication 객체를 리턴해줌
- 보통 AuthenticationManager 구현체로는 ProviderManager를 사용한다. 
- 인증은 ProviderManager가 직접하지 않고 ProviderManager가 가지고 있는 Provider 들에게 인증을 위임해 인증을 진행한다.
    ```java
    for (AuthenticationProvider provider : getProviders()) {
        // ...
    }
    ```
    ![pProviderManager 안 Porviders](https://lh3.googleusercontent.com/pw/ACtC-3elkMqbNFItChd2QolNvzPbs5hfiZKIhWrYdlhysn84943S4LybnX97ey7X6bu_uLjrV2kcRI6n1vmfr8DRIPCh1GvpnDYCs4b0QEwLykeI-YOweOv64kvbLaTPIZUeIswsoS07mwgVSMzbArRGE5KJXQ=w940-h152-no?authuser=0)

- 실제로 인자로 들어온 Authentication 객체는 UsernamePasswordAuthenticationToken 타입이고, Authentication을 처리하는 Provider는 AnonymousAuthenticationProvider 타입이기 때문에 UsernamePasswordAuthenticationToken 형태의 Authentication을 처리할 수 없는  Provider다.
- 그럴 경우 ProviderManager가 가지고 있는 parent 로 해당 인증을 위임을 해 진행한다.
    ![ProviderManager의 parent](https://lh3.googleusercontent.com/pw/ACtC-3eFmmeyrcGfTXumhVs4ZTkkeMGpUg6koPipUvbbFRM_aajtvJHaxLFBUhkTG6-4SS5_ipUqsOuqXpKPRIl9Lt3SufEhhYGoQgGbbza4laqv5qXttZQjvjO-peYTesLx2Qi__WO6zGPScJVs6985CX8ikg=w1134-h202-no?authuser=0)

- parent 에는 provider로 DaoAuthenticationProvider 이 들어와 있음
    ![DaoAuthenticationProvider](https://lh3.googleusercontent.com/pw/ACtC-3fYX4rVisTaW_N7U0WgwbkAWFL2Eib2ueUDDdjgzjBDMNDfa6rTe4UkEES8Dw_lRmE5q5Krb_fAQbSB_oyzblQ9V-OoUuwWGYhC2LIAgSQzMMQcXCpLcumvLOUro42N7PzFZw0M--SRQMbFfd6E41KSdQ=w762-h176-no?authuser=0)


- DaoAuthenticationProvider 는 UsernamePasswordAuthenticationToken 형태의  Authentication 의 인증을 진행할 수 있음

- DaoAuthenticationProvider 에서 authenticate 를 진행하고 실제 retrieveUser 메소드 내부에서 this.getUserDetailsService() 불러와 UserDetails을 불러오는데 이때 불러와지는 UserDetailsService()가 소스코드에서 작성했던 [UserDetailsService](../src/main/java/til/demo/demospringsecurityform/account/AccountService.java) 가 된다.

    ![authenticate](https://lh3.googleusercontent.com/pw/ACtC-3ftR1VU1YCnMdZmk9Tzy_9Q8cV2ycwO-RUUDsPL2JA10dDjnXrJRYKJxly5AR7mv4yo8glW2bCqt4RAB3vKbhhDqNcevVD-7z38ir-RfFKXkCvL9T2LN7UCvjZRTGUyGBKb9UHJ700xchSlVUONj-NaYg=w1192-h548-no?authuser=0)

    ![retrieveUser](https://lh3.googleusercontent.com/pw/ACtC-3dQ-1YoQwPA_K5-sRmwzmFINOdLL-M0c3mOyD5_TP2rP0vb9K0r0hYzv1KjX7kKSVqnUcOqOvjIflR_8jf1zX-pj-nExgivYXT-5_WeLAY2BZIO8GJU8CRNVcZKPsabc5nXFyT9W1NCqOqk0GnHkwZK_g=w1106-h342-no?authuser=0)

- UserDetails객체는 우리가 생성했던 Account 객체와 스프링이 사용할 Principal정보를 중간에서 이어주는 어뎁터 객체라고 생각하면 된다.
- 우리가 입력한 Authentication은 입력한 Principal 값이 문자열이지만 실제 return 되는 Authentication 객체에 Principal은 [AccountService](../src/main/java/til/demo/demospringsecurityform/account/AccountService.java)에서 만들었던 UserDetails 객체이다.  
    ![UserDetails](https://lh3.googleusercontent.com/pw/ACtC-3fpbUTmPT06hrlYB_G8h-b_VYUP-ETECsOQ7zec85WRc5pCQg7On3MTV9KVjrE5PgGGf_Vs8OpXqLFC1nAGqsReG5iIUk9iDOMX5xFBMEgMduyJgO6Dyinf5n69U2O6sJLyWjUoAXhwpQquXmBXsGwUww=w1502-h514-no?authuser=0)