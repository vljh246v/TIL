# 1 JUnit 5 소개

- 자바 개발자가 가장 많이 사용하는 테스팅 프레임워크
    ![junit구조](https://lh3.googleusercontent.com/pw/ACtC-3cmPlps7f7h-sQvvkNarNpeZtOwB7KfyRS4OjrHee5_cdOnj-YiQMTconuUj-KZDmf0v0PTNOnzH0oDRHatzI0emYtjXwu-ijactXrzP5yPKBVxPrQrd1Rw9O1FslNa-qEeV5-CCxa9ZpKyWuVDTbX9MA=w705-h347-no?authuser=0)

- Platform  : 테스트를 실행해주는 런처 제공. TestEngine API 제공
- Jupiter: TestEngine API 구현체로 JUnit5를 제공
- Vintage : JUnit4 와 3을 지원하는 Testengine 구현체

- [JUnit5 가이드](https://junit.org/junit5/docs/current/user-guide/)

- JUnit5를 공부해야 하는 이유 : Spring Boot 가 버전 2.2 로 올리면서 기본 JUnit 기본 버전으로 5로 올렸음

    ```java
    class AppTest {

        @Test
        public void create(){
            App app = new App();
            assertNotNull(app);
        }
    }
    ```
- main 메소드가 없어도 JUnit Platform 을 사용해서 @Test 어노테이션이 붙은 메소드를 실행해줌