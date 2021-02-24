# **7. 고급 매핑**

- 이번 장에서 다룰 내용
  - **상속 관계 매핑 :** 객체의 상속 관계를 데이터베이스에 어떻게 매핑하는지 다룬다.
  - **@MappedSuperclass :** 등록일, 수정일 같이 여러 엔티티에서 공통으로 사용하는 매핑 정보만 상속받고 싶으면 이 기능을 사용하면 된다.
  - **복합 키와 식별 관계 매핑 :** 데이터베이스의 식별자가 하나 이상일 때 매핑하는 방법을 다룬다. 그리고 데이터베이스 설계에서 이야기하는 식별 관계와 비식별 관계에 대해서도 다룬다.
  - **조인 테이블 :** 테이블은 외래 키 하나로 연관관계를 맺을 수 있지만 연관관계를 관리하는 연결 테이블을 두는 방법도 있다. 여기서는 이 연결 테이블을 매핑하는 방법을 다룬다.
  - **엔티티 하나에 여러 테이블 매핑하기 :** 보통 엔티티 하나에 테이블 하나를 매핑하지만 엔티티 하나에 여러 테이블을 매핑하는 방법도 있다. 여기서는 이 매핑 방법을 다룬다.

## **7.1 상속 관계 매핑**

- 관계형 데이터베이스에는 객체지향 언어에서 다루는 상속 대신에 **슈퍼타입, 서브타입 관계**라는 모델링 기법을 사용해 객체의 상속 구조와 데이터베이스의 슈퍼타입 서브타입 관계를 매핑한다.

  ![슈퍼타입 서브타입 논리 모델](https://lh3.googleusercontent.com/pw/ACtC-3f6aqBOIpXXlqAg5ZgfMWyeMyX9sZgWRhGIacqjg1OIhKGcJ7HEU2Xh_zL0zyZAMPEQfocuaK81V-ODN2gu6wmEbmRL-v1ZgZz3lZ07peJPiu5auQaa9xmHGllWoiRul0NS5oiR9xtd4EYNMGjB_3egBw=w715-h361-no?authuser=0)

  ![객체 상속 모델](https://lh3.googleusercontent.com/pw/ACtC-3f_qBKWvIeiqmeKTsVLunWvgk5Eiis2lBvdFX-edHXqXw81SGidgX1VVqwalsznBgi2PsnEaGV99bIrj-ysvlvRB6S5ZAUa5AqKmFE53hgU95oOVzRk7olijiP2Bz6fi8Ym_kvfnKnpaP8Q422T75AcYQ=w693-h352-no?authuser=0)

- 슈퍼타입 서브타입 논리 모델을 실제 물리 모델인 테이블로 구현할 때는 3가지 방법을 선택할 수 있다.

  - 각각의 테이블로 변환 : 모두 각각의 테이블로 만들고 조회할때 조인을 사용한다. JPA 에서는 조인 전략이라 한다.
  - 통합 테이블로 변환 : 테이블 하나만 사용해서 통합한다. JPA 에서는 단일 테이블 전략이라 한다.
  - 서브타입 테이블로 변환 : 서브 타입마다 하나의 테이블을 만든다. JPA 에서는 구현 클래스마다 테이블 전력이라 한다.

### **7.1.1 조인 전략**

- 엔티티 각각을 모두 테이블로 만듬
- 자식 테이블이 부모 테이블의 기본 키를 받아서 기본 키 + 외래 키로 사용하는 전략
- 조인이 자주 일어남
- 테이블은 타입의 개념이 없기 때문에 타입을 구분하는 컬럼을 추가해야함

  ![JOINED TABLE](https://lh3.googleusercontent.com/pw/ACtC-3fdb4fwBusuAMuzcf3cF3eATE4tDECyQPtWW9CJJu5SJIBNNLmYs5OEUvh-BtWW7sFaOjsargbcTm_GbusHhQ9TRKkvgD4qmzkXncqC3UyUJ59HKxpoxanmIF2UjhRyUesDYl_qE3zX8Cb436jyxIMImg=w657-h380-no?authuser=0)

  ```java
  @Entity
  @Inheritance(strategy = InheritanceType.JOINED)
  @DiscriminatorColumn(name = "DTYPE")
  public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;

    private String name; // 이름
    private int price; // 가격
  }

  ...

  @Entity
  @DiscriminatorValue("A")
  public class Album extends Item{

    private String artist;
  }

  ...

  @Entity
  @DiscriminatorValue("M")
  public class Movie extends Item{

    private String director; // 감독
    private String actor; // 배우
  }

  ...

  @Entity
  @DiscriminatorValue("B")
  public class Book extends Item{

    private String author; // 작가
    private String isbn; // ISBN
  }
  ```

- **@Inheritance(strategy = InheritanceType.JOINED) :** 상속 매핑은 부모 클래스에 @Inheritance를 사용해야 한다. 그리고 매핑 전략을 지정해야 하는데 여기서는 조인 전략을 사용하므로 InheritanceType.JOINED를 사용했다.

- **@DiscriminatorColumn(name = "DTYPE") :** 부모 클래스에 구분 컬럼을 지정한다. 이 컬럼으로 저장된 자식 테이블을 구분할 수 있다. 기본값이 DTYPE이므로 @DiscriminatorColumn으로 줄여 사용해도 된다.

- **@DiscriminatorValue("M") :** 엔티티를 저장할 때 구분 컬럼에 입력할 값을 지정한다. 만일 영화 엔티티를 저장하면 구분 컬럼이 DTYPE에 값 M이 저장된다.