# JUnit 5 junit-platform.properties

- JUnit 설정파일을 제공해서 기능들을 설정할 수 있음
- 위치는 test/resources/junit-platform.properties (인텔리j 에서는 테스트의 리소스디렉토리로 설정해줘야함)
- 설정값 예제
  - junit.jupiter.testinstance.lifecycle.default = per_class : 테스트 인스턴스를 per_메소드일지 클래스일지 일괄적용하고 싶은 설정을 명시
  - 확장팩 자동 감지 기능 : junit.jupiter.extensions.autodetection.enabled = true
  - @Disabled 무시하고 실행하기 : junit.jupiter.conditions.deactivate = org.junit.*DisabledCondition
  - 테스트 이름 표기 전략 설정 : junit.jupiter.displayname.generator.default = org.junit.jupiter.api.DisplayNameGenerator$ReplaceUnderscores