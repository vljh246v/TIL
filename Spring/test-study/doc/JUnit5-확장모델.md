# JUnit 5 확장 모델

- JUnit 4 보다 확장 모델이 간편해짐
- 4에서는 @RunwWith(Runner), TestRule, MethodRule로 나누어져 있었지만 5 에서는 Extension 으로 통합

- 테스트 속도가 지정된 속도이상 걸릴경우 알림을 주는 확장 예제
    ```java
    import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
    import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
    import org.junit.jupiter.api.extension.ExtensionContext;
    import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
    import org.junit.jupiter.api.extension.ExtensionContext.Store;

    public class FindSlowTestExtension implements BeforeTestExecutionCallback,
        AfterTestExecutionCallback {

    private static final long THRESHOLD = 1000l;

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Store store = getStore(context);
        String testMethodName = context.getRequiredTestMethod().getName();

        long start_time = store.remove("START_TIME", Long.class);
        long duration = System.currentTimeMillis() - start_time;

        if(duration >  THRESHOLD){
        System.out.printf("Please consider mark method [%s] with @SlowTest.\n", testMethodName);
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }

    private Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();

        return context.getStore(Namespace.create(testClassName, testMethodName));
    }
    }

    ```

- 선언적 방법
```java
    // 선언적 방법
    @ExtendWith(FindSlowTestExtension.class)
    class StudyTest  {...}
```

- 프로그래밍적 방법
  - 사용이유 : 선언적 방법에서는 인스턴스를 커스터마이징 할 수 없음
    ```java
    public class FindSlowTestExtension implements BeforeTestExecutionCallback,
        AfterTestExecutionCallback {

        private long THRESHOLD;
        public FindSlowTestExtension(long THRESHOLD){

            this.THRESHOLD = THRESHOLD;
        }
    }

    ...

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension
        = new FindSlowTestExtension(1000);
    ```