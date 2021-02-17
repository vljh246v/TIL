# **6. 다양한 연관관계 매핑**

-   엔티티의 연관관계를 매핑할 때는 다음 3가지를 고려해야 한다.

    -   다중성
    -   단방향, 양방향
    -   연관관계의 주인

-   **다중성**

    -   다대일 (@ManyToOne)
    -   일대다 (@OneToMany)
    -   일대일 (@OneToOne)
    -   다대다 (@ManyToMany)

    -   다중성을 판단하기 어려울 때는 반대방향을 생각해 보면 된다.

-   **단방향, 양방향**

    -   객체는 참조용 필드를 가지고 있는 객체만 연관된 객체를 조회할 수 있다.
    -   객체 관계에서 한 쪽만 참조하는 것을 단방향 관계라고 하고, 양쪽이 서로 참조하는 것을 양방향 관계라고 한다.

-   **연관관계의 주인**
    -   테이블과는 다르게 엔티티를 양방향으로 매핑하면 서로를 A -> B, B -> A 서로를 참조한다.
    -   JPA는 두 객체 연관관계 중 하나를 정해서 외래키를 관리해야 하는데 이것을 주인이라고 한다.
    -   보통 외래키를 가진 테이블과 매핑한 엔티티가 외래키를 관리하는게 효율적이므로 보통 이곳을 주인으로 선택한다.
    -   주인이 아닌 방향은 외래 키를 변경할 수 없고 읽기만 가능하다.
    -   연관관계 주인이 아니면 mappedBy 속성을 사용한다.

## **6.1 다대일**

-   다대일 관계의 반대 방향은 항상 일대다 관계이다.
-   데이터베이스 테이블의 일(1), 다(N) 관계에서 외래 키는 항상 다쪽에 있다. 따라서 객체 양방향 관계에서 연관관계의 주인은 항상 다쪽이다.

### **6.1.1 다대일 단방향 [N:1]**

![다대일 단방향](https://lh3.googleusercontent.com/pw/ACtC-3cohfNUTUu0dzfUuWuVfE7G-n2I2pOBZHMg3WpgHmwbrS7AZoO7lHxaquuJVyVALmaJeZwj-0qTbULGyfpL3mHF39pwhNNitGo6Y-w3MHwwK3KLL0k4Kzvey5wD1HrLRvbW9zLNyhj0-_FDxfy_WoUstQ=w912-h508-no?authuser=0)

-   예제

    ```java
    @Entity
    public class Member {
        @Id @GeneratedValue
        @Column(name = "MEMBER_ID")
        private Long id;

        private String username;

        @MnayToOne
        @JoinColumn(name = "TEAM_ID")
        private Team team ;
        ...
    }

    @Entity
    public class Team {
        @Id @GeneratedValue
        @Column(name = "TEAM_ID")
        private Long id;

        private String name;
        ...
    }

    ```

-   회원은 Member.team 으로 엔티티를 참조할 수 있지만 반대로 팀에는 회원을 참조하는 필드가 없다. 따라서 다대일 단방향 관계이다.
-   @JoinColumn(name = "TEAM_ID")을 사용해 Member.team 필드를 TEAM_ID 외래키와 매핑했다. 따라서 Mmeber.team 필드로 TEMA_ID 외래키를 관리한다.

### **6.1.2 다대일 양방향 [N:1, 1:N]**

-   아래 그림에서 실설(Member.team)이 연관관계의 주인이고 점섬(Team.members)은 연관괸계의 주인이 아니다.
    ![다대일 양방향](https://lh3.googleusercontent.com/pw/ACtC-3fQgml28u3o_auT5VeDQBmvYFGbOK8reZrpiVUbgIvDgPj2IcmBHIq9MU77dtOvGQKz-DcABHlJnTQ2eZeOF7Mi_LKsExN8t0AzDxC-gRKY3_VoaBStdBVhZmkNI7O39DpA2ebznZvY33jI90_gb819pg=w969-h546-no?authuser=0)

-   회원 / 팀 엔티티는 아래와 같이 설정가능 하다.

    ```java
    @Entity
    public class Member {
        ...
        @ManyToOne
        @JoinColumn(name = "TEAM_ID")
        private Team team;

        public void setTeam(Team team){
            this.team = team;

            // 무한 루프 방지
            if(!team.getMembers().contains(this))
                team.getMembers().add(this);
        }
        ...
    }
    ...

    @Entity
    public class Team {
        ...

        @OneToMany(mappedBy = "team")
        private List<Member> members = new ArrayList<>();

        public void addMember(Member member){
            this.members.add(member);

            // 무한 루프 방지
            if(member.getTeam() != this) {
                member.setTeam(this);
            }
        }
    }
    ```

-   양방향은 외래 키가 있는 쪽이 연관관계의 주인이다.
-   양방향 연관관계는 항상 서로를 참조해야 한다.
-   편의 메소드는 한 곳에만 작성 하거나 양쪽다 작성 가능하다.
-   양쪽 다 작성시 무한루프에 주의한다.

## **6.2 일대다**

-   일대다 관계는 다대일 관계의 반대 방향이다.
-   엔티티를 하나 이상 참조할 수 있으므로 자바 컬렉션을 사용해야 한다.

### **6.2.1 단방향 [1:N]**

-   하나의 팀은 여러 회원을 참조할 수 있다 : 일대다 / 팀은 회원들은 참조하지만 반대로 회원은 팀을 참조하지 않는다 : 단방향
    ![일대다 단방향](https://lh3.googleusercontent.com/pw/ACtC-3edfQWsC0IyH0JUQ8InjeBqt2nWbVvGztHI6yjv828ZNXUogTzpnjUHoq8sJ_7mRDQF_NHuHo8qhplUTrZpXxnYnpojXlmcxcwP0W01ZMCnZFyQsbaNqaAPpyeOuU7-p85Z04nA71fpN2-QS2iNXceIwQ=w959-h503-no?authuser=0)

-   위 그림을 보면 팀 엔티티의 Team.members로 회원 테이블의 TEAM_ID 외래 키를 관리한다.
-   이 매핑은 반대쪽 테이블에 있는 외래 키를 관리한다. 그 이유는 일대다 관계에서 외래키는 다(N) 쪽 테이블에 있기 때문이다.
-   Members 엔티티에는 외래키를 매핑할 수 있는 참조 필드가 없다.

    ```java
    @Entity
    public class Team {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "TEAM_ID")
        private Long id;

        private String name;

        @OneToMany
        @JoinColumn(name = "TEAM_ID")
        private List<Member> members = new ArrayList<>();

        // getter, setter
    }

    @Entity
    public class Member {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "MEMBER_ID")
        private Long id;

        private String username;

        // getter, setter
    }
    ```

-   일대다 단방향 관계를 매핑할 때는 @JoinColumn을 명시해야한다.
-   그렇지 않으면 JPA는 연결 테이블을 중간에 두고 연관관계를 관리하는 조인테이블 전략을 기본으로 사요한다.

**일대다 단방향 매핑의 단점**

-   매핑한 객체과 관리하는 외래 키가 다른 테이블에 있다는 점이다.
-   다른 테이블에 외래 키가 있으면 연관 관계를 처리하기 위한 UPDATE 가 추가로 일어난다.

    ```java
    private static void saveMember(EntityManager em) {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        Team team1 = new Team("team1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(member1); // INSERT-member1
        em.persist(member2); // INSERT-member2
        em.persist(team1); // INSERT-team1, UPDATE-member1.fk, UPDATE-member2.fk
    }
    ```

-   Member 엔티티는 주인이 아니고 Team 엔티티가 주인이라 Team 엔티티를 insert 할때 MEMBER 테이블에 FK 값이 업데이트 된다.

**일대다 단방향 매핑보다 다대일 양방향 매핑을 사용하자**

-   일대다 단방향 매핑을 사용하면 엔티티를 매핑한 테이블이 아닌 다른 테이블의 외래 키를 관리해야 한다.
-   좋은 방법은 일대다 단방향 매핑 대신에 다대일 양방향 매핑을 사용하는 것이다.

### **6.2.2 일대다 양방향 [1:N, N:1]**

-   일대다 양방향 매핑은 존재하지 않는다.
-   정확히는 양방향 매핑에서 @OneToMany 는 연관관계의 주인이 될 수 없다.
-   이러한 이유로 @ManyToOne 에는 mappedBy 속성이 없다.
-   하지만 일대다 단방향 매핑 반대편에 외래 키를 사용하는 다대일 단방향 매핑을 읽기 전용으로 하나 추가하면 된다.

    ![일대다 양방향](https://lh3.googleusercontent.com/pw/ACtC-3fu0tJahTz5dVzjy5NK8pikFUGMXBpj4US0AP0R3I7FV4Z8cZdK5_EmpWmO-FnY76pYZgz16Rz5VPEn5r2HXTPkf6wBWg1YF772-vS4OsPnMNquh62aSFmleD8L0kjAPleWpAF3hNASCvkowNS-oQQ6-A=w1003-h501-no?authuser=0)

    ```java
    public class Member {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "MEMBER_ID")
        private Long id;

        private String username;

        @ManyToOne
        @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
        private Team team;
    }
    ```

-   이렇게 되면 둘다 동일한 키를 관리하여서 문제가 발생할 수 있다.
-   반대편인 다대일 쪽은 insertable = false, updatable = false 설정을 해준다.
-   하지만 이 설정은 일대다 양방향 이라기보다, 일대다 단방향 매핑 반대편에 다대일 단방향 매핑을 읽기 전용으로 추가한 것이다.
-   여전히 update 문제는 그대로 가지고 있다.

## **6.3 일대일 [1:1]**

-   일대일 관계는 양쪽이 서로 하나의 관계만 가진다.
-   일대일 관계는 그 반대도 일대일 관계다
-   일대다, 다대일은 항상 다(N) 쪽이 외래 키를 가진다. 반면 일대일 관계는 주 테이블이나 대상 테이블 둘중 어느 곳이나 외래키를 가질 수 있다.
-   일대일 관계는 주 테이블이나 대상 테이블 중에 누가 외래 키를 가질지 선택해야 한다.

**주 테이블에 외래 키**

-   외래 키를 객체 참조와 비슷하게 사용할 수 있다.
-   주 테이블이 외래 키를 가지고 있으므로 주 테이블만 확인해도 대상 테이블과 연관관계가 있는지 알 수 있다.

**대상 테이블에 외래 키**

-   테이블 관계를 일대일에서 일대다로 변경할 때 테이블 구조를 그대로 유지할 수 있다.

### **6.3.1 주 테이블에 외래키**

-   JPA 같은 경우 주 테이블에 외래키가 있으면 좀 더 편리하게 매핑할 수 있다.

**단방향**

-   MEMBER가 주 테이블이고 LOCKER는 대상 테이블이다.
    ![MEMBER-LOCKER](https://lh3.googleusercontent.com/pw/ACtC-3c-d-YGmHDm6jQKTFu7oAIDFa6piohkeVsfwytykhndegNjpnsADbG57_zU5xjyZ-idSby3g6-fczFLNFRR0Wwg8BwlsmMiXkwkok5jO8mgNaVKqntBi1weEMqo3nwGFBy5Vdu6tje6xjT1vmxpCIX7mw=w1228-h653-no?authuser=0)
