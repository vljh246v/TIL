# DelegatingFilterProxy와 FilterChainProxy

- 어떤 요청은 서블릿컨테이너가 받음
- 서블릿 컨테이너는 서블릿 스팩을 지원하고 현재 사용중인 컨테이너는 톰캣임
- 서블릿 스팩에는 [필터](https://tomcat.apache.org/tomcat-5.5-doc/servletapi/javax/servlet/Filter.html)라는 개념이 있음
- 필터 구현체중 스프링이 제공해 주는것이 DelegatingFilterProxy가 있음
- FilterProxy는 스스로 필터 역할을 하는것이 아니라 스프링 IoC에 들어있는 특정 bean 에게 역할을 위임함
- FilterChainProxy 에게 위임을 한다고 볼 수 있음
- DelegatingFilterProxy 는 스프링에 들어있는 어떤 bean 에게 위임하는지 명시를 해주어야 함
- 스프링부트를 사용할 때는 자동을 등록이 된다.(SecurityFilterAutoConfiguration)

- 