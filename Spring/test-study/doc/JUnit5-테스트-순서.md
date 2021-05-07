# JUnit 5 테스트 순서

- 테스트 메소드는 내부적으로 순서가 있긴 있음
- 하지만 그 순서는 JUnit 업데이트에 따라서 언제든지 변경될 수 있음
- 또한 하나의 단위 테스트는 다른 단위 테스트에 의존적이면 안되기 때문에 순서가 기본적으로는 상관없고, 중요하게 생각하지 않아도 됨
- 하지만 시나리오 테스트나 특정 순서가 필요할 때가 있다면 순차적으로 실행될 필요가 있음
- 그렇다면 테스트 인스턴스를 하나만 생성하는 설정 - @TestInstance(Lifecycle.PER_CLASS) 과 함께 @TestMethodOrder를 사용한다.( 꼭 같이 써야 하는건 아님)
- @TestMethodOrder 에는 MethodOrderer 구현체를 넘겨주어야 한다.
- 기본적으로 3개의 구현체가 있다.
  - Alphanumeric
  - OrderAnnotaion
  - Random
- @TestMethodOrder(MethodOrderer.OrderAnnotation.class) 를 사용할 경우 @Order 라는 어노테이션을 메소드에 붙여주어야 하는데, 이때 스프링에서 제공하는 Order 어노테이션과 구별이 필요하다.
- 낮은 값일수록 더 높은 우선수위가 있다.