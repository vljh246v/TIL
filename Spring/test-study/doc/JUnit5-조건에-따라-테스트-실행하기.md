# JUnit 5 조건에 따라 테스트 실행하기

- 테스트코드를 특정한 OS, 특정한 java 버전, 환경/시스템 변수 값에 따라 실행하거나 실행하지 않아야 할때 사용
- assumeTrue 를 사용해서 설정 가능
    ```java
    String test_env = System.getenv("TEST_ENV");
    System.out.println(test_env);
    assumeTrue("LOCAL".equalsIgnoreCase(test_env));
    ```
- 또는 assumingThat을 사용해서 특정 조건에서만 테스트를 실행 가능
    ```java
    assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
      System.out.println(test_env);
      Study actual = new Study(10);
      assertThat(actual.getLimit()).isGreaterThan(0);
    });
    ```

 - 어노테이션을 사용해서도 설정 가능
    ```java
    @Test
    @DisplayName("스터디 만들기1 (/ω＼)")
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void create_new_study() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        Study actual = new Study(100);

        assertThat(actual.getLimit()).isGreaterThan(0);
    }


    @Test
    @DisplayName("스터디 만들기2 (/ω＼)")
    @DisabledOnOs(OS.MAC)
    void create_new_study_again(){
        System.out.println("create1");
    }
    ```
    ![테스트 결과](https://lh3.googleusercontent.com/pw/ACtC-3de05NKvKVBW0-UMY3Jiy22I_h3aypZa-_WvfQPaE-ZKVYA93nPP3SSJSE2zQ70T30czaXwsqWUVMvKvHAh9IUB0SYr2OWnt2nPu-108B0IE7RQPz3NEvR441yfhhCqAZAZ1GJ4y1nLk56S7gx8OsAFLg=w608-h98-no?authuser=0)

- @EnabledOnJre를 사용해서 자바 버전을 지정할 수 있다.
    ```java
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
    ```

- @EnabledIfEnvironmentVariable 통해 assumingThat / assumeTrue 와 같은 설정을 어노테이션으로 지정해 줄 수 있음
    ```java
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    ```