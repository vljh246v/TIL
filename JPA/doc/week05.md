# **5. 연관관계 매핑 기초**

-   객체는 참조(주소)를 사용해서 관계를 맺고 테이블은 외래 키를 사용해서 관계를 맺는다.
-   객체의 **참조**와 테이블의 **외래 키**를 매핑해보자
-   방향
    -   예를 들어 회원과 팀이 있을때 둘 중 한쪽만 참조하는 것을 단방향 관계라고 한다.
    -   반대로 양쪽 모두 서로 참조하는 것을 양방향관계라 한다.
    -   방향은 객체관계에서만 존재하고 테이블은 항상 양방향이다.
-   다중성
    -   [다대일(N:1)], [일대다(1:N)], [일대일(1:1)], [다대다(N:M)] 다중성이 있다.
    -   회원과 팀 관계에서 여러 회원은 한 팀에 속하므로 회원과 팀은 다대일 관계다.
    -   반대로 한 팀에 여러 회원이 소속될 수 있으므로 팀과 회원은 일대다 관계다.
-   연관관계의 주인
    -   객체를 양방향 연관관계로 만들면 연고나관계의 주인을 정해야 한다.

## **5.1. 단방향 연관관계**

-   제약조건

    -   회원과 팀이 있다.
    -   회원은 하나의 팀에만 소속될 수 있다.
    -   회원과 팀은 다대일 관계다
        ![다대일 연관관계 | 다대일(N:1), 단방향](https://lh3.googleusercontent.com/pw/ACtC-3e3JO7r_dqiWQTHPKiRbrZ64-mCxSnxiwkQqZxV__Sbg9IBWNd5xbwTyOFa5yyKyyhTk3lAVgEx0JTdJS4l9mO4zulQJBQDr9PRWi8K0q_nuTNIIbsE0pmPnwYCFfmw0znj36stxxdlVi4vdUUKXB9aww=w858-h523-no?authuser=0)

-   객체 연관관계
    -   회원 객체는 Member.team 필드로 팀 객체와 연관 관계를 맺는다.
    -   회원 객체와 팀 객체는 단방향 관계다. 회원은 팀을 알 수 있지만 팀은 회원 알 수 없다.
-   테이블 연관관계

    -   회원 테이블은 TEAM_ID 외래 키로 팀 테이블과 연관관계를 맺는다.
    -   회원 테이블과 팀 테이블은 양방향 관계다
    -   TEAM_ID 외래키를 통해 회원과 팀을 조인할 수 있고, 반대로 팀과 회원도 조인할 수 있다.

        ```sql
        SELECT *
          FROM MEMBER M
         JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID

        SELECT *
          FROM TEAM T
          JOIN MEMBER M ON T.TEAM_ID = M.TEAM_ID
        ```

-   객체 연관관계와 테이블 연관관계의 가장 큰 차이

    -   참조를 통한 연관관계는 언제나 단방향이다.
    -   객체간에 연관관계를 양방향으로 만들고 싶으면 반대쪽에도 필드를 추가해서 참조를 보관해야 한다.
    -   정확히 이야기 하면 이것은 양방향 관계까 아니라 단방향 관계 2개다.

        ```java
        class A {
            B b;
        }

        class B {
            A a;
        }
        ```

-   객체 연관관계 vs 테이블 연관관계 정리
    -   객체는 참조(주소)로 연관과계를 맺는다.
    -   테이블은 외래 키로 연관관계를 맺는다.
    -   연관된 데이터를 조회할때 객체는 참조를 사용하지만 테이블은 조인(JOIN)을 사용한다.

### **5.1.1 순수한 객체 연관관계**

-   아래는 JPA를 사용하지 않은 순수한 회원과 팀 클래스의 코드다.

    ```java
    public class Member {

        private String id;
        private String username;

        private Team team; // 팀의 참조를 보관

        // getter, setter
    }


    public class Team {
        private String id;
        private String name;

        // getter, setter
    }
    ```

-   회원을 팀에 포함시키는 예제는 아래와 같다.

    ```java
    public static void main(String[] args) {
        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");

        Team team1 = new Team("team1", "팀1");

        member1.setTeam(team1);
        member2.setTeam(team1);

        Team findTeam = member1.getTeam();
    }
    ```

-   객체는 객체 참조를 사용해서 연관관계를 탐색할 수 있는데 잉것을 객체 그래프 탐색이라 한다.

### **5.1.2 테이블 연관관계**

-   데이터베이스 테이블의 회원과 팀의 관계를 표시하기 위한 DDL 이다.

    ```sql
    CREATE TABLE MEMBER (
        MEMBER_ID VARCHAR(255) NOT NULL,
        TEAM_ID VARCHAR(255),
        USERNAME VARCHAR(255),
        PRIMARY KEY (MEMBER_ID)
    )

    CREATE TABLE TEAM (
        TEAM_ID VARCHAR(255) NOT NULL,
        NAME VARCHAR(255),
        PRIMARY KEY (TEAM_ID)
    )

    -- 회원 테이블의 TEAM_ID 에 외래키 제약조건 설정
    ALTER TABLE MEMBER ADD CONSTRAINT FK_MEMBER_TEAM
        FOREIGN KEY (TEAM_ID)
        REFERENCES TEAM
    ```

-   회원을 팀에 소속시키고 검색하는 QUERY는 아래와 같다.

    ```sql
    -- 삽입
    INSERT INTO TEAM(TEAM_ID, NAME) VALUES('team1', '팀1');
    INSERT INTO MEMBER(MEMBER_ID, TEAM_ID, USERNAME) VALUES('member1', 'team1', '회원1');
    INSERT INTO MEMBER(MEMBER_ID, TEAM_ID, USERNAME) VALUES('member2', 'team2', '회원2');

    -- 조회
    SELECT T.*
      FROM MEMBER M
      JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID
     WHERE M.MEMBER_ID = 'member1';
    ```

### **5.1.3 객체 관계 매핑**

-   JPA를 통해 둘을 매핑해보자
    ![다대일 연관관계1 | 다대일(N:1), 단방향](https://lh3.googleusercontent.com/pw/ACtC-3dJphyxwWQv0YXWLIVgDnlyLJGT4cr9V56HC5sHwviYSynMYlpNP3p2NDpuuCfJCIc74KDbSo7wBnIcMqiq3oJ-1Na0D6u0Z-zawnrvqwyxke4Ossqo2YOS7Gc35PGZgSHbKQw6IO-Jcyyup55yLode-A=w912-h508-no?authuser=0)

-   엔티티 예제 코드

    ```java
    @Entity
    public class Member {

        @Id
        @Column(name = "MEMBER_ID")
        private String id;
        private String username;

        // 연관관계 매핑
        @ManyToOne
        @JoinColumn(name = "TEAM_ID")
        private Team team;

        public void setTeam(Team team) {
            this.team = team;
        }

        // getter, setter
    }

    @Entity
    public class Team {

        @Id
        @Column(name = "TEAM_ID")
        private String id;

        private String name;

        // getter, setter
    }
    ```

-   **객체 연관관계** : 회원 객체의 Member.team 필드 사용
-   **테이블 연관관계** : 회원 테이블의 MEMBER.TEAM_ID 외래 키 컬럼을 사용
-   Member.team 과 MEMBER.TEAM_ID 를 매핑하는 것이 연관관계 매핑이다.
-   @ManyToOne : 이름 그대로 다대일(N:1) 관계라는 매핑 정보다. 연관 관계를 매핑할 때 이렇게 다중성을 나타내는 어노테이션을 필수로 사용해야 한다.
-   @JoinColumn(name="TEAM_ID) : 조인 컬럼은 외래 키를 매핑할 때 사용한다. name 속성에는 매핑할 외래 키 이름을 지정한다.

### **5.1.4 @JoinColumn**

![@JoinColumn의 주요 속성](https://lh3.googleusercontent.com/pw/ACtC-3fYxwVEYsqfV709IJR_yM5WeUDDXWNWn8mqsSdtXs--_TJd1LlNSgPstSmlYC7SyulBStgZcQWZrVswVDTda7kZEV7p0MWuE9BYfudHrt7VhWNLOX0O5H2nRdkyeW-9-8hlIX2z5tz35qh1j_2VHw85AA=w717-h285-no?authuser=0)

> 참고
> @JoinColumn 생략
> @JoinColumn을 생략하면 기본 전략을 사용한다.
>
> ```java
> @ManyToOne
> private Team team;
> ```
>
> -   기본전략 : 필드명 + \_ + 참조하는 테이블의 컬럼명
> -   예 : 필드명(team) + \_(밑줄) + 참조하는 테이블의 컬럼명(TEAM_ID) = team_TEAM_ID 외래 키를 사용

### **5.1.5 @ManyToOne**

![@ManyToOne 속성](https://lh3.googleusercontent.com/pw/ACtC-3dcEdqs6W4kUDtTzELmbTICH3IoR6bcI9kjhbrX1WHZouXw2AxWQPfgOPJ1kuQECMvsScFg7d7AFfkdARTGoLmpkL-h9vGqq_Dh4nngacAnGbLydE1RWZQ4Yjpao6wwAk3C1H97sPQn4AjZesCBGHizZA=w704-h159-no?authuser=0)

## **5.2. 연관관계 사용**

### **5.2.1 저장**

-   아래는 연관관계를 매핑한 엔티티를 저장하는 예제이다.

    ```java
    // 팀 저장
    Team team = new Team("team1", "팀1");
    em.persist(team);

    // 회원 1 저장
    Member member1 = new Member("member1", "회원1");
    member1.setTeam(team); // 연관관계 설정 member1 -> team1
    em.persist(member1);

    // 회원 2 저장
    Member member2 = new Member("member2", "회원2");
    member2.setTeam(team); // 연관관계 설정 member2 -> team1
    em.persist(member2);
    ```

-   저장된 값을 보면 회원 테이블의 외래 키 값으로 참조한 팀의 식별자 값인 team1이 입력된 것을 확인할 수 있다.

### **5.2.2 조회**

-   연관관계가 있는 엔티티를 조회하는 방법은 크게 2가지다.

    -   객체 그래프 탐색 (객체 연관관계를 사용한 조회)
    -   객체지향 쿼리 사용(JPQL)

-   객체 그래프 탐색

    -   member.getTeam()을 사용해서 member와 연관된 team 엔티티를 조회할 수 있다.

-   객체지향 쿼리 사용

    -   회원을 대상으로 조회할때 특정 팀에 속한 회원을 조회하려면 sql은 조인을 해서 검색 조건을 사용하면 된다.
    -   JPQL도 조인을 지원하기 때문에 동일하게 조인을 통한 검색조건을 사용하면 된다.

        ```java
        String jpql = "select m from Member m join m.team t where " +
                "t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
            .setParameter("teamName", "팀1")
            .getResultList();

        for(Member member : resultList){
          System.out.println("[query] member.username = " + member.getUsername());
        }
        ```

    -   팀과 관계를 가지고 있는 필드(m.team)를 통해서 Member와 Team 을 조인했다.
    -   :teamName과 같이 :로 시작하는 것은 파라미터 바인딩 문법이다.
    -   이때 실행되는 SQL은 아래와 같다.
        ```sql
        select
            member0_.MEMBER_ID as MEMBER_I1_0_,
            member0_.TEAM_ID as TEAM_ID3_0_,
            member0_.username as username2_0_
        from
            Member member0_
        inner join
            Team team1_
                on member0_.TEAM_ID=team1_.TEAM_ID
        where
            team1_.name=?
        ```

### **5.2.3 수정**

-   팀 1 소속이던 회원을 새로운 팀 2 에 소속하도록 수정해보자

    ```java
    private static void updateRelation(EntityManager em) {
      Team team2 = new Team("team2", "팀2");
      em.persist(team2);

      Member member = em.find(Member.class, "member1");
      member.setTeam(team2);
    }
    ```

-   실행 되는 sql은 아래와 같다.

    ```sql
    update
          Member
      set
          TEAM_ID=?,
          username=?
      where
          MEMBER_ID=?
    ```

-   수정은 em.update() 같은 메소드가 없다.
-   단순히 엔티티 값만 변경해두면 틀내잭션을 커밋할 때 플러시가 일어나면서 변경 감지 기능이 작동한다.
-   연관관계를 수정할 때도 참조하는 대상만 변경하면 나머지는 JPA가 자동으로 처리한다.

### **5.2.4 연관관계 제거**

-   연관된 엔틴티를 삭제하려면 기존에 있던 연관관계를 먼저 제거하고 삭제해야 한다.
-   그렇지 않으면 외래 키 제약조건으로 인해, 데이텁이스에서 오류가 발생한다.

    ```java
    member1.setTeam(null);
    member2.setTeam(null);

    em.remove(team);
    ```

## **5.3 양방향 연관관계**
- 이번에는 반대방향에서 접근할 수 있도록 팀에서 회원으로 접근하는 관계를 추가하자.
- 일대다 관계는 여러 건과 연관관계를 맺을 수 있으므로 컬렉션을 사용해야 한다.
  ![양방향 객체 연관관계](https://lh3.googleusercontent.com/pw/ACtC-3fvRtOVa9AuGpG6GftwTnTQk2RSxk0iq0TF-bEaAtVUJxlOC4lipQrctTLr-p7lxFy8w3JHxZOOvCFVC7YnB13q6v42iKXWuDHGMr4rLLp0r2-tw9R1SjpsiMOCH1vRzEJEqTV61DZXXkj6nRgtJZPtOQ=w670-h199-no?authuser=0)

    - 회원 -> 팀 (Mebmer.team)
    - 팀 -> 회원 (Team.members)

### **5.3.1 양방향 연관관계 매핑**
- Team 엔티티에 양방향 관계를 설정하자
    ```java
    @Entity
    public class Team {

        @Id
        @Column(name = "TEAM_ID")
        private String id;

        private String name;

        // 추가
        @OneToMany(mappedBy = "team")
        private List<Member> members = new ArrayList<>();
    }
    ```
- 팀과 회원은 일대다 관계다. 따라서 팀 엔티티에 컬렉션인 List<Member> members를 추가했다.
- 그리고 일대다 관계를 매핑하기 위해 @OneToMany 매핑정보를 사용했다.
- mappedBy 속성은 반대쪽 매핑의 필드 이름값으로 주면된다.

### **5.3.2 일대다 컬렉션 조회**
- 객체 그래프 탐색을 통해 조회한 회원들을 출력하는 예제이다
    ```java
    Team2 team = em.find(Team2.class, "team1");
    List<Member2> members = team.getMembers();

    for(Member2 member : members){
        System.out.println("member.username = " + member.getUsername());
    }
    ```