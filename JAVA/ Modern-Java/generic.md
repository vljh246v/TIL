# 1. 제네릭 기본 이해
- 자바 1.4 까지는 컬렉션에서 어떤 데이터 타입이 추가 혹은 조회 되는지 개발자가 미리 알고 있다는 가정하에 컴파일을 수행한다.
    ```java
    List nameList = new ArrayList();
    for(int i = 0; i< namelist.size(); i++) {
        String name = (String)namelist.get(i);
    }
    ```
- 제네릭을 이용해서 타입을 지정하면 컴파일러가 어떤 데이터 타입이 추가 혹은 조회 될지 인식한다.
- 문제 발생 소지가 있을 경우 컴파일 오류를 발생시킨다.
- 데이터를 저장할 때도 다른 데이터 타입을 추가하면 컴파일 오류를 발생시킨다.
    ```java
    List<String> nameList = new ArrayList();
    nameList.add(new Integer(2)); // 컴파일 에러!
    ```
- 제네릭을 이요하면 코드 품질이나 가독성이 높아진다.
  ```java
  List<String> nameList = new ArrayList();
  List nameList = new ArrayList();
  ```
- 하위 프로그램 호환성을 위해서 아직까지 제네릭을 사용하지 않아도 코딩이 가능하다.
- **제네릭을 사용하면 얻을 수 있는 장점**
  - 사용하고자 하는 데이터 타입을 명확히 선언할 수 있다.
  - 정확한 데이터를 사용했는지 엽를 컴파일하는 시점에 확인할 수 있다.
  - 클래스 캐스트가 필요 없기 때문에 불필요한 소스 코드를 줄일 수 있다.
  - 객체 선언 시 명확한 데이터를 지정할 수 있기 때문에 소스 코드의 가독성을 높일 수 있다.

## **1.1 for each 문장의 등장**
- 제네릭의 등장은 명확한 데이터를 지정할 수 있어서 컬렉션 프레임워크 기능 개선의 기반이 되었다.
- 자바 가상 머신이 해당 컬렉션에 어떠한 데이터를 포함했을지 추론할 수 있어서 for each 문을 사용할 수 있다.
  ```java
  List<String> nameList = new ArrayList();
  for(String name : nameList){
      ...
  }
  ```

- 물론 배열과 같은 타입이 명확할 경우에는 제네릭이 없어도 for each 문 사용가능

## **1.2 오토 박싱.언박싱 등장**
- 기본 데이터 타입에 대한 오토 박싱/언박싱 기능 역시 제네릭에 기반을 두고 있다.
- 제네릭이 추가되면서 이를 추론할 수 있어서 데이터를 자동으로 변환할 수 있게 된 것이다.
- 데이터 변환(박싱/언박싱)이 일어날 수 밖에 없는 구조라면 개발자가 직접 처리하기보다는 언어적으로 처리하는것이 훨씬 안전하다.

# **2. 제네릭과 클래스.메서드 설계**
- 대부분의 컬렉션 프레임워크가 제네릭을 기반으로 설계되어 있기 때문에 한번 보는걸 추천!
- 인터페이스 혹은 클래스에 제네릭 타입을 지정한다는 것은 제네릭을 이용해서 하위 타입(sub typing)을 지정한다는 의미이다.
  
## **2.1 제네릭 적용 전**

- 제네릭 타입 없이 선언한 컬랙션 객체를 로우 타입(Raw Type)이라고 한다.
- 제네릭의 로우 타입은 제네릭을 설계된 클래스 임에도 불구하고 제네릭을 사용하지 않는 경우를 의미한다.

    ```java
    public class CustomList {
        private List list = new ArrayList();

        public void add(Object element){
            list.add(element)l
        }

        public Object get(int index) {
            return list.get(index);
        }
    }
    ```

## **2.2 제네릭 타입 클래스**

- 제네릭을 이용해 컬렉션에 담겨질 수 있는 클래스 타입을 제한할 수 있다.
- '<>' 구문 안에 원하는 개수만큼 타입을 기술하면 됨
  ```java
  class ClassName<Type1, Type2, Type3 ... TypeN> {}

  // ex
  class TestSuperList<A, B, C> {

  }

  public class TestSubList <A, B, C> extends TestSuperList<A, B, D> { // 컴파일 에러

  }
  // java: cannot find symbol
  // symbol: class D
  ```
- '<>' 부분을 공식용어로 Type Parameter 라고 한다.
- 많이 사용하는 T, K, V 등등 공식 예약어는 아니다.
- 제네릭 코드를 쉽게 분석하기 위해서는 타입 파라미터를 특정한 클래스로 변경해 보는 것이다.
  ```java
  class TestSuperList<Integer, Double, String> {

  }
  ```
- 클래스를 사용할때 외부에서 데이터 타입을 받아 오기 위한 것임을 알 수 있다.
- 제네릭에서 사용하는 타입 파라미터 표현식에는 몇 가지 규칙이 있다.
  - 자바의 파일명은 제네릭을 제외하고 만든다.
  - 같은 패키지 안에 클래스 명은 같고 제네릭 명은 다르게 선언할 수 없다. 제네릭은 보조적인 정보이지 클래스명을 결정하는 구성 요소는 아니다.
  - 제네릭 표현식 안의 문자는 자바의 변수 선언 규칙과 동일하다. (숫자 시작 X, 특수 문자 시작 X, '_' 시작 가능, 유니코드 문자 지원)
  - 통상적으로 대문자 영문 한 글자를 쓰는게 관례
  - 객체의 생성과 선언 시 타입 파라미터는 동일해야 한다.

- 관습정 정의 (P 494)
  

## **2.3 멀티 타입 파라미터**
- 제네릭 타입을 두 개 이상 선언하는 것을 멀티 타입 파라미터라고 한다.
  ```java
  public class GenericCustomMap<K, V> {
    private Map<K, V> map = new HashMap<K, V>();

    public void put(K key, V value) {
      map.put(key, value);
    }

    public void get(K key) {
      return map.get(key);
    }
  }
  ```
- 클래스 설계시 타입을 너무 많이 지정하면 복잡해진다.
  

## **2.4 파라미터화된 타입**
- 제네릭에정의하는 타입 자체가 자바의 객체 유형을 수용하기 때문에 또 다른 제네릭 클래스를 받아들일 수 있다.
```java
Map<String, List<String>> map;
```

## **2.5 제네릭을 사용하면서 하기 쉬운 실수**
- 상속 관계에 있는 클래스를 제네릭의 타입 파라미터로 정의했을때
  - Vehicle : 움직이는 이동 수단의 상위 추상 클래스
  - Sedan :  Vehicle을 상속받아 구현한 승용차 클래스
  - Truck  : Vehicle을 상속받아 구현한 트럭 클래스
  
  ```java
  MyList<Sedan> sedanList = new MyList<Sedan>();
  MyList<Truck> truckList = new MyList<Truck>();

  MyList<Vehicle> truckList = sedanList; //컴파일 에러
  MyList<Object> truckList = truckList; //컴파일 에러
  ```
- 제네릭 타입 파라미터는 컬렉션 클래스 등에서 처리할 객체를 명확히 하기 위한 것으로, 상속까지 고려한 것은 아니다.
- MyList<Sedan>, MyList<Truck>, MyList<Vehicle>, MyList<Object> 는 타입이 상속 관계에 있지만 전혀 다른 선언이라고 생각해야 한다.


- 객체의 인스턴스 타입을 확인하는 방법 중 제일 쉽고 빠른 것은 instanceof를 이용해서 비교하는 것이다.

  ```java
  import java.util.ArrayList;
  import java.util.List;

  public class GenericExample1 {

    public static void main(String[] args) {
      List myList = new ArrayList();
      List<String> stringList = new ArrayList();
      List<Integer> integerList = new ArrayList();

      System.out.println(myList instanceof ArrayList);
      System.out.println(stringList instanceof ArrayList<String>); // 컴파일 에러
      System.out.println(integerList instanceof ArrayList<Integer>); // 컴파일 에러
  }
  ```
- instanceof는 제네릭에서 정의한 타입까지 비교해서 확인하지는 않는다.
- 해당 컴파일 에러를 해결하기 위해서는 두가지 방법이 있다.
  1. 제네릭이 아닌 클래스 자체를 비교하는 것이다. 
    ```java
    System.out.println(stringList instanceof ArrayList);
    System.out.println(integerList instanceof ArrayList);
    ```
  2. 특정한 클래스를 선언하지 않고 와일드 카드를 선언해서 사용하는 것이다.
    ```java
    System.out.println(stringList instanceof ArrayList<?>);
    System.out.println(integerList instanceof ArrayList<?>);
    ```
    - 제네릭의 와일드카드는 제네릭을 좀 더 유연하게 사용할 수 있게 하는 훌륭한 기능이다.



## **2.6 제네릭 메서드 정의 방법**
- 제네릭메서드를 설계 할 때 고려해야 할 점은 다음과 같다.
  - 제네릭 메서드에서 선언한 타입의 범위는 메서드 내부로 제한된다.
  - 일반 메서드뿐만 아니라 정적 메서드에서도 제네릭을 사용할 수 있다.
  - 정적 메서드를 정의할 때 리턴 값을 기술하기 전에 반드시 제네릭 타입 파라미터를 먼저 선언해야 한다.
  
  ```java
  public void put(K key, V value) {
    map.put(key, value);
  }

  public void get(K key) {
    return map.get(key);
  }
  ```
- 클래스를 생성할 때 K, V 타입을 결정했기 때문에 해당 제네릭 메서드도 호출하기 이전에 이미 타입이 결정 됐다.
- 제네릭을 사용해서 정적 메서드를 선언하면 아래와 같다.


  ```java
  public static <K, V> Map<K, V> sorting(Map<K, V> map) {
    return map;
  }
  ```
  
- 메서드로 선언시 리턴 타입과 입력 파라미터만 제네릭을 지정해 주는 것과 달리, 정적 메서드는 객체화 하지 않고 호출하는 메서드이기 때문에 제네릭 메서드 정의할 때 메서드 이름 앞에 제네릭 타입 파라미터를 정의해야 한다.
- 그리고 사용하는 방법은 아래와 같다.
  ```java
  Map<String, String> prop = new HashMap<String, String>();
  Map<String, String> sortedProp = Util.<String, String>sorting(prop);
  ```
- 메서드 이름 앞에 제네릭 타입을 선언하는 것을 타입 참조(Type Reference)라고 한다.


# **3. JVM 에서 제네릭 처리**
- 제네릭은 컴파일시에만 유효하다. 바이트 코드 변환시 Object형을 변환된다.
  ```java
  // 기본 코드
  public class GenericErasureExample1 {

    public static void main(String[] args) {
      List<String> myList = new ArrayList<>();
      myList.add("Hello");
      myList.add("World");

      String hello = myList.get(0);
      String world = myList.get(1);

      System.out.println(hello + " " + world);
    }
  }

  // 디컴파일 코드(인텔리 j 버전)
  public class GenericErasureExample1 {
    public GenericErasureExample1() {
    }

    public static void main(String[] args) {
      List<String> myList = new ArrayList();
      myList.add("Hello");
      myList.add("World");
      String hello = (String)myList.get(0);
      String world = (String)myList.get(1);
      System.out.println(hello + " " + world);
    }
  }

  // 디컴파일 코드
  public class GenericErasureExample1 {
    public GenericErasureExample1() {
    }

    public static void main(String[] args) {
      List myList = new ArrayList();
      myList.add("Hello");
      myList.add("World");
      String hello = (String)myList.get(0);
      String world = (String)myList.get(1);
      System.out.println(hello + " " + world);
    }
  }

  // 바이트 코드
  // access flags 0x9
  public static main([Ljava/lang/String;)V
    // parameter  args
   L0
    LINENUMBER 9 L0
    NEW java/util/ArrayList // 타입 정보 없음
    DUP
    INVOKESPECIAL java/util/ArrayList.<init> ()V
    ASTORE 1
   L1
   ...
  ```

- 디컴파일 코드를 보면 제네릭 타입 선언등의 내용이 삭제되고 변환된 것을 볼 수 있다. 이를 제네릭 삭제 라고 한다.
- 제네릭의 적용으로 기존 코드에 문제가 생기지 않도록 하기 위해서 컴파일 후에느 넺네릭 관련 코드가 삭제 되도록 했다.
- 타입 파라미터는 컴파일러에 의해 해석되는 부분이고 자바 가상머신에서는 해석이 되지 않기 때문이다.
- 제네릭은 런타임에 체크하는 것이 아니라 컴파일 시에 정합성을 체크하게 된다.
- 제네릭 관련 원칙
  - 컴파일 시에 해석되고 바이트 코드로 변환될 때에는 제거된다.
  - 클래스 선언 시 사용된 제네릭 타입은 제거되며 메서드에서 리턴 받을 때는 컴파일러에 의해 형 변환된 코드가 자동 추가 된다.

# **4. 와일드카드와 타입 제한**
- 제네릭에서는 '?' 를 와일드 카드로 사용할 수 있다.
  ```java
  void printCollection(Collection<Object> c){
    for(Object e : c){
      System.out.println(e);
    }
  }
  ```
- 실제 위 코드에서 파라미터를 Collection<Object> 로 설정을 해두었기 때문에 반드시 Collection<Object> 생성된 객체만 받을 수 있다.
- 제네릭의 타입 파라미터의 목적은 사용할 클래스의 타입을 명확하 하기 위함이기 때문에 Collection<Object> 외에 다른 타입은 다른 것으로 판단한다.
- 이러한 경우 와일드카드를 사용하면 타입 파라미터에 어떤 클래스가지정되어도 다 허용하겠다는 뜻이다.
- 타입 파라미터미터는 어떤 타입을 지정하더라도 다 수용할 수 있지만 실제 클래스 생성 시에는 특정 클래스를 지정해야 한다.
- 반면 와일드카드로 지정되 메서드는 실행 시에 타입 파라미터에 지정된 클래스와는 무관하게 사용된다.
- 반면 클래스를 설계할 때 제네릭 타입으로 특정 클래스 혹은 인터페이스와 상속 관계에 있도록 제한하고 싶을 때가 있다. 이러한 것을 바운드 타입 파라미터라고 한다.
  
## **4.1. extends 를 이용한 제한**

- 아래 처럼 엔티티 클래스의 상속 관계를 설계 했다고 하자.

![자동차 상속 관계](https://lh3.googleusercontent.com/pw/ACtC-3fJM-RcDmG-IeQJFVTumorCPIRtYUmzaf9gMVXH2h_zpCSX34A1ylNW6slZt571tp0KruoWRBKFXVhpnYZsqnLCS9O4C2BEFnF-CwUq-LnJtDNSaWVqREyWUNDwMQmvTp1TRC6omcYAgsd2jCy26w8YXQ=w968-h540-no?authuser=0)

- 자동차 클래스와 상속 관계에 있는 클래스만으로 제네릭 타입을 제안하고 싶을경우 타입 파라미터에 상속 관계를 정의할 수 있다.
  ```java
  public class GenericBoundExample <T extends Vehicle> {
    private T vehicleType;

    public void setVehicleType(T vehicleType) {
      this.vehicleType = vehicleType;
    }

    public T getVehicleType() {
      return vehicleType;
    }

    public static void main(String[] args) {
      GenericBoundExample<Sedan> sedan = new GenericBoundExample<>();
      GenericBoundExample<Truck> truck = new GenericBoundExample<>();
      GenericBoundExample<String> error = new GenericBoundExample<>(); // 컴파일 에러
    }
  }
  ```

- extends 키워드를 통해 반드시 특정 클래스를 상속 혹은 구현한 클래스만을 지정할 수 있다.
- 제네릭 타입으로는 클래스뿐만 아니라 인터페이스도 사용할 수 있다.
- 제네릭 메서드 에서도 extends 키워드를 통해 타입을 제한할 수 있다.
- 타입을 제한할 때는 타입을 하나 이상 지정할 수도 있다. <T extends A & B & C>
- 또한 클래스를 가장 앞에 두어야 한다. <T extends Class1 & interface1 & interface2>

## **4.2. super 를 이용한 제한**
- 반대로 super 키워드를 이용한 타입 제한은 지정한 클래스나 인터페이스의 상위 클래스 혹인 인터페이스를 허용하겠다는 뜻
- extends 와 달리 여러 타입을 AND 조건으로 지정할 수 없다.


## **4.3 extends ? super ?**
- 언제 extends를 써야하고 super를 써야할까?
- 제네릭 메서드에서 입력 파라미터의 경ㅇ extends 와일드 카드를 이용하는 것이 좋다.
- 제네릭 메서드에서 출력 파라미터의 경우 super 와일드 카드를 이용하는것이 좋다.
- 입력은 데이터를 생성하는 주체 - extends
- 출력은 데이터를 소비하는 주체 - super

# **5. 제네릭 제약 조건**

**제네릭의 타입은 자바의 기본형을 사용할 수 없다.**
- 필요한 경우 기본형에 대응하는 래퍼 클래스를 사용해야 함(int -> Integer)
  
**제네릭 코딩은 컴파일 시에만 사용된다.**
- 바이트 코드로 변환하면 관련 코딩이 재거된다.

**제네릭 타입으로 정의한 배열은 생성할 수 없다.**
- 변수 선언은 가능, 생성은 불가능
  
**제네릭의타입 변수는 정적 필드 혹은 메서드에서 사용할 수 없다.**
  ```java
  public class Generic3<T> {
    private static T t;

    public static T method(){
      return t;
    }
  }
  ```
- static 키워드특징상 클래스가 로딩되면서 생성되고, 싱글턴 형태로 메모리에 남아 사용되는데, 이때 제네릭 타입으로 특정 지을수 없다.

**제네릭 타입 변수로 객체를 생성할 수 없다.**
  ```java
  public class Generic3<T> {
    public void method2(){
      T t = new T();
    }
  }
  ```

**제네릭 클래스를 catch 하거나 throw할 수 없다.**
  ```java
  public class Generic3<T> {
    public void method3(){
      try {

      }catch (T e){
        throw new T();
      }
    }
  }
  ```


# **6. 다이아몬드 연산자**
  ```java
  // java 7 이전
  Map<String, List<String>> myMap = new HashMap<String, List<String>>();

  // 이후
  Map<String, List<String>> myMap = new HashMap<>();
  ```
- 객체 선언부에 <> 를 ㄱ술하면 변수 선언 시에 사용된 제네릭 타입이 그대로 적용 된다.