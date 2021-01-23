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
