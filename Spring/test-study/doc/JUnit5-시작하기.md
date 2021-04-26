# JUnit 5 시작하기

- 스프링 부트 프로젝트 만들기
  - 2.2+ 버전의 스프링 부트 프로젝트를 만든다면 기본적으로 JUnit5 의존성 추가 됨 
  
- 스프링 부트를 사용하지 않느다면?
    ```xml
    <dependency>
        <groupId>org.junit.jupiter</groupId> <artifactId>junit-jupiter-engine</artifactId>
        <version>5.5.2</version>
        <scope>test</scope>
    </dependency>
    ```

- 기본 어노테이션
  - @Test
  - @BeforeAll / @AfterAll
  - @BeforeEach / @AfterEach
  - @Disabled

- @BeforeAll 
  - 모든 테스트를 실행하기 전에 딱 한번 실행 
  - static 메소드로 작성해야함
  - 접근제어자 private은 X
  - return 값은 void

- @AfterAll 
  - 모든 테스트를 실행된 이후에 딱 한번만 호출 됨
  - @BeforeAll과 나머지 특징은 동일

- @BeforeEach
  - 각각의 테스트를 실행하기 이전에 각각 한번씩 실행 됨
  - static일 필요는 없음

- @AfterEach
  - 각각의 테스트 실행 후 각각 한번씩 실행 됨
  - static일 필요는 없음

    ```java
    class StudyTest {

        @Test
        void create() {
            System.out.println("create");
            Study study = new Study();
            assertNotNull(study);
        }

        @Test
        void create1(){
            System.out.println("create1");
        }

        @BeforeAll
        static void beforeAll(){
            System.out.println("beforeAll");
        }

        @AfterAll
        static void afterAll(){
            System.out.println("afterAll");
        }

        @BeforeEach
        void beforeEach(){
            System.out.println("beforeEach");
        }

        @AfterEach
        void afterEach(){
            System.out.println("afterEach");
        }
    }

    /* 실행 결과
    beforeAll
    beforeEach
    create
    afterEach
    beforeEach
    create1
    afterEach
    afterAll
    */

    ```
- @Disabled
  - 실행하고 싶지 않은 테스트일경우 제외 시킬수 있음

- JUnit5 는 메소드와 클래스에 public을 붙일 필요 없음

- JUnit5에 어노테이션들은 이전 버전 어노테이션들이랑 매핑이 됨
  - @BeforeAll / @AfterAll -> @BeforeClass / @AfterClass
  - @BeforeEach / @AfterEach-> @Before / @After
  - @Disabled -> @Ignore