# SecurityContextHolder 와 Authentication

-   어플리케이션에서 인증을 거치고 난 후 인증된 사용자 정보를 스프링 시큐리티에서는 Principal 이라는 말로 나타냄
-   Principal 정보를 Authentication 객체안에 담아서 관리
-   Authentication 를 SecurityContext 로 감싸고 그리고 또 그것을 SecurityContextHolder 로 감싸서 관리함
-   SecurityContextHolder 는 SecurityContext를 제공해줌
-   SecurityContext 제공하는 기본적인 방법이 ThreadLocal임
-   ThreadLocal : 한 스레드 내부에서 쉐어하는 저장소
-   Authentication을 한 스레드 내에서 공유할 수 있음

    ```java
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
    ```

-   authentication 안에는 GrantAuthority 라는 타입에 컬렉션이 들어있음
    ```java
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    ```
-   principal은 사용자를 나타내는 정보
-   Authority(GrantedAuthority) 정보는 사용자 권한을 나타내는 정보
-   GrantedAuthority 안에 문자열은 principal이 가지는 권한을 나타내는 문자열
-   Authentication 인증을 거쳐 들어온 정보만 있음
-   SecurityContextHolder 반드시 인증이 된 정보가 들어감
