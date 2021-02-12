# ThreadLocal
- 변수의 스코프를 쓰레드 범위로 선언하도록 하는 장치
- 한 쓰레드내에서는 변수 하나를 공유할 수 있다.
    ```java
    // 예제
    public class AccountContext {
        private static final ThreadLocal<Account> ACCOUNT_THREAD_LOCAL
            = new ThreadLocal<>();

        public static void setAccount(Account account){
            ACCOUNT_THREAD_LOCAL.set(account);
        }

        public static Account getAccount() {
            return ACCOUNT_THREAD_LOCAL.get();
        }
    }
    ```

- SecurityContextHolder의 기본전략이 ThreadLocal임