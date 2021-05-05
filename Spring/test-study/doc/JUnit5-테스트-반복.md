# JUnit 5 테스트 반복하기

- 랜덤 값을 쓴다거나 타이밍에 따라 달라지는 조건이 있다면 테스트를 여러번 실행해서 검증할 경우가 있음
- @RepeatedTest(value={반복수}) 를 사용
- 테스트 메소드 인자로 RepetitionInfo를 받을 수 있음, 해당 info 를 통해 반복 횟수 등 기타 반복 관련 정보를 얻을 수 있음
- displayName, currentRepetition, totalRepetitions 내용을 사용할 수 있음
- @RepeatedTest(name = "{displayName}, {currentRepetition} / {totalRepetitions}")

- @ParameterizedTest 어노테이션도 사용 가능
  ![@ParameterizedTest](https://lh3.googleusercontent.com/pw/ACtC-3ffqjkU-XT9xzwb7cm7uxCXwGibxc1IPTxnDnUqTxjgjJvScxKA4eNoeIOv3g53TJLjCBg6wpGXu0J43qAHGma-YtFW9GmE0CbGG0ZDkdF7mnqtRB9hQqf21fAwYEPVHQlQlv3f7PO__2XYBhqIaqS0lw=w1084-h427-no?authuser=0)
