# JUnit 5 테스트 이름 표시하기 20210427

- 테스트 결과를 표시할때 기본적으로 메소드 이름이 표시됨

    ```java
    @Test
    void create() {
        System.out.println("create");
        Study study = new Study();
        assertNotNull(study);
    }
    ```
    ![테스트이름출력1](https://lh3.googleusercontent.com/pw/ACtC-3eXs5L87dFjc3ckPSxc7omtuEdvIwEq5lbAb11m82r-I29R2AdG5guDFtiSEUBtOkL0iIR3pGpVJwUYRJVzj2lztWxObnHrKCjm1tsxs24UEsAcJUgfFwnL7QAlK8PqdHH7ncwVSwAi7zBzBXYQ0t5q7Q=w254-h71-no?authuser=0)

- 이름을 변경하기 위해 @DisplayNameGeneration 어노테이션을 사용할 수 있음
- 클래스와 메소드 둘다 사용 가능
- 클래스에 사용할 경우 클래스 내부 테스트 메소드에 전부 적용
- 여러 옵션들이 있음
  - DisplayNameGenerator.ReplaceUnderscores.class : 언더스코어를 공백으로 변경
    ![DisplayNameGenerator](https://lh3.googleusercontent.com/pw/ACtC-3eJZNuW8ZAAPZPROZJIsJaXWKB2DViggZ5uK1WBs8CaYvNhcfaGuLnKLa97hfBAC22Dc6mAYjeZcTtms1foyEPNLFCFaMTqTyhFrdL2wY7DC9OjFdXeXxltycW4ZTvwSnjko7fnS4YAVFlyTACUq-x88A=w556-h108-no?authuser=0)

- @DisplayName 어노테이션이 있음 : @DisplayName("테스트~")
- 