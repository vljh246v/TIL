# SecurityContext 영속화 필터: SecurityContextPersistenceFilter
- 여러 요청간 SecurityContext를 공유할 수 있는 기능
- 기본적으로 SecurityContextRepository 인터페이스에 위임을 해 기존에 존재하는 SecurityContext를 읽어옴
    ![SecurityContextPersistenceFilter](https://lh3.googleusercontent.com/pw/ACtC-3eknM0j-_wnCtVupkvZfRWFX9gEZ1ILrbnpCoAJvuzZHnfSKq02VGnOrkUHYpBYcUeEaX_-mS59dUmuGwIA3neJwXYUdJsR220aXOC1hoNTFIf5pfrJ7j14AFSH9UfQa4ej0Zkc9HK7RCdYj1ZVuxRLiA=w647-h164-no?authuser=0)
- SecurityContextRepository의 기본 구현체는 HttpSessionSecurityContextRepository
- 즉 http session 에서 읽어오게 됨
- 아무 정보도 없는 첫 요청이라면 비어있는 SecurityContext를 생성
- 많은 인증 필터들 보다 앞에서 동작해야 한다.
- 인증을 하기 전에 Principal정보를 담고 있는 SecurityContext가 있다면 다른 인증 과정을 거치는것은 불필요하기 때문에 다른 인증 필터들 보다 앞에 존재해야 한다.