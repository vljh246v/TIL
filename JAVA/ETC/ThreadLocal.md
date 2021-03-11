# ThreadLocal - 1

- 자바 ThreadLocal 클래스를 사용하면 동일한 Thread에서만 접근할 수 있는 변수를 만들 수 있다. 
- 따라서 두 Thread가 동일한 코드를 실행하고 ThreadLocal 변수에 접근을 해도 서로의 ThreadLocal 변수를 볼 수없다.



## ThreadLocal 생성

- 자바 객체를 만드는 것과 같이 new 연산자를 통해서 ThreadLocal을 생성

  ```java
  private ThreadLocal threadLocal = new ThreadLocal();
  ```

- 해당 작업은 Thread당 한 버만 수행하면 된다.
- 각 Thread에는 자체 설정된 ThreadLocal 값만 표시



## ThreadLocal 값 사용

- Thread 로컬에 저장된 값은 get() 메서드를 통해 사용할 수 있다.

  ```java
  String threadLocalValue = (String) threadLocal.get();
  ```



## ThreadLocal 은 어떤 방식으로 동작할까?

- 예제

  ```java
  public class Context {
    public static ThreadLocal<String> local = new ThreadLocal<String>();
  }
  
  @RestController
  public class ThreadLocalController {
  
    private final ThreadLocalService threadLocalService;
  
    public ThreadLocalController(
        ThreadLocalService threadLocalService) {
      this.threadLocalService = threadLocalService;
    }
  
    @GetMapping("/thread")
    public ResponseEntity<String> thread(){
      Context.local.set(Thread.currentThread().getName());
      String threadLocalInfo = threadLocalService.getThreadLocalInfo();
      
      return ResponseEntity.ok(threadLocalInfo);
    }
  }
  
  @Service
  public class ThreadLocalService {
  
    public String getThreadLocalInfo(){
      return Context.local.get();
    }
  }
  
  ```

- 기본적인 구조
  - /thread 경로로 request가 오면 자신의 Thread 이름을  ThreadLocal 변수에 set 한다.
  - 다음은 ThreadLocalService 에서 ThreadLocal에 저장된 Thread 이름을 다시 반환한다.

- 그렇다면 현재 사용하고 있는 ThreadLocal 은 static 변수이기때문에 모든 Thread가 공유하는게 아닌가? 라는 의문이 든다.
- 실제로 ThreadLocal 가 가지고 있는 고유의 hashCode를 찍어보면 각각의 Thread에서 동일한 ThreadLocal 변수를 가지고 있는걸 확인할 수 있다.

```text
#############################################################################################################
thread name : http-nio-8080-exec-9, ThreadLocal : 2065699417
#############################################################################################################
#############################################################################################################
thread name : http-nio-8080-exec-1, ThreadLocal : 2065699417
#############################################################################################################
#############################################################################################################
thread name : http-nio-8080-exec-3, ThreadLocal : 2065699417
#############################################################################################################
#############################################################################################################
thread name : http-nio-8080-exec-6, ThreadLocal : 2065699417
#############################################################################################################
```



* 그렇다면 어떤 방식으로 ThreadLocal은 Thread 단위로 값을 저장하고 있을까?

* ThreadLocal 안으로 들어가보자

* **ThreadLocal 초기화**

  ```java
  public class ThreadLocal<T> {
    private final int threadLocalHashCode = nextHashCode();
  
    private static AtomicInteger nextHashCode =
      new AtomicInteger();
  
    private static int nextHashCode() {
      return nextHashCode.getAndAdd(HASH_INCREMENT);
    }
    ...
    static class ThreadLocalMap {
      ...
      static class Entry extends WeakReference<ThreadLocal<?>> {
        ...
      }
      private void set(ThreadLocal<?> key, Object value) {
        ...
      }
    }
  }
  ```

  - ThreadLocal을 생성할때 AtomicInteger 를 사용해서 고유한 threadLocalHashCode 를 생성해 주고 있다.

- **ThreadLocal Set**

  - 아래 코드는 ThreadLocal 에서 데이터를 가지고 오는 set() 메소드이다.

    ```java
    public class ThreadLocal<T> {
      ...
      public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
          map.set(this, value);
        } else {
          createMap(t, value);
        }
        ...
        ThreadLocalMap getMap(Thread t) {
          return t.threadLocals;
        }
      }
      ...
    }
    ```

  - 그런데 자세히 보면 Thread.currentThread(); 작업을 통해 현재 Thread를 가지고 온다

  - 그리고 getMap(t); 을 통해 현재 Thread에 있는 ThreadLocalMap 이라는 것을 가지고 오고 있다.

  - 그렇다면 ThreadLocalMap이 뭘까?

  - 이는 Thread의 소스코드를 보면 알 수 있다. 바로 Thread 에서 가지고 있는 ThreadLocalMap 이다

    ```java
    public
    class Thread implements Runnable {
      ...
      ThreadLocal.ThreadLocalMap threadLocals = null;
      ...
    }
    ```

  - 다시 set 메소드로 돌아가보자

  - 해당 메소드 내부에서 가리키는 this는 현재 Thread에서 데이터를 저장하고 있는 ThreadLocal이다.

  - 그렇다면 map.set(this, value) 메소드의 뜻은 '현재 Thread 내부에 있는 Map 자료구조에 ThreadLocal을 key 값으로 value를 저장해라' 라는 의미다.

  - 다시한번 정리하면 

    - 각 Thread별로 ThreadLocalMap 이라는 Map을 가지고 있다
    - key 값은 ThreadLocal 의 hashCode를 사용하고
    - ThreadLocal.set(T value) 를 통해 지정한 value를 삽입해라는 의미이다. 

  - 이렇게 된다면 static 변수인 ThreadLocal 을 많은 Thread가 동시에 사용한다고 해도, 각 Thread 가 가지고 있는 Map 에 key 값으로만 사용하고  value는 Thread가 각각의 ThreadLocalMap을 가지고 있기 때문에 저장되는 값이 다를 수밖에 없다.

- **ThreadLocal get**

  ```java
  public class ThreadLocal<T> {
    ...
    public T get() {
      Thread t = Thread.currentThread();
      ThreadLocalMap map = getMap(t);
      if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
          @SuppressWarnings("unchecked")
          T result = (T)e.value;
          return result;
        }
      }
      return setInitialValue();
    }
    ...
  }
  ```

  - ThreadLocal 에 set을 이해 했다면 동일한 방식으로 현재 Thread 의 Map 에서 ThreadLocal을 key 값으로 value를 가지고 오고 있다는 것을 알 수 있다.