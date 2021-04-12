# 스프링 빈 생명주기 메서드와 실행 순서

- 스프링 빈(Bean) 생명주기를 담당하는 메소드들을 알아보자

## 초기화 메소드
- 빈이 생성 되고 의존성 주입까지 완료된 후에 실행하는 초기화 메서드

### @PostConstruct
    ```java
    import javax.annotation.PostConstruct;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Component;

    @Slf4j
    @Component
    public class TempBean {

        @PostConstruct
        public void postConstruct(){
            log.info("@PostConstruct !!!!!!!!");
        }
    }

    ```

- 메서드 선언부에 @PostConstruct를 붙여주면 된다.
- 특징으로는 스프링 프레임워크에 의존적이지 않다는 특징이 있다. 

### InitializingBean 구현
- InitializingBean 인터페이스를 구현하는 방법이다. 
- 해당 방법은 스프링 프레임워크에 종속되는 인터페이스를 구현하는 방법이다.
- 그렇기 때문에 생성된 빈은 스프링 프레임워크에 종속된다.
- 외부에서 재사용을 하지 못한다.
    ```java
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.InitializingBean;
    import org.springframework.stereotype.Component;

    @Slf4j
    @Component
    public class TempBean implements InitializingBean {
        // ...
        @Override
        public void afterPropertiesSet() throws Exception {
            log.info("InitializingBean afterPropertiesSet !!!!!!!!");
        }
        // ...
    }

    ```

### @Bean(initMethod)
- @Bean 어노테이션을 이용해 초기화 메소드를 직접 지정해 줄 수 있다.

    ```java
    @Configuration
    public class BeanConfig {

        @Bean(initMethod = "beanInitMethod")
        public TempBean tempBean(){
            return new TempBean();
        }
    }

    ...
    
    @Slf4j
    public class TempBean  {
        public void beanInitMethod(){
            log.info("@Bean(initMethod) !!!!!!!!");
        }
    }
    ```

## 소멸 메서드
- applicantcontext 가 사라질 때 비니 호출할 메소드


### @PreDestroy
- @PostConstruct 유사하게 어노테이션을 추가하면 된다.
- @PostConstruct 마찬가지로 스프링에 의존적이지 않다.

    ```java
    import javax.annotation.PreDestroy;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Component;

    @Slf4j
    @Component
    public class TempBean  {

        @PreDestroy
        public void preDestroy(){
            log.info("@PreDestroy !!!!!!!!");
        }
    }
    ```

### DisposableBean 구현
- InitializingBean 인터페이스와 마찬가지로 destroy 메소드를 재정의하는 방법
- 스프링프레임워크에 의존적이게 된다.

    ```java
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.DisposableBean;
    import org.springframework.stereotype.Component;

    @Slf4j
    @Component
    public class TempBean implements DisposableBean {
        // ...
        @Override
        public void destroy() throws Exception {
            log.info("DisposableBean destroy !!!!!!!!");
        }
        // ...
    }
    ```


### @Bean(destroyMethod)
- @Bean 어노테이션에 있는 destroyMethod 옵션을 사용
- 직접 메소드를 지정해서 정의

    ```java
    @Configuration
    public class BeanConfig {

        @Bean(destroyMethod = "beanDestroyMethod")
        public TempBean tempBean(){
            return new TempBean();
        }
    }

    ...

    @Slf4j
    public class TempBean {

        public void beanDestroyMethod(){
            log.info("@Bean(destroyMethod) !!!!!!!!");
        }
    }
    ```

## 호출 순서
- 예제를 통해 알아보자

    ```java
    import javax.annotation.PostConstruct;
    import javax.annotation.PreDestroy;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.DisposableBean;
    import org.springframework.beans.factory.InitializingBean;

    @Slf4j
    public class TempBean implements InitializingBean, DisposableBean {

        public void beanDestroyMethod(){
            log.info("@Bean(destroyMethod) !!!!!!!!");
        }

        public void beanInitMethod(){
            log.info("@Bean(initMethod) !!!!!!!!");
        }

        @Override
        public void destroy() throws Exception {
            log.info("DisposableBean destroy !!!!!!!!");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            log.info("InitializingBean afterPropertiesSet !!!!!!!!");
        }

        @PostConstruct
        public void postConstruct(){
            log.info("@PostConstruct !!!!!!!!");
        }

        @PreDestroy
        public void preDestroy(){
            log.info("@PreDestroy !!!!!!!!");
        }
    }

    ...

    @Configuration
    public class BeanConfig {

        @Bean(destroyMethod = "beanDestroyMethod", initMethod = "beanInitMethod")
        public TempBean tempBean(){
            return new TempBean();
        }
    }

    ```
### 실행결과
- 초기화
    ![초기화](https://lh3.googleusercontent.com/pw/ACtC-3d9fEAfDmIWJOgNXKs5Wh2kYqEBTh7XjsyV84gnMMK7lcC-zY67v61M9tIh7Pe28DfogL_a0Y5jIBGpRCJaO4eQeU7hVtDnF4YkMCOujCNX45uIn-NijYTmV5yjRgoElHI-W9eTiMlJVc03sdW4uVX86g=w1190-h71-no?authuser=0)

- 소멸
    ![소멸](https://lh3.googleusercontent.com/pw/ACtC-3fuI7dmJOjdk9FXrNOw38NkS4kHSltn85qN9ufcQSjwPcxFpEFb4Ge8hq8or7QxgF4KmzSONvLK0sAaQSglgadx1B1bypt2uF68-07HM9VvyiDFWC_n-lzc4S1bVRG22mZYRMFBTR4_yKyhKGQkqiC6Kg=w1090-h71-no?authuser=0)


## 참고
[참고](https://madplay.github.io/post/spring-bean-lifecycle-methods)