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

- 자식 테이블의 기본 키 컬럼명을 변경하고 싶으면 @PrimaryKeyJoinColumn 를 사용하면 된다.

  ```java
  @Entity
  @DiscriminatorValue("B")
  @PrimaryKeyJoinColumn(name = "BOOK_ID")
  public class Book extends Item{

    private String author; // 작가
    private String isbn; // ISBN
  }
  ```

**장점**

- 테이블이 정규화된다.
- 외래 키 참조 무결성 제약 조건을 활용할 수 있다.
- 저장공간을 효율적으로 사용한다.

**단점**

- 조회할 때 조인이 많이 사용되므로 성능이 저하될 수 있다.
- 조회 쿼리가 복잡하다.
- 데이터를 등록할 INSERT SQL을 두번 실행한다.

**특징**

- jpa 표준 명세는 구분 컬럼을 사용하도록 하지만 하이버네이트를 포함한 몇 구현체는 구분 컬럼(@DiscriminatorColumn) 없이도 동작한다.

**관련 어노테이션**

- @PrimaryKeyJoinColumn, @DiscriminatorColumn, @DiscriminatorValue

### **7.1.2 단일 테이블 전략**

- 이름 그대로 테이블을 하나만 사용한다.
- 구분 컬럼으로 어떤 자식 데이터가 저장되었는지 구분
- 조인이 없어서 가장 빠르다
- 주의점은 매핑한 컬럼은 모두 null을 허용해야 한다.
- 다른 엔티티와 매핑된 컬럼은 사용하지 않으므로 null이 입력

  ```java
  @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
  @DiscriminatorColumn(name = "DTYPE")
  public abstract class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;

    private String name; // 이름
    private int price; // 가격
  }

  ...

  @Entity
  @DiscriminatorValue("B")
  public class Book extends Item{

    private String author; // 작가
    private String isbn; // ISBN
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
  @DiscriminatorValue("A")
  public class Album extends Item{

    private String artist;
  }
  ```

- @Inheritance(strategy = InheritanceType.SINGLE_TABLE) 로 지정하면 단일 테이블 전략을 사용한다.

**장점**

- 조인이 필요 없으므로 일반적으로 조회 성능이 빠르다.
- 조회 쿼리가 단순하다.

**단점**

- 자식 엔티티가 매핑한 컬럼ㅇ느 모두 null을 허용해야 한다.
- 단일 테이블 저장이라 테이블이 커질 수 있다. 그렇기 때문에 상황에 따라 오히려 느려질 수 있다

**특징**

- 구분 컬럼을 꼭 사용해야 한다. 따라서 @DiscriminatorColumn 을 꼭 설정해야 한다.
- @DiscriminatorValue을 설정하지 않으면 기본적으로 엔티티 이름을 사용한다.

### **7.1.3 구현 클래스마다 테이블 전략**

- 자식 엔티티 마다 테이블을 만든다. 그리고 각각에 필요한 컬럼이 모두 있다.

  ```java
  @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
  @DiscriminatorColumn(name = "DTYPE")
  public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name; // 이름
    private int price; // 가격
  }
  ```

- 추천하지 않음

## **7.2 @MappedSuperclass**

- 부모 클래스는 테이블과 매핑하지 않고 부모 클래스를 상속받은 자식 클래스에게 매핑 정보만 제공하고 싶으면 @MappedSuperclass를 사용하면 된다.

- @Entity는 실제 테이블과 매핑 되지만 @MappedSuperclass는 실제 테이블과 매핑되지 않는다.

  ![@MappedSuperclass 설명 객체](https://lh3.googleusercontent.com/pw/ACtC-3ddhE9t0smsKBqoQZn0iaVywjvljxQGHj0BEYR5_odbYK5M8rGn_pgfTJOYUD8GXvCSx0ePjFHq63aYlJ0KKTRdXhYByy6Vsgy0ap6iRS5Y2FsUA2UVXpKgc3P-gTsmty4WX30i-o3Qtd64xNpjIxb7Sg=w945-h461-no?authuser=0)

  ```java
  @MappedSuperclass
  public abstract class BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;
  }

  ...

  @Entity
  public class Member extends BaseEntity{

    private String email;
  }

  ...

  @Entity
  public class Seller extends BaseEntity{

    private String shopName;
  }
  ```

- BaseEntity 는 공통 매핑 정보를 정의하고, 자식 엔티티들은 상속을 통해 매핑 정보를 물려 받았다.
- 부모로부터 물려받은 매핑 정보를 재정의하려면 @AttributeOverrides 나 @AttributeOverride 를 사용하고, 재정의 하려면 @AssociationOverrides나 @AssociationOverride를 사용한다.

  ```java
  @Entity
  @AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID"))
  public class Member extends BaseEntity{

    private String email;
  }
  ```

- 둘 이상을 재정의하려면 @AttributeOverrides 사용하면 된다.

  ```java
  @Entity
  @AttributeOverrides({
      @AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID")),
      @AttributeOverride(name = "name", column = @Column(name = "MEMBER_NAME"))
  })
  public class Member extends BaseEntity{

    private String email;
  }
  ```

- @MappedSuperclass 특징

  - 테이블과 매핑되지 않고 자식 클래스에 엔티티 매핑 정보를 상속하기 위해 사용한다.
  - @MappedSuperclass로 지정한 클래스는 엔티티가 아니므로 em.find()나 JPQL 에서 사용할 수없다.
  - 이 클래스를 직접 생성해서 사용할 일은 거의 없으므로 추상 클래스로 만드는 것을 권장한다.

- @MappedSuperclass를 사용하면 등록일자, 수정일자, 등록자, 수정자 같은 여러 엔티티에서 공통으로 사용하는 속성을 효과적으로 관리할 수 있다

## **7.3 복합 키와 식별 관계 매핑**

### **7.3.1 식별 관계 vs 비식별 관계**

- 데이터베이스 테이블 사이에 관계는 외래 키가 기본 키에 포함되는지 여부에 따라 식별 관계와 비식별 관계로 구분한다.

**식별 관계**

- 식별 관계는 부모 테이블의 기본 키를 내려받아서 자식 테이블의 기본 키 + 외래키로 사용하는 관계다.
  ![식별 관계](https://lh3.googleusercontent.com/pw/ACtC-3fu8QiFnLOR3iVlJXCKF6UF1JVPYbEk5X_X6_Xuc6lFk72qeq2nF-Byzc_tZAJ6Hv6L9ZXTb6Dd6l9tM12pVBaICMvHsyhTL_TF10bG1IrHnUK78xupiYWsNLJgy2ivfwNIuL4tYfGhjVkQdtwMQOVA9w=w613-h173-no?authuser=0)

**비식별 관계**

- 비식별 관계는 부모 테이블의 기본 키를 받아서 자식 테이블의 외래 키로만 사용하는 관계다

  ![비식별 관계](https://lh3.googleusercontent.com/pw/ACtC-3fDPRk4hbbRey_4GWTVO1L3hVRx0g4U_gNwtxXoJdzsNRAO7g8_gPWY5RovZ-LGTAROJLJ1Ejx5Cp8yl4dcdcFx0wffcMeTJNdyBsnzeixuhRH7xdbEiV_u88z-WXtiAVcZLXTvdKXYVQZnEsAIKNEcHQ=w619-h401-no?authuser=0)

  - 필수적 비식별 관계 : 외래 키에 NULL을 허용하지 않는다. 연관관계를 필수적으로 맺어야 한다.
  - 선택적 비식별 관계 : 외래 키에 NULL을 허용한다. 연관관계를 맺을지 말지 선택할 수 있다.

### **7.3.2 복합키 : 비식별 관계 매핑**

- JPA에서 식별자를 둘 이상 사용하려면 별도의 식별자 클래스를 만들어야 한다.

  ```java
  @Entity
  public class Hello {
    @Id
    private String id1;

    @Id
    private String id2; // 에러!!!!!!
  }
  ```

- JPA는 영속성 컨텍스트에 엔티티를 보관할 때 엔티티의 식별자를 키로 사용한다.
- 그리고 식별자를 구분하기 위해 equals 와 hashCode를 사요해서 동등성 비교를 한다.
- 식별자 필드가 2개 이상이면 별도의 식별자 클래스를 만들고 그곳에 equals와 hashCode를 구현해야 한다.
- JPA는 복합 키를 지원하기 위해 @IdClass 와 @EmbeddedId 2가지 방법을 제공하는데 @IdClass는 관계형 데이터베이스에 가까운 방법이고 @EmbeddedId는 좀더 객체지향에 가까운 방법이다.

**@IdClass**

- 복합 키 테이블은 비식별 관계고 PARENT는 복합 기본 키를 사용한다.

  ![복합 키 테이블](https://lh3.googleusercontent.com/pw/ACtC-3eEh8npMlx_7PxxSufaENW1Hgt0V1yGKPRza08hwfBtSqht_zYil9ieJSeh2_A8h_KkQ44Lc7GNF03PH6QdyTlgzXgE_BPovcH2I7qcpnnrv0aLqKWkH4ofOhP8A7SyAPs437jRqTmGtDaL8zROEM7aXQ=w535-h190-no?authuser=0)

- 기본 키를 PARENT_ID1, PARENT_ID2로 묶은 복합 키로 구성했다.
- 복합 키를 매핑하기 위해 식별자 클래스를 별도로 만들어야 한다.

  ```java
  @Entity
  @IdClass(ParentId.class)
  public class Parent {

    @Id
    @Column(name = "PARENT_ID1")
    private String id1;

    @Id
    @Column(name = "PARENT_ID2")
    private String id2;

    private String name;
  }
  ```

- 각각 기본 키 컬럼을 @Id로 매핑했다. 그리고 @IdClass를 사용해서 ParentId 클래스를 식별자 클래스로 지정했다

  ```java
  @NoArgsConstructor // lombok 사용
  @AllArgsConstructor
  @EqualsAndHashCode
  public class ParentId implements Serializable {

    private String id1;
    private String id2;
  }
  ```

- @IdClass를 사용할 때 식별자 클래스는 다음 조건을 만족 해야 한다

  - 식별자 클래스의 속성명과 엔티티에서 사용하는 식별자의 속성명이 같아야 한다.
  - Serializable 인터페이스를 구현해야 한다.
  - equals, hashCode 를 구현해야 한다.
  - 기본 생성자가 있어야 한다.
  - 식별자 클래스는 public이어야 한다.

  ```java
  // 저장
  Parent parent = new Parent();
  parent.setId1("myId1");
  parent.setId2("myId2");
  parent.setName("parentName");
  em.persist(parent);
  ```

- em.persist(parent)을 통해 영속성 컨텍스트에 등록하기 직전, 내부에서 ParentId 클래스를 생서해 영속성 컨텍스트의 키로 사용한다.

  ```java
  ParentId parentId = new ParentId("myId1", "myId2");
  Parent resultParent = em.find(Parent.class, parentId);
  ```

- 부모 테이블의 기본 키 컬럼이 복합 키이므로 자식 테이블의 외래 키도 복합 키다.

  ```java
  @Entity
  public class Child {

    @Id
    private String id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PARENT_ID1", referencedColumnName = "PARENT_ID1"),
        @JoinColumn(name = "PARENT_ID2", referencedColumnName = "PARENT_ID2")

    })
    private Parent parent;
  }
  ```

**@EmbeddedId**

- @IdClass가 데이터베이스에 맞춘 방법이라면 @EmbeddedId는 좀 더 객체지향적인 방법이다.

  ```java
  @Entity
  public class Parent {

    @EmbeddedId
    private ParentId id;

    private String name;
  }
  ```

- Parent 엔티티에서 식별자 클래스를 직접 사용하고 @EmbeddedId 어노테이션을 붙여주면 된다.

  ```java
  @NoArgsConstructor // lombok 사용
  @AllArgsConstructor
  @EqualsAndHashCode
  @Embeddable
  public class ParentId implements Serializable {

    @Column(name = "PARENT_ID1")
    private String id1;

    @Column(name = "PARENT_ID2")
    private String id2;
  }
  ```

- @EmbeddedId를 적용한 식별자 클래스는 다음 조건을 만족해야 한다.

  - @Embeddable 어노테이션을 붙여주어야 한다.
  - Serializable 인터페이스를 구현해야 한다.
  - equals, hashCode 를 구현해야 한다.
  - 기본 생성자가 있어야 한다.
  - 식별자 클래스는 public이어야 한다.

  ```java
  // 저장
  Parent parent = new Parent();
  ParentId parentId = new ParentId("myId1", "myId2");
  parent.setId(parentId);
  parent.setName("parentName");
  em.persist(parent);

  ...

  // 조회
  ParentId parentId = new ParentId("myId1", "myId2");
  Parent resultParent = em.find(Parent.class, parentId);
  ```

- 조회 코드도 식별자 클래스 parentId를 직접 사용한다.

**복합 키와 equals(), hashCode()**

- 영속성 컨텍스트는 엔티티의 식별자를 키로 사용해서 엔티티를 관리한다.
- 식별자를 비교할 때 equals()와 hashCode()를 사용한다.
- 따라서 복합키는 equals()와 hashCode()를 필수로 사용해야 한다.

**@IdClass vs @EmbeddedId**

- @EmbeddedId가 @IdClass비교해서 더 객체지향적이다.
- 특정 상황에서 JPQL이 더 길어질 수 있다.

### **7.3.3 복합 키: 식별 관계 매핑**

- 부모, 자식, 손자 까지 계속 기본 키를 전달하는 식별 관계
  ![](https://lh3.googleusercontent.com/pw/ACtC-3f_QE7RHCknsvKPAojrdKtM4h3Gmm6JZcCiFT3VmT3v1h3KUe-zYMKnWPsNFLIs79Y0ySP9UPiTZCrzgZS2GFF9q93nEnBvC0ui1Pxeiut-ctf2uWt_WwW-9Bf2HGq-1zQt5Rr3ABpbtA-UQ1EByMbTMw=w737-h156-no?authuser=0)

- @IdClass나 @EmbeddedId를 사용해서 식별자를 매핑해야 한다.

**@IdClass와 식별 관계**

- @IdClass로 식별관계를 정의 하는 예제

  ```java
  // 부모
  @Entity
  public class Parent {

    @Id @Column(name = "PARENT_ID")
    private String id;

    private String name;
  }

  ...

  // 자식
  @Entity
  @IdClass(ChildId.class)
  public class Child {

    @Id
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    @Id @Column(name = "CHILD_ID")
    private String childId;

    private String name;
  }

  ...

  // 자식 ID
  @NoArgsConstructor // lombok 사용
  @AllArgsConstructor
  @EqualsAndHashCode
  @Setter
  @Getter
  public class ChildId implements Serializable {

    private String parent;
    private String childId;

  }

  ...

  // 손자
  @Entity
  @IdClass(GrandChildId.class)
  public class GrandChild {

    @Id
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PARENT_ID"),
        @JoinColumn(name = "CHILD_ID")
    })
    private Child child;

    @Id @Column(name = "GRANDCHILD_ID")
    private String id;

    private String name;
  }

  ...


  // 손자 ID
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  @Setter
  @Getter
  public class GrandChildId implements Serializable {

    private ChildId child;
    private String id;
  }
  ```

**@EmbeddedId와 식별 관계**

- @EmbeddedId로 식별관계를 구성할 때는 @MapsId를 사용해야 한다.

  ```java
  // 부모
  @Entity
  public class Parent {

    @Id @Column(name = "PARENT_ID")
    private String id;

    private String name;
  }

  ...

  // 자식
  @Entity
  public class Child {

    @EmbeddedId
    private ChildId id;

    @MapsId("parentId")
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    private String name;
  }

  ...

  // 자식 ID
  @NoArgsConstructor // lombok 사용
  @AllArgsConstructor
  @EqualsAndHashCode
  @Setter
  @Getter
  @Embeddable
  public class ChildId implements Serializable {

    private String parentId;

    @Column(name = "CHILD_ID")
    private String childId;
  }

  ...

  // 손자
  @Entity
  public class GrandChild {

    @EmbeddedId
    private GrandChildId id;

    @MapsId("childId")
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PARENT_ID"),
        @JoinColumn(name = "CHILD_ID")
    })
    private Child child;

    private String name;
  }

  ...

  // 손자 ID
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  @Setter
  @Getter
  @Embeddable
  public class GrandChildId implements Serializable {

    private ChildId childId;

    @Column(name = "GRANDCHILD_ID")
    private String id;
  }
  ```

- @EmbeddedId는 식별 관계로 사용할 연관관계의 속성에 @MapsId를 사용하면 된다.
