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

    ```java
    public class Member {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "MEMBER_ID")
        private Long id;

        private String username;

        @OneToOne
        @JoinColumn(name = "LOCKER_ID")
        private Locker locker;
    }
    ...

    public class Locker {

        @Id @GeneratedValue
        @Column(name = "LOCKER_ID")
        private Long id;

        private String name;
    }
    ```

**양방향**

-   양방향 관계
    ![일대일 주 테이블에 외래 키, 양방향](https://lh3.googleusercontent.com/pw/ACtC-3c3mG3CSdrmv7RcyUVG9SNf6OEnjOapaSj-ejV1pS5aG5MCVqtZcPu_1g0Ds1nNo_WtqVuefBrOJ_n8iyUWu4dKB25frBqoU6Lp9kd8XDjSFiSCUfPOBEqNBiIHPTv8J6G19T8H6has2zOCrG47P1rPMA=w1228-h685-no?authuser=0)

    ```java
    public class Member {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "MEMBER_ID")
        private Long id;

        private String username;

        @OneToOne
        @JoinColumn(name = "LOCKER_ID")
        private Locker locker;
    }
    ...

    public class Locker {

        @Id @GeneratedValue
        @Column(name = "LOCKER_ID")
        private Long id;

        private String name;

        @OneToOne(mappedBy = "locker")
        private Member member;
    }
    ```

-   양방향이므로 연관관계의 주인을 정해야 한다.
-   MEMBER 테이블이 외래 키를 가지고 있으므로 Member 클래스의 Member.locker 가 연관관계의 주인이다.
-   따라서 반대쪽 Locker.member 에 mappedBy를 선언해 줘야 한다.

### 6.3.2 대상 테이블에 외래 키

**단방향**

-   대상 테이블에 외래 키가 있는 단방향 관계

    ![일대일 대상 테이블에 외래 키, 단방향](https://lh3.googleusercontent.com/pw/ACtC-3c1Xt8H8dbcKE0iEJblB9DgzMVLAY8ZltE5aAG1RLFe7iAUoUybl905WMbW4qnt86o3_koWabNqY3eXOaBYlCkzPI5juSRoi82awxapxYUJS5ZEgErUfTH4aa7Zqdn4m4XIijmC-s3vloi3uSfcaMV3Ag=w1228-h663-no?authuser=0)

-   일대일 관계 중 대상 테이블에 외래 키가 있는 단방향 관계는 JPA에서 지원하지 않는다. 그리고 이런 모양으로 매핑할 수 있는 방법도 없다.
-   이때는 단방향 관계를 Locker 에서 Member 방향으로 수정하거나, 양방향 관계로 만들고 Locker 를 연관관계의 주인으로 설정해야 한다.

**양방향**

-   대상 테이블에 외래 키가 있는 양방향 관계

    ![일대일 대상 테이블에 외래 키, 양방향](https://lh3.googleusercontent.com/pw/ACtC-3coS-ddbvzQpY2I4VG7vJGyxPhwVC-__vTkRVBHa_DpyTdaB0TLLUpzG7ESjz1NKboQKtaSGT73IZ6AaLim2KhEAGQVcJXarBWwHn2llzYGbaJO-uf6Db9pEevZYzth8zfpTVu1x38iQXmJd1YE6yxI7g=w1228-h690-no?authuser=0)

    ```java
    public class Member {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "MEMBER_ID")
        private Long id;

        private String username;

        @OneToOne(mappedBy = "member")
        private Locker locker;
    }
    ...
    public class Locker {

        @Id @GeneratedValue
        @Column(name = "LOCKER_ID")
        private Long id;

        private String name;

        @OneToOne
        @JoinColumn(name = "MEMBER_ID")
        private Member member;
    }
    ```

-   일대일 매핑에서 대상 테이블에 외래 키를 두고 싶으면 이렇게 양방향으로 매핑한다.
-   주 엔티티인 Member 엔티티 대신에 대상 엔티티인 Locker 를 연관관계의 주인으로 만들어서 LOCKER 테이블의 외래 키를 관리하도록 했다.

## **6.4 다대다**

-   관계형 데이터베이스는 테이블 2개로 다대다 관계를 표현하기 보다는 일대다, 다대일 관계로 풀어내는 연결 테이블을 사용한다.
-   보통 중간 연결 테이블을 추가한다.

    ![테이블 다대다 연결 테이블](https://lh3.googleusercontent.com/pw/ACtC-3cmGQVLvcJVkb13za1hI3IZvjtIvIA9N0ZkuXbJmjdrtDl5-iFme5CISNblQbEPD_aPWHjRBW8A55THnT7kiDOWx_LkumM1ErRSaRQKUC-S2HJu08n-3cGLYl2chI9m_ROEvhv5KdwfBro61kG09KopeQ=w1228-h546-no?authuser=0)

-   그런데 객체는 테이블과 다르게 객체 2개로 다대다 관계를 만들 수 있다.
-   각각 컬렉션으로 참조하면 된다.

### **6.4.1 다대다 단방향**

-   다대다 단방향 관계인 회원과 상품 엔티티

    ```java
    public class Member {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "MEMBER_ID")
        private Long id;

        private String username;

        @ManyToMany
        @JoinTable(name = "MEMBER_PRODUCT", joinColumns = @JoinColumn(name = "MEMBER_ID"),
            inverseJoinColumns = @JoinColumn(name =
                "PRODUCT_ID"))
        private List<Product> products = new ArrayList<>();
    }
    ...
    public class Product {

        @Id @GeneratedValue
        @Column(name = "PRODUCT_ID")
        private Long id;

        private String name;
    }
    ```

-   회원 엔티티와 상품 엔티티를 @ManyToMany로 매핑했다.
-   여기서 중요한 점은 @ManyToMany와 @JoinTable을 사용해서 연결 테이블을 바로 매핑한 것이다.
-   따라서 중간 엔티티 없이 매핑을 완료할 수 있다.

-   @JoinTable

    -   @JoinTable.name : 연결 테이블을 지정한다. 여기서는 MEMBER_PRODUCT 테이블을 선택했다.
    -   @JoinTable.joinColumns : 현재 방향인 회원과 매핑할 조인 컬럼 정보를 지정한다.
    -   @JoinTable.inverseJoinColumns : 반대 방향인 상품과 매핑할 조인 컬럼 정보를 지정한다.

-   MEMBER_PRODUCT 테이블은 다대다 관계를 일대다, 다대일 관계로 풀어내기 위해 필요한 연결 테이블일 뿐이다.
-   @ManyToMany 를 사용해 연결 테이블을 신경 쓰지 않아도 된다.

-   다대다 관계를 저장하는 예제는 아래와 같다.

    ```java
    private static void save(EntityManager em) {
        Product productA = new Product();
        productA.setId(1L);
        productA.setName("상품 A");
        em.persist(productA);

        Member member1 = new Member();
        member1.setId(1L);
        member1.setUsername("회원1");
        member1.getProducts().add(productA);
        em.persist(member1);
    }
    ```

### **6.4.2 다대다: 양방향**

-   역방향도 @ManyToMany 를 사용하여 원하는 곳에 mappedBy로 연관관계의 주인을 지정한다.

    ```java
    public class Product {

        @Id @Column(name = "PRODUCT_ID")
        private Long id;

        @ManyToMany(mappedBy = "products")
        private List<Member> memberList;

        private String name;
    }
    ```

-   양방향 연관관계는 편의 메소드를 추가해서 관리하는것이 편리하다.

### **6.4.3 다대다: 매핑의 한계와 극복, 연결 엔티티 사용**

-   @ManyToMany를 사용하면 연결 테이블을 자동으로 처리해주므로 도메인 모델이 단순해지고 여러 가지로 편리하다.
-   다만 실무에서 사용하기에는 한계가 있다.
-   단순 연결 테이블이 아닌 다른 데이터가 들어갈 경우 다른 컬럼이 필요하다.
-   아래처럼 연결 테이블에 키 외에 다른 컬럼을 추가 한다면 더는 @ManyToMany를 사용할 수없다.

    ![테이블, 연결 테이블에 필드 추가](https://lh3.googleusercontent.com/pw/ACtC-3fAab0icdYxLa_QBPSjlEMyGqsx7SiI2kTGN8Nfh9RNMmQNNPFMHM36JruA2rpouXxdLZUEZaY2UP0vTISWvz3T-uq7o2G49xFqR47oLOudYO2lqs4h80-8DiSAej9CpfxGT7iq5Fo_b4qFaQUQI0sMOA=w847-h174-no?authuser=0)

-   결국 아래 그림처럼 연결 테이블을 매핑하는 연결 엔티티를 만들어야 한다.
-   그리고 엔티티 간의 관계도 테이블 관계처럼 다대다에서 일대다, 다대일 관계로 풀어야 한다.

    ![클래스, 다대다를 푸는 연결 엔티티](https://lh3.googleusercontent.com/pw/ACtC-3e8OA3-ZL84by2u8zGRN_XVKRWokn_BAe74TIhSgRpPSEYKPNHSXm_GknW3M7rq7d_hXnsXN_KaCiTtE-pvv7gupFiX9-8zFKo4jGIlvdPms3WiXKUYqOCkKlrPNXs_ukgf3XSEuQ1v97Ns6sRylw7lrQ=w701-h164-no?authuser=0)

    ```java
    @IdClass(MemberProductId.class)
    public class MemberProduct {

        @Id
        @ManyToOne
        @JoinColumn(name = "MEMBER_ID")
        private Member member;

        @Id
        @ManyToOne
        @JoinColumn(name = "PRODUCT_ID")
        private Product product;

        private int orderAmount;
    }
    ```

-   @IdClass 복합키 매핑

**복합 기본 키**

-   회원-상품 엔티티는 기본키가 복합키이다.
-   JPA에서 복합키를 사용하려면 별도의 식별자 클래스를 만들어야 한다.
-   @IdClass 로 지정 가능하다.
-   복합키를 위한 식별자 클래스는 다음과 같은 특성이 있다.
    -   Serializable을 구현해야 한다.
    -   equals 와 hashCode를 구현해야 한다.
    -   기본 생성자가 있어야 한다.
    -   식별자 클래스는 public 이어야 한다.
    -   @IdClass 또는 @EmbeddedId 을 사용할 수 있다.

### **6.4.4 다대다: 새로운 기본 키 사용**

-   추천하는 기본 키 생성 전략은 데이커베이스에서 자동으로 생성해주는 대리 키를 Long 값으로 사용하는 것이다.

-   간편하고, 영구히 쓸 수 있으며 비즈닛느에 의존적이지 않다.

    ![테이블, N:M 다대다 새로운 기본 키](https://lh3.googleusercontent.com/pw/ACtC-3fk3gZEMy9zCT-tXAQpuTyIG1DpAhRU7qUbWZ9wtEZTABhwH63g5HGv3mqFid_xoI2AL8v5Qk1r7CDK3aidVNrfR0yaTjbfWjhueZdfg6zgIt5uvEwLyDAPrdfgXyxvHfS4fHge-tlKPrCJ4VnMYpPMSw=w867-h217-no?authuser=0)

-   MEMBER_ID, PRODUCT_ID는 외래 키로만 사용한다.

### **6.4.5 다대다 연관관계 정리**

-   식별 관계 : 받아온 식별자를 기본 키 + 외래 키로 사용한다.
-   비식별 관계 : 받아온 식별자는 외래 키로만 사용하고 새로운 식별자를 추가한다.
