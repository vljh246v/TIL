# JUnit 4 마이그레이션
- 기본적으로 최신 스프링부트 프로젝트를 만들면 vintage 엔진이 exclusion 되어 있다.
- vintage 엔진이 있어야지 JUnit4에서 작성된 test를 사용할 수 있다.
- JUnit4 형태 테스트 코드 또한 JUnit5의 엔진으로 vintage 구현체를 가지고 실행함
- JUnit4 에서 사용하던 @Rule 어노테이션은 기본적으로 지원 불가 (@EnableRuleMigrationSupport 사용해야함)

    ![대응 어노테이션](https://lh3.googleusercontent.com/pw/ACtC-3fgzjIGKXWjP-M0TOffPHbcliHAgCePWcirgP_Llbo3oZZhjhLHhPf9HeT84mBVk0LQAaGmU99BjYMyd70F9VpsE37ttEbqprEygXz0zJ9G0i9zJb2VX8lj1YAz47nnJNZVnGk-I40e8kpvVI51zHyGpA=w1680-h488-no?authuser=0)