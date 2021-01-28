# **4. 엔티티 매핑**
- JPA를 사용하는 데 가장 주우요한 일은 엔티티와 테이블을 정확히 매핑하는 것이다.
- JPA는 다양한 매핑 어노테이션을 지원하는데 크게 4가지로 분류할 수 있다.
  1. 객체와 테이블 매핑
  2. 기본 키 매핑
  3. 필드와 컬럼 매핑
  4. 연관관계 매핑
   
## **4.1 @Entity**
- JPA를 사용해 테이블과 매핑할 클래스는 @Entity 어노테이션을 필수로 부여야 한다.
- @Entity가 붙은 클래스는 JPA가 관리하는 것으로,  엔티티라 부른다.
- 주의사항
  - 기본 생서자 필수
  - final 클래스, enum, interface, inner 클래스에는 사용할 수 없다.
  - 저장할 필드에 final 을 사용하면 안 된다.
  
## **4.2 @Table**
- @Table은 엔티티와 매핑할 테이블을 지정한다.
- 생략하면 매핑한 엔티티 이름을 테이블 이름으로 사용한다.
- 속성
  - name : 매핑할 테이블 이름
  - uniqueConstraints : DDL 생성시에 유니크 제약조건을 만든다. 2개 이상의 복합 유니크 제약조건도 만들 수 있다.  

## **4.3 다양한 매핑 사용**
- 회원 관리 프로그램에 아래와 같은 요구 사항이 있다고 가정하자
  1. 회원은 일반 / 관리자로 구분
  2. 가입일 / 수정일 존재
  3. 회원 설명 필드는 길이 제한이 없음
- 아래는 해당 요구사항을 만족하는 엔티티
  ```java
  @Entity
  @Table(name="MEMBER")
  public class Member {
      ...

      // 추가
      @Enumerated(EnumType.STRING)
      private RoleType roleType;
      
      @Temporal(TemporalType.TIMESTAMP)
      private Date createDate;
      
      @Temporal(TemporalType.TIMESTAMP)
      private Date lastModifiedDate;
      
      @Lob
      private String description;
      
      // ...
      // getter, setter
  }
  
  
  public enum RoleType {
      ADMIN, USER
  }
  ```

- roleType : enum 을 사용해 회원 타입을 구분, enum을 사용하기 위해서는 @Enumerated 어노테이션으로 매핑해야함
- createDate, lastModifiedDate : 자바의 날짜 타입은 @Temporal 을 사용해서 매핑
- description : 길이 제한이 없기 때문에 varchar 보다는 @Lob을 사용해서 CLOB, BLOB와 매핑한다.

## **4.4 데이터베이스 스키마 자동 생성**
- JPA는 데이터베이스 스키마를 자동을 생성하는 기능을 지원한다.
- 클래스의 매핑정보를 통해 어떤 테이블에 어떤 컬럼을 상요하는지 알 수 있다.
- 아래 옵션을 persistence.xml에 추가하면 **애플리케이션 실행 시점에 데이터베이스 테이블을 자동으로 생성한다.**

  ```xml
  <property name="hibernate.hbm2ddl.auto" value="create"/>
  ```

- 콘솔에 sql을 출력하기 위해서 아래 속성을 true로 설정하면 된다.

  ```xml
  <property name="hibernate.show_sql" value="true"/>
  ```

- 기존 테이블은 삭제하고 다시 생성한 것을 알 수 있다.
- 하지만 운영환경에서는 절대 사용하지 말고 데이터베이스와 엔티티 간 맵핑을 확인하는 단순 '학습' 용으로만 사용하자. 
- hibernate.hdm2ddl.auto 속성
  - create : 기존 테이블을 삭제하고 새로 생성한다. DROP + CREATE
  - create-drop : create 속성에 추가로 애플리케이션이 종료할 때 생성한 DDL을 제거한다. DROP + CREATE + DROP
  - update : 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 변경 사항만 수정한다.
  - validate : 비교를 해 차이가 있으면 경고를 남기고 애플리케이션을 실행하지 않는다. DDL 실행은 없다.
  - none : 자동 생성 기능을 사용하지 않는다.
- 운영 환경에서는 hibernate.hdm2ddl.auto 속성중 DDL이 실행되는 환경은 만들면 안된다.

## **4.5 DDL 생성 기능**
- 위 예제에서 추가로 회원 이름 필수와 10자 초과 금지라는 제약조건이 추가되었다.
- 스키마 자동 생성하기를 통해 만들어지는 DDL에 제약조건을 추가 할 수 있다.

    ```java
    @Column(name = "NAME", nullable = false, length = 10)
    private String username;
    ```

    ```sql
    create table MEMBER (
        ID varchar(255) not null,
        age integer,
        createdDate timestamp,
        description clob,
        lastModifiedDate timestamp,
        roleType varchar(255),
        NAME varchar(10) not null,
        primary key (ID)
    )
    ```
- sql 에도 해당 내용이 반영된 것을 확인 할 수 있다.
- 유니크 제약 조건을 만들어주는 @Table 의 uniqueConstrainnts 예는 아래와 같다.

    ```java
    @Table(name="MEMBER", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"}
    )})
    public class Member {
        ...
    }
    ```

    ```sql
    alter table MEMBER 
       add constraint NAME_AGE_UNIQUE unique (NAME, age)
    ```

- 이런 속성을 설정하는 기능들은 DDL을 자동으로 생성할 때만 사용되고 JPA 의 실행 로직에는 영향을 주지 않는다.


## **4.6 기본 키 매핑**
- 기본키를 에플리케이션에서 직접 할당하는 대신에 데이터베이스가 생성해주는 값을 사용하려면 JPA가 제공해주는 기본 키 생성 전략을 사용하면 된다.
  - **직접할당** : 기본 키를 애플리케이션에서 직접 할당
  - **자동 생성** : 대리 키 사용 방식
    - IDENNTITY : 기본 키 생성을 데이터베이스에 위임한다.
    - SEQUENCE : 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다.
    - TABLE : 키 생성 테이블을 사용한다.
- 자동 생성 전략이 다양한 이유는 데이터베이스 벤더마다 지원하는 방식이 다르기 때문이다.
- 따라서 SEQUENCE 나 IDENNTITY 전략은 사용하는 데이터베이스에 의존적이다.
- 기본 키를 직접 할당하려면 @Id 어노테이션만 사용하면 되고, 자동 생성 전략을 사용하려면 @GeneratedValue 어노테이션을 추가하고 원하는 키 생성 전략을 선택하면 된다.
  
### **4.6.1 기본 키 직접 할당 전략**
- 기본키를 직접 할당하려면 @Id로 직접 매핑하면 된다.
- @Id 적용 가능한 자바 타입
  - 자바 기본형
  - 자바 래퍼형
  - String
  - java.util.Date
  - java.sql.Date
  - java.math.BigDecimal
  - java.math.BigInteger

### **4.6.2 IDENTITY 전략**
- IDENTITY는 기본키 생성을 데이터베이스에 위임하는 전략이다.
- 예를 들어 MySQL의 AUTO_INCREMENT 기능은 데이터베이스가 기본 키를 자동으로 생성하게 해준다.
- IDENTITY 전략은 지금 설명한 AUTO_INCREMENT를 사용한 예제처럼 데이터베이스에 값을 저장하고 나서야 기본키 값을 구할 수 있을때 사용한다.
- 식별자가 생성되는 경우에는 @GeneratedValue 어노테이션을 사용하고 시별자 생성 전략을 선택해야 한다.


    ```java
    // IDENTITY 매핑 코드
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    ...

    // IDENTITY 사용 코드
    Member member = new Member();
    em.persist(member);
    System.out.println("member id : " + member.getId());

    ```
- em.persist()를 호출해서 엔티티를 저장한 직후에 할당된 식별자 값을 출력했다.
- 출력된 값은 저장 시점에 데이터베이스가 생성한 값을 JPA가 조회한 것이다.
- 따라서 이 전략은 트랜잭션을 지원하는 쓰기 지연이 동작하지 않는다.

### **4.6.3 SEQUENCE 전략**
- 시퀀스는 유일한 값을 순서대로 생성하는 특별한 오브젝트이고, SEQUENCE 전략은 이 시퀀스를 사용해서 기본 키를 작성한다.
- 시퀀스를 지원하는 데이터베이스에서 사용할 수 있다.
- 아래는 시퀀스를 생성하고 매핑하는 예제다.
  
  ```sql
  CREATE TABLE BOARD (
    ID BIGINT NOT NULL PRIMARY KEY,
    DATA VARCHAR (255)
  )

  -- 시퀀스 생성
  CREATE SEQUENCE BOARD_SEQ START WITH 1 INCREMENT BY 1;
  ```

  ```java
  @Entity
  @SequenceGenerator(
      name = "BOARD_SEQ_GENERATOR",
      sequenceName = "BOARD_SEQ",
      initialValue = 1, allocationSize = 1)
  public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    private Long id;
  }

  ```

- @SequenceGenerator 를 사용해서 BOARD_SEQ_GENERATOR 라는 시퀀스 생서기를 등록했다.
- sequenceName 으로 설정한 시퀀스와 시퀀스 생성기를 매핑한다.
- 그리고 @GeneratedValue 전략을 GenerationType.SEQUENCE 로 설정을 하고 시퀀스 생성기를 선택해준다.
  
  ```java
  private static void logic(EntityManager em){

    Board board = new Board();
    em.persist(board);
    System.out.println("board.id : " + board.getId());
  }

  // board.id : 1
  ```
- 시퀀스 사용 코드는 IDENTITY와 내부 동작 방식이 다르다.
- SEQUENCE 전략은 em.persist() 를 호출할 때 먼저 데이터베이스 시퀀스를 사용해서 식별자를 조회한다.
- 그리고 조회한 식별자를 엔티티에 할당한 후에 엔티티를 여옥성 컨텍스트에 저장한다.
- 이후 트랜잭션을 커밋해서 플러시가 일어난다.

**@SequenceGenerator**
- 속성
  - name : 식별자 생성기 이름
  - sequenceName : 데이터베이스에 등록되어 있는 시퀀스 이름
  - initialValue : DDL 생성시에만 사용됨, 시퀀스 DDL 을 생성할 때 처음 시작하는 수를 지정한다.
  - allocationSize : 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨)
  - catalog,  schema : 데이터베이스 catalog, schema 이름
  
### **4.6.4 TABLE 전략**
- TABLE 전략은 키 생성 전용 테이블을 하나 만들고 여기에 이름과 값으로 사용할 컬럼을 만들어 데이터베이스 시퀀스를 흉내내는 전략이다.
- TABLE 전략을 사용하려면 키 생성 용도로 사용할 테이블을 만들어야 한다. 
  
  ```sql
  create table MY_SEQUENCES (
    sequence_name varchar(255) not null,
    next_val bigint,
    primary key (sequence_name)
  )
  -- default 컬럼 이름을 사용( 컬럼 이름 변경 가능)
  ```
  ```java
  @Entity
  @TableGenerator(
      name = "BOARD_SEQ_GENERATOR",
      table = "MY_SEQUENCES",
      pkColumnValue = "BOARD_SEQ", allocationSize = 1
  )
  public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_SEQ_GENERATOR")
    private Long id;
  }
  ```

- 실제 테이블에 삽입된 데이터를 보면 sequence_name 컬럼에는 우리가 pkColumnValue로 지정한 'BOARD_SEQ'가 들어있다.

### **4.6.5 AUTO 전략**
- GenerationType.Auto는 데이터베이스에 따라 자동으로 선택한다.
- 장점은 데이터베이스가 변경되어도 코드 수정이 없다는 것이다.
- 아직 키 생성 전략이 확정되지 않은 초기 단계나 프로토타입에 편리

### **4.6.6 기본키 매핑 정리**
- 영속성 컨텍스트는 엔티티를 식별자 값으로 구분하므로 엔티티를 영속 상태로 만들려면 식별자 값이 반드시 있어야 한다.
- 직접할당 :  애플리케이션에서 직접 식별자 값을 할당
- SEQUENCE : 데이터베이스 시퀀스에서 식별자 값을 획득한 후 영속성 컨텍스트에 저장한다.
- TABLE 데이터베이스 시퀀스 생성용 테이블에서 식별자 값을 회득한 후 영속성 컨텍스트에 저장한다.
- IDENTITY : 데이터베이스에 엔티티를 저장해서 식별자 값을 획득한 후 영속성 컨텍스트에 저장한다.

> 권장하는 식별자 선택 전략
> 1. 자연 키 보다는 대리 키를 권장한다.
> 2. 비즈니스 환경은 언젠가 변한다.
> 3. JPA는 모든 엔티티에 일관된 방식으로 대리키 사용을 권장한다.

## **4.7 필드와 컬럼 매핑: 레퍼런스**
