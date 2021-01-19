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
