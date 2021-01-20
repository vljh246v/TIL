# **2. JPA 시작**

> 2.1 이클립스 설치와 프로젝트 불러오기  
> 예제 경로 https://github.com/holyeye/jpabook  
> 2.2 H2 데이터베이스 설치  
> 다운로드 경로 : https://www.h2database.com/  
> 2.3 라이브러리와 프로젝트 구조  
> 위 3개 챕터는 설정이라 넘어감

## **2.4 객체 매핑 시작**

-   회원 테이블

    ```sql
    CREATE TABLE MEMBER(
        ID VARCHAR(255) PRIMARY KEY,
        NAME VARCHAR(255),
        AGE INTEGER NOT NULL
    );
    ```

-   회원 클래스

    ```java
    public class Member {
        private String id;
        private String username;
        private Integer age;

        // getter, setter
    }
    ```

-   회원 클래스와 회원 테이블 매핑 정보

    -   클래스와 테이블 : Member - MEMBER
    -   기본 키 : id - ID
    -   필드와 컬럼 : username - NAME
    -   필드와 컬럼 : age - AGE

-   JPA가 제공하는 매핑 어노테이션 추가

    ```java
    import javax.persistence.*;

    @Entity
    @Table(name="MEMBER")
    public class Member {
        @Id
        @Column(name = "ID")
        private String id;

        @Column(name = "NAME")
        private String username;

        private Integer age; //매핑정보가 없는 필드
    }
    ```

    ![클래스와 테이블 매핑](https://lh3.googleusercontent.com/pw/ACtC-3dR9wnUXYz7fApGFwk79lfrcIlAwFvraFhEmDrNzTNZi3hvyrp2J0xzNnhUO3Aqn2QTXXB7Ftgl4ebAW1eL2FayGP2UBKxWsjRt80jji1CwHaLuRbmDw_6GMAUQwKy2cCY0oFkF6cAdyrKQ6YWZthPQMg=w1053-h243-no?authuser=0)

-   @Entity, @Table, @Column 이 매핑 정보다.
-   JPA는 어노테이션을 분석해서 어떤 객체가 어떤 테이블과 관계가 있는지 알아낸다.
-   @Entity
    -   이 클래스를 테이블과 매핑한다고 JPA에게 알려준다. 이렇게 @Entity 가 사용된 클래스를 엔티티 클래스라고 한다.
-   @Table
    -   엔티티 클래스에 매핑할 테이블 정보를 알려준다.
    -   name 속성을 사용해 Member 엔티티를 MEMBER 테이블에 매핑했다.
    -   이 어노테이션을 생략하면 클래스 이름으르 테이블 이름(더 정확히는 엔티티 이름)으로 매핑한다.
-   @Id
    -   엔티티 클래스의 필드를 테이블의 기본 키에 매핑한다.
    -   이렇게 @Id 가 사용된 필드를 식별자 필드라고 한다.
-   @Column
    -   필드를 컬럼에 매핑한다.
-   매핑 정보가 없는 필드
    -   매핑 어노테이션을 생략하면 필드명을 사용해서 컬럼명으로 매핑한다.
    -   대소문자를 구분하는 데이터베이스를 사용한다면 @Column(name="AGE") 처럼 명시적으로 매핑해야 한다.

## **2.5 persistence.xml 설정**

-   JPA는 psersistence.xml 을 사용해 필요한 설정 관리
-   META-INF/persistence.xml 클래스 패스 경로에 있으면 JPA가 인식
-   persistence.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.2"> <!-- JPA 2.2 버전을 사용하기 위해서 표시 -->
    <persistence-unit name="jpabook">
        <properties>
        <!-- 필수 속성 -->
        <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
        <property name="javax.persistence.jdbc.user" value="sa"/>
        <property name="javax.persistence.jdbc.password" value=""/>
        <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
        <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect" />

        <!-- 옵션 -->
        <property name="hibernate.show_sql" value="true" />
        <property name="hibernate.format_sql" value="true" />
        <property name="hibernate.use_sql_comments" value="true" />
        <property name="hibernate.id.new_generator_mappings" value="true" />
        </properties>
    </persistence-unit>
    </persistence>
    ```

-   XML 네임스페이스와 JPA 사용 버전 표시
    ```xml
    <persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.2">
    ```
-   JPA는 영속성 유닛을 일반적으로 데이터베이스 하나당 하나의 영속성 유닛을 등록한다.
-   그리고 영속성 유닛에 고유한 이름을 부여해야 하는데 예제에서는 jpabook 이라는 이름을 사용함

    ```xml
    <persistence-unit name="jpabook">
    ```

-   JPA 표준 속성
    -   javax.persistence.jdbc.driver : JDBC 드라이버
    -   javax.persistence.jdbc.user : 데이터베이스 접속 아이디
    -   javax.persistence.jdbc.password : 데이터베이스 접속 아이디
    -   javax.persistence.jdbc.url : 데이터베이스 접속 URL
-   하이버네이트 속성

    -   hibernate.dialect : 데이터베이스 Dialect(방언) 설정

-   javax.persistence 으로 시작하는 속성은 특정 구현체에 종속되지 않음
-   hibernate 로 시작하는 속성은 하이버네이트 전용속성

### **2.5.1 데이터베이스 Dialect(방언)**

-   JPA는 데이터베이스에 종속적이지 않은 기술
-   데이터베이스가 제공하는 SQL 문법과 함수가 조금씩 다르다는 문제점이 있다.
    -   데이터 타입 : MYSQL - VARCHAR / 오라클 - VARCHAR2
    -   다른 함수명 : SQL 표준 - SUBSTRING() / 오라클 - SUBSTR()
    -   페이징 처리 : MYSQL - LIMIT / 오라클 - ROWNUM
-   이처럼 SQL표준이 아니거나 특정 데이터베이스만의 고유 기능을 JPA에서는 Dialect(방언) 이라고 부른다.
-   하이버네이트를 포함한 대부분의 JPA 구현체들은 이런 문제를 해결하기위해 다양한 데이터베이스 방언 클래스를 제공한다.
-   하이버네이트가 제공하는 대표적인 데이터베이스 방언
    -   H2 : org.hibernate.dialect.H2Dialect
    -   오라클 10g : org.hibernate.dialect.Oracle10gDialect
    -   MySQL : org.hibernate.dialect.MySQL5InnoDBDialect

## **2.6 애플리케이션 개발**

-   시작코드

    ```java
    public class JpaMain {

        public static void main(String[] args) {

            // 엔티티 매니저 팩토리 생성
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

            // 엔티티 매니저 생성
            EntityManager em = emf.createEntityManager();

            // 트랜잭션 획득
            EntityTransaction tx = em.getTransaction();

            try{
                tx.begin(); //트랜잭션 시작
                logic(em);
                tx.commit(); // 트랜잭션 커밋
            } catch (Exception e){
                tx.rollback(); // 롤백
            }finally {
                em.close(); // 엔티티 매니저 종료
            }
            emf.close(); // 엔티티 매니저 팩토리 종료
        }

        private static void logic(EntityManager em){
        }
    }
    ```

-   코드는 크게 3부분으로 나뉨
    -   엔티티 매니저 설정
    -   트랜잭션 관리
    -   비즈니스 로직

### **2.6.1 엔티티 매니저 설정**

![엔티티 매니저 생성 과정](https://lh3.googleusercontent.com/aRRhDGYOuXbAPIUnmfyvq2xphl1Jciz4eWSANJOKhnon3SM-DikgpGe1lQKhF3LyWL78IhSDw3PHmq5Hz7_HgZLQPTZ1c0uJHbaKW8HIoep21gOCw5lLbsG19levz_KSXkCIrELPO-_suAJZNfr1jN5T0h5wPxBLTUQB3P7dzjUuBDSeeOH30hmpdArxPY96ht5he347cz7zGBmpsgq3OV57mZhvKL1_SdFsUrM4mqlQ6s5NpAwnF8nwKEsuj1TTjo8MqD1YZ5bW0Mb0rP17p93qPiBTRj70_W7gOIk0gM3QxzIaNznPkqNiZ1NfToZcA_raYZj_qldZi4ALHvUto5mk-aFc6pboiC_h63ao6THY2jszdXkCecp2Ua8rUIYrCWi0kLuzc5wslLSXhj4-Jrub6wP-C2VVzWS_PTW65JclB3CUK8-ZhcLLqYz6OMET3OgIqFMmpjDuWq_O6Do5_hRcNhr2V5GjK2o3RJ-Dsfr0kG3v3O22iwxEV7mCL89pvieFLJbjxt21wSx4AVkP8yCndugK9Zl7zrnOLZvz8bpq2S8pWKatfAyAJYQ8G5_fja4QKu3O9TAtDxSomoAnVFP8oLjHLZC74uRbxPHVKBDOZJoqdBwTQUasnruNc3Dtt9tXcqyWLjXjCxCpu2jZoNTw-KGYj2ZpVdVtARUFIhhXfS1nRZR_DAM4x7bTJT8=w783-h405-no?authuser=0)

-   엔티티 매니저 팩토리 생성
    -   Persistence 클래스를 사용해 매니저 팩토리를 생성해서 JPA를 사용할 수 있게 준비한다.
    -   설정한 이름의 영속성 유닛을 찾아 엔티티 매니저 팩토리를 생성한다.
    -   설정 정보를 읽어서 매니저 기반객체를 만들고, 구현체에 따라서 커넥션 풀도 생성하므로 엔티티 매니저 팩토리를 생성하는 비용은 아주 크다.
    -   그렇기 때문에 엔티티 매니저 팩토리는 애플리케이션 전체에서 딱 한 번만 생성하고 공유해서 사용해야 한다.
-   엔티티 매니저 생성
    ```java
    EntityManager em = emf.createEntityManager();
    ```
    -   엔티티 매니저 팩토리에서 엔티티 매니저를 생성한다.
    -   에티티 매니저를 사용해서 엔티티를 데이터베이스에 등록/수정/삭제/조회할 수 있다.
    -   개발자는 엔티티 매니저를 가상의 데이터베이스로 생각할 수 있다.
    -   데이터베이스 커텍션과 밀접한 관계가 있으므로 스레드간에 공유하거나 재사용하면 안된다.
-   종료
    -   사용이 끝난 엔티티 매니저는 종료 해주어야 한다.
    -   애플리케이션을 종료할 때 엔티티 매니저 팩토리도 종료해 주어야 한다.

### **2.6.2 트랜잭션 관리**

-   트랜잭션을 사용하려면 엔티티 매니저에서 트랜잭션 API를 받아와야 한다.

    ```java
    // 트랜잭션 획득
    EntityTransaction tx = em.getTransaction();

    try{
        tx.begin(); //트랜잭션 시작
        logic(em);
        tx.commit(); // 트랜잭션 커밋
    } catch (Exception e){
        tx.rollback(); // 롤백
    }
    ```

-   비즈니스 로직의 정상 / 예외 에따라 커밋 / 롤백을 한다.

### **2.6.3 비즈니스 로직**

-   비즈니스 로직 코드

    ```java
    private static void logic(EntityManager em){

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("demo");
        member.setAge(30);

        // 등록
        em.persist(member);

        // 수정
        member.setAge(31);

        // 한건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername()
            + ", age=" + findMember.getAge());

        // 목록 조회
        List<Member> members =
            em.createQuery("select m from Member m", Member.class)
            .getResultList();
        System.out.println("member.size=" + members.size());

        // 삭제
        em.remove(members);
    }
    ```

-   비즈니스 로직을 보면 등록/수정/삭제/조회 작업이 엔티티 매니저를 통해서 수행되는 것을 알 수 있다.

-   등록

    ```java
    String id = "id1";
    Member member = new Member();
    member.setId(id);
    member.setUsername("demo");
    member.setAge(30);

    // 등록
    em.persist(member);
    ```

    -   엔티티를 저장하려면 엔티티 매니저의 persist() 메소드에 저장할 엔티티를 넘겨주면 된다.
    -   실행 쿼리
        ```sql
        insert into MEMBER (age, NAME, ID) values (?, ?, ?)
        ```

-   수정

    ```java
    member.setAge(31);
    ```

    -   update 와 비슷한 메소드를 호출하지 않고 단순히 엔티티 값만 변경
    -   JPA는 어떤 엔티티가 변경되었는지 추적하는 기능ㅇ르 갖추고 있음
    -   실행 쿼리
        ```sql
        update
            MEMBER
        set
            age=?,
            NAME=?
        where
            ID=?
        ```

-   삭제

    ```java
    em.remove(members);
    ```

    -   엔티티를 삭제하려면 엔티티 매니저의 remove() 메소드에 삭제하려는 엔티티를 넘겨준다.
    -   실행 쿼리
        ```sql
        delete from MEMBER where ID=?
        ```

-   한건 조회
    ```java
     Member findMember = em.find(Member.class, id);
    ```
    -   find() 메소드는 조회할 엔티티 타입과 @Id로 데이터베이스 테이블의 기본키와 매핑한 식별자 값으로 엔티티 하나를 조회하는 가장 단순한 메소드다.
    -   실행 쿼리
        ```sql
        select * from member where id=?
        ```

### **2.6.4 JPQL**

-   하나 이상의 회원 목록 조회하는 코드
    ```java
    List<Member> members =
            em.createQuery("select m from Member m", Member.class)
            .getResultList();
    ```
-   JPA는 엔티티 객체를 중심으로 개발하므로 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색해야 한다.
-   데이터베이스의 모든 데이터를 애플리케이션으로 불러와 엔티티 객체로 변경한 다음 검색하는 것은 불가능 하다.
-   필요한 데이터만 불러오기위해 결국 검색 조건이 포함된 SQL을 사용해야 한다.
-   JPA는 JPQL(Java Persistence Query Language) 이라는 객체지향 쿼리 언어를 제공한다.
-   차이점
    -   JPQL은 에닡티 객체를 대상으로 한다.
    -   SQL은 데이터베이스 테이블을 대상으로 한다.
-   from Member 부분은 엔티티 객체를 말하는 것이지 MEMBER 테이블이 아니다.
-   JPQL은 데이터베이스 테이블을 전혀 알지 못한다.
