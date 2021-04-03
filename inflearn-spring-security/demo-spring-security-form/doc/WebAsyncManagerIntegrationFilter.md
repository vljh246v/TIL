# Async 웹 MVC를 지원하는 필터: WebAsyncManagerIntegrationFilter

-   Spring MVC Async 기능에서 사용하는 쓰레드에서도 동일한 시큐리티 컨텍스트를 공유해서 사용할 수 있도록 지원해주는 필터

-   아래와 같은 controller 가 있다면 Callable 내부에 로직이 동작하기 전에 이미 쓰레드가 반환

    ```java
    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler(){
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Async Handler";
            }
        };
    }
    ```

-   request 에 대해 응답하는 쓰레드는 톰캣이 할당해준 nio 쓰레드이다.
-   내부 Callable은 별도의 쓰레드이다.
-   아래와 같은 log 유틸을 작성하고

    ```java
    public class SecurityLogger {

        public static void log(String message){
            System.out.println(message);
            Thread thread = Thread.currentThread();
            System.out.println("Thread name : " + thread.getName());
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("principal : " + principal);
        }
    }
    ```

-   "/async-handler" 로 요청을 보내서 logger 결과를 찍어보면 다른 쓰레드에서 동일한 시큐리티 컨텍스트를 참조할수 있다.

    ![/async-handler](https://lh3.googleusercontent.com/pw/ACtC-3ff3VHjSyTvWC7UiDCJcmSpHSuRdQFIQ6HB_WJRMrOmh9Zr-P4tiBvlwZ_yYBXqvwXekNfRJkjNkBBcV3Bip8s1Xz0Dz-Fjl8YCid7KLoK1tjuYkY11ugtwzK5gavTN44_YPQoKUDibuKJRvvydrnN50g=w1228-h724-no?authuser=0)

-   이런 식으로 시큐리티 컨텍스트는 쓰레드 로컬 변수라 다른 쓰레드에서는 접근할 수 없지만, 다른 쓰레드에서 시큐리티 컨텍스트를 공유할 수 있도록 해주는 필터가 WebAsyncManagerIntegrationFilter 이다.

-   PreProcess 에서 시큐리티 컨텍스트를 새로 생성한다.
-   PostProcess 에서 시큐리티 컨텍스트를 정리 한다.

# 스프링 시큐리티와 @Async
- @Async 어노테이션이 붙으면 해당 메소드를 새로은 thread로 실행한다.
- 일반적으로 @Async가 붙은 메소드 내부에서 SecurityContextHolder를 호출하면 null을 반환한다.

    ```java
    @Async
    public void asyncService() {
        SecurityLogger.log("Async Service");
        System.out.println("Async service is called");
    }
    ...
    public class SecurityLogger {

        public static void log(String message){
            System.out.println(message);
            Thread thread = Thread.currentThread();
            System.out.println("Thread name : " + thread.getName());
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("principal : " + principal);
        }
    }
    ```
- 새롭개 생성된 thread 에서는 SecurityContext가 공유가 안된다.