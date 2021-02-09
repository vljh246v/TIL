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
