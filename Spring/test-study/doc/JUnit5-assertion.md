# JUnit 5 Assertion

- 검증하고자 하는 내용을 확인하는 기능
- assert에서 일반적으로 기대하는 값을 첫번째 파라미터, 실제 값은 두번째로 설정한다.
    ```java
    public static void assertEquals(Object expected, Object actual, String message) {
        AssertEquals.assertEquals(expected, actual, message);
    }
    ```
- 대표 사용 메소드
  - assertEqulas(expected, actual) : 실제 값이 기대한 값과 같은지 확인
  - assertNotNull(actual) : 값이 null이 아닌지 확인
  - assertTrue(boolean) : 다음 조건이 참(true)인지 확인
  - assertAll(executables...) : 모든 확인 구문 확인
  - assertThrows(expectedtype, executable) : 예외 발생 확인
  - assertTimeout(duration, executable) : 특정 시간 안에 실행이 완료되는지 확인
