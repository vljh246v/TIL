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
