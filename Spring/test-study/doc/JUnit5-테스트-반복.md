# JUnit 5 테스트 반복하기

- 랜덤 값을 쓴다거나 타이밍에 따라 달라지는 조건이 있다면 테스트를 여러번 실행해서 검증할 경우가 있음
- @RepeatedTest(value={반복수}) 를 사용
- 테스트 메소드 인자로 RepetitionInfo를 받을 수 있음, 해당 info 를 통해 반복 횟수 등 기타 반복 관련 정보를 얻을 수 있음
- displayName, currentRepetition, totalRepetitions 내용을 사용할 수 있음
- @RepeatedTest(name = "{displayName}, {currentRepetition} / {totalRepetitions}")

- @ParameterizedTest 어노테이션도 사용 가능
  ![@ParameterizedTest](https://lh3.googleusercontent.com/pw/ACtC-3ffqjkU-XT9xzwb7cm7uxCXwGibxc1IPTxnDnUqTxjgjJvScxKA4eNoeIOv3g53TJLjCBg6wpGXu0J43qAHGma-YtFW9GmE0CbGG0ZDkdF7mnqtRB9hQqf21fAwYEPVHQlQlv3f7PO__2XYBhqIaqS0lw=w1084-h427-no?authuser=0)
  
- @ValueSource 를 활용해서 다양한 값들을 넘겨 줄 수 있음
- 그 외에도 아래와 같은 어노테이션을 사용가능 
  - @ValueSource
  - @NullSource, @EmptySource, @NullAndEmptySource
  - @EnumSource
  - @MethodSource
  - @CvsSource
  - @CvsFileSource
  - @ArgumentSource

- 지정한 타입을 내가 원하는 타입으로 변경하기위해서 SimpleArgumentConverter 를 상속받은 구현체를 작성
  ![SimpleArgumentConverter](https://lh3.googleusercontent.com/pw/ACtC-3esk42ClPAeyP_8cFgRt6Uf-iO_ZINzkgtpbphYckEzo6WoUbhcm6gYdRv415E5MigikEGJOrhKiHTKyelUXwABlSVd-Jo4KtburhFbAhwtiLEcfHfn_X7MOu1EG_3RRtkJprUrXaJdaszGTBcHPDkKFw=w1148-h524-no?authuser=0)
  
- @CsvSource를 사용해서 다른 타입에 파라미터를 받을 수 있음
- SimpleArgumentConverter는 하나의 아규먼트에 대해서만 작성할 수 있지만 두개 이상일 경우 Aggregate를 사용해야함
- 그 이전에 인자값을 조합해 하나로 받아주는 ArgumentsAccessor를 사용해야 함
  ```java
  @DisplayName("스터디 만들기")
  @ParameterizedTest(name = "{index} {displayName} message={0}")
  @CsvSource({"10, '자바 스터디'", "20, 스프링"})
  public void parameterizedTest(ArgumentsAccessor argumentsAccessor){
    Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
    System.out.println(study);
  }
  ```
- 인스턴스를 만드는 작업조차도 줄이려면 Aggregator를 사용
  ```java
  @DisplayName("스터디 만들기")
  @ParameterizedTest(name = "{index} {displayName} message={0}")
  @CsvSource({"10, '자바 스터디'", "20, 스프링"})
  public void parameterizedTest(@AggregateWith(StudyAggregator.class) Study study){
    System.out.println(study);
  }
  
  
  static class StudyAggregator implements ArgumentsAggregator {
  
    @Override
    public Object aggregateArguments(ArgumentsAccessor argumentsAccessor,
        ParameterContext parameterContext) throws ArgumentsAggregationException {
      return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
    }
  }
  ```
 - Aggregator 제약조건은 public class 또는 내부 static class여야 한다.