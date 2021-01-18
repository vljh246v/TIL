# **1. JAP 소개**

## **1.1 SQL을 직접 다룰 때 발생하는 문제점**

-   관계형 데이터베이스는 가장 대중적이고 신뢰할 만한 안전한 저장소
-   JDBC API 를 사용해서 SQL을 데이터베이스에 전달

### **1.1.1 반복**

-   예를들어 회원 관리 기능을 개발해 보자

    1. 회원 관리용 객체 생성 및 회원용 DAO 생성

        ```java
        // 회원 객체
        public class Member {
            private String memberId;
            private String name;
            ...
        }

        // 회원용 DAO
        public class MemberDAO {
            public Member find (String memberId) {...}
        }
        ```

    2. 회원 조회용 기능 개발

        1. 회원 조회용 SQL 작성

            ```sql
            SELECT MEMEBER_ID, NAME
            FROM MEMBER M
            WHERE MEMBER_ID = ?
            ```

        2. JDBC API 를 사용해서 SQL TLFGOD

            ```java
            ResultSet rs = stmt.executeQuery(sql);
            ```

        3. 조회 결과를 Member 객체로 매핑한다.

            ```java
            String memeberId = rs.getString("MEMEBER_ID");
            String name = rs.getSring("NAME");

            Member member = new Member(memeberId, name);
            ```

-   등록 기능 또한 이것과 비슷한 단계를 거친다.
-   기능이 추가될 때 마다 비슷한 일을 반복해야 한다.
-   데이터베이스는 객체 구조와는 다른 데이터 중심의 구조를 가지므로 객체를 데이터베이스에 직접 저정하거나 조회할 수 없다.
-   개발자가 애플리케이션과 데이터베이스 중간에서 변환 작업을 해주어야 한다.
-   문제는 너무 많은 코드를 반복해서 작성해야 한다.

### **1.1.2 SQL에 의존적인 개발**

-   만약 회원의 연락처도 함께 저장해달라는 요구 사항이 추가 되었다.
-   이런 상황에서는 모든 기능에 연관된 QUERY를 전부 분석해 수정해 주어야한다.

    ```java
    String insertSql = "INSERT INTO MEMBER(MEMBER_ID, NAME, TEL) VALUES(?, ?, ?)"; // 등록 sql 수정 -> tel 컬럼 추가
    String findSql = "SELECT MEMBER_ID, NAME, TEL FROM MEMBER WHERE MEMBER_ID = ?"; // 조회 sql 수정 -> tel 컬럼 추가
    // 업데이트 sql도 수정 필요
    ```

-   아래처럼 데이터베이스가 아니라 자바 컬렉션에 보관한다면 필드를 추가한다고 해서 모든 기능의 QUERY를 분석할 필요는 없다.

    ```java
    list.add(member); // 등록list.
    Member member = list.get(xxx) //조회
    member.setTel("111-222-333"); // 수정
    ```

-   데이터 접근 계층을 사용해서 SQL을 숨겨도 어쩔 수 없이 DAO를 열어서 어떤 SQL이 실행되는지 확인해한다.
-   개발자들은 엔티티를 신뢰하고 사용할 수 없다.
-   진정한 의미의 계층 분할이 아니다.
-   논리적으로는 엔티티와 SQL/JDBC API 가 아주 강하게 의존관계를 가지고 있다.

### **JPA와 문제 해결**

-   JPA를 사용해서 객체를 데이터베이스에 저장하고 관리할 때, 개발자가 직접 SQL을 작성하지 않는다.
-   JPA 가 개발자 대신에 적절한 SQL을 생성해서 데이터베이스에 전달한다.

    ```java
    // 저장기능
    jpa.persist(member);

    // 조회 기능
    String memberId = "helloId";
    Member member = jpa.find(Member.class, memberId);

    // 수정 기능
    Member member = jpa.find(Member.class, memberId);
    member.setName("이름변경");

    // 연관된 객체 조회
    Member member = jpa.find(Member.class, memberId);
    Team team = member.getTeam(); // 연관된 객체 조회
    ```

-   위 예제 코드 작성만으로도 적절한 SQL을 생성해 실행시켜 준다.

## **1.2 패러다임의 불일치**

-   관계형 데이터베이스는 데이터 중심으로 구조화되어 있고, 집합적인 사고를 요구한다.
-   그리고 관계형 데이터베이스는 객체지향에서 이야기하는 추상화, 상속, 다형성 같은 개념이 없다.
-   객체와 관계형 데이터베이스는 지향하는 목적이 다르므로 패러다임 불일치가 발생한다.
-   그렇기 때문에 객체 구조를 테이블 구조에 저장하는 데는 한계가 있다.
-   이러한 불일치 문제는 개발자가 중간에서 해결을 해야 하는데, 해결에 있어 많은 시간과 코드를 소비해야한다.

### **1.2.1 상속**

-   객체는 상속이라는 기능을 가지고 있지만 관계형 데이터베이스에서 테이블은 상속이라는 기능이 없다.
    ![객체 상속 모델](https://lh3.googleusercontent.com/nyXcMwiOdmTKi05WFsjm_2s_rw4Y0x3DYx4-Ggjlo1Pm-6zQJ_UDAqVtR0lb3PO1r2azbgKEzmhJRjnpnoYeO74ljkWeJ_gkJpNA8wNo2MDdvZ5g_nULihl-4P27XtYgTExils3Uf5i9YtBhjcW9z-fI29md956VRr8DERuACzfZ3NaTzOd89_Ef5XLD8ZiKPd_X1Fw4QnQ_5cWxpoxULKkH58RtRPiOq8_d8OfUYszef4QcfqyDImPvqeIocy-_QX68CZl8-ZxcvWZHDeOrjS-03aPj0IU0lKqWxYrEfFTdTXBgsRySTLJUOnGGdpjE6QNFIvJNlqqE6UQL1TCWYXunx3XuubW9GNmHrYob6lYgccYWpUFng0yUDFcl5rB55CP3Rq4WwDfVITxQIo94uIYGbHVej8S93a5HGNDd1-dWOSFvMREEe6M_xQVjjGrAKkMi8eEF1scv1iNPYBF8YEqsyBae5jpZXENTIqWVcARDPOpZcJseI-TBpJ9I3GvsrWwPZUZDo__zkQPH-eO2Nz68i31yl1NKBOM2VLySewTELj-VgKddWTpnZZBsOQQcobUEQLUzxy3Lexix4BzNuOFiI-riuQO-Ha4kTytT_Jrltt4TRkCJVe1HMFBJFjMwHLvn-Le5i7zux2wO6KgcglvZ4O_sNJZu0s4BhALwiY5AOqRzt5PpS2C_OFmPWlY=w784-h413-no?authuser=0)

-   관계형 데이터베이스에서는 슈퍼타입 서브타입 관계를 이용해 상속과 유사한 형태로 테이블을 설계할 수 있다.
-   아래 모델에서는 DTYPE 컬럼을 사용해 어떤 자식 테이블과 관계가 있는지 정의했다.
    ![테이블 모델](https://lh3.googleusercontent.com/Y3lAlFIykfT10qtnag7rrKM0YvCBcIW_ZUHaotYLzVNKtnQB9cgOBMJ1nwrSfvNrF9zv6zw1pTs5usAjkg6gsG69VEtvfxpYBe4Q-I-0bj0BaQAc3KVyj62g2fUzOwt11RkVRMe7upLx3KCf3qeytdFrAvh5785fxfEwl4ENoPebi6Nb2h4vX4tF2Ao14nlAWw81IDVNWg8ex1lDvTXtVuhRW9xOQf-gyDQ_u0YZPIWNhkpPDvfetC52ZM3j6bHaQhQaH7aYckp59cdYPlOCNmWs6CUHijzBN6lR44d1phzYLEVre8orQW6ydYYkn_3VQuRgfAy3izxn_jQIEJ42TTZGDt93v9Z5m2R5EydSilxH58ow6s2lbu7uZZBwaPRWwHViJ3fJ-m4MYWjfK8c2jCUVVbfodY0dcEweXK3MelG18fLJD4b_JSr47G_DPWP2RYPgE2vMsPUVOgp01BWiyVnzpe4gh1Z0Le24EPh_60QBnZQ2y4884K6owygG0Qyki7qK6JXiq73vfywgU1vEBs1x--9rKa5hBkMLtpfNtsmSK886Tpn9sKm76O8Bo3asYkD8vjc7yL7GwwbD4ZBNqa9EQSnvc-VmT7HG5yHFlXxTF12XEIbKm_jFkhPPeaV_QDEvZg0UPu7yMVCMnRTbzLFm83hBUwtHBxvAi9_mBqS_PB1VIg3Cd8B12J4E8D8=w966-h408-no?authuser=0)

    ```java
    // 객체 모델 코드
    abstract class Item {
        Long id;
        String name;
        int price;
    }

    class Album extends Item {
        String artist;
    }

    class Movie extends Item {
        String director;
        String actor;
    }

    class Book extends Item {
        String author;
        String isbn;
    }
    ```

-   Album 객체를 저장하려면 이 객체를 분해해서 두 SQL을 작성하여야 한다.

    ```sql
    INSERT INTO ITEM ...
    INSERT INTO ALBUM ...
    ```

-   INSERT 를 하기위해서는 부모 객체에서 부모 데이터, 자식 객체에서 자식 데이터만 꺼내와서 두개의 INSERT QUERY 를작성해야 한다.
-   이러한 과정이 모두 패러다임의 불일치를 해결하려고 소모하는 비용들이다.
-   만약 객체들을 데이터베이스가 아닌 자바 컬렉션에 보관한다면 자식/부모 타입에 대한 고민 없이 가능하다.

    ```java
    list.add(album);
    list.add(movie);

    Album album = list.get(albumId);
    ```

**JPA 상속**

-   JPA 는 이러한 상속 패러다임 불일치 문제를 개발자 대신해서 해결해준다.
-   마치 자바 컬렉션에 저장하듯 객체를 저장하면 된다.
    ```java
    jpa.persist(album);
    ```
-   그러면 JPA는 다음 SQL을 실행해서 ITEM, ALBUM 두 테이블에 나눠서 저장한다.
    ```sql
    INSERT INTO ITEM ...
    INSERT INTO ALBUM ...
    ```
-   조회 또한 마찬가지로 JPA가 두 테이블을 조인해서 필요한 데이터를 조회한다.

### **1.2.2 연관관계**

-   객체는 참조를 사용해 연관된 객체를 조회한다.
-   테이블은 외래키를 사용해 JOIN후 연관된 테이블을 조회한다.
    ![연관관계](https://lh3.googleusercontent.com/rTNXnXc_fWdATxTlzVS78x-a5-Yb3yA4Wc-LjiHu5OLCCJbPlvtgMRNGl_XRrJEwcLhW4dkCVilWXdUUaqSqX-BroVYemlkBkJGRlyA46-ONea_k9CwBd4kMzwZXuGgvRaqv0tAQtqTGT-34aMYWbBpszKxyyWIGCYn9xzkxpRwo399ZYfOb7iupmssfWj7GK-0_0y4Q8roGN2Zx6W8ZWoIJVsqoIFeoAsBvr1uy1t2hwZY5TfSB9B2gOmVlCxy05Bohpu5U7ECUACSa7arNhSvomiUfRRCKo2NLLdtt_JiBz8qvHFyXM3IYC3-PHLjXU62LG0Bx_63laF9qPeP3C0TsLsip-mcGdAwH4PhUGnoWKCCLIrkoy36qm7WNVeRWqKNxLDDw8n63xYOzZ6JQt74ZzKhpRjboW-FvYBfRX9x46Esf_2H4uR1aZO9kL6ovFS_FWJU-mknt_bZ18xurZhxQUmsHjyEd1g10SRHfQMYMWWXzeti9e4-5mkbB2ZxGBQuGhIdclw9eWgg9wY84zauxGlk5_nOlyaHvh-NMEbdJoPwx9cnQH1umreBhNUalEPUC-COvftHfEnrjlLhrq3dRbE7_OMnNS7XSTAEG-hezMWojkzyAFVkC-BeiImpJ3cPHDGJKY5BQTcbn61OGB-4rf_8pVIIINkjPVBOeGkUCRww31bbUeUFtKJB327o=w1063-h560-no?authuser=0)
-   Member 객체는 Member.team 객체의 참조를 보관해서 관계를 맺음

    ```java
    class Member {
        Team team;
        Team getTeam() {
            return team ;
        }
    }

    class Team {

    }
    ```

-   Member 테이블은 MEMBER.TEAM_ID 외래 키 컬럼을 사용해서 TEAM 테이블과 관계를 맺는다.
-   JOIN 을 통한 MEMBER 테이블과 연관된 TEAM 테이블 조회가 가능하다.
    ```sql
    SELECT M.*, T.*
      FROM MEMBER M
      JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID
    ```
-   또한 객체는 참조가 있는 방향으로만 조회할 수 있지만, 테이블은 외래 키 하나로 반대 참조도 가능하다.

**객체를 테이블에 맞추어 모델링**

-   테이블에 맞춤 객체 모델링

    ```java
    class Member {
        String id;          // MEMBER_ID 컬럼 사용
        Long teamId;        // TEAM_ID FK 컬럼 사용
        String username;    // USERNAME 컬럼 사용
    }

    class Team {
        Long id;            // TEAM_ID PK 사용
        String name;        // NAME 컬럼 사용
    }
    ```

-   관계형 데이터베이스일 경우 조인을 위해 teamId 필드에 외래 키 값을 보관해도 된다. 하지만 객체는 참조를 통해 보관해야 한다.
    ```java
    Team team = member.getTeam();
    ```
-   teamId 처럼 관계형 데이터베이스가 사용하는 방식에 맞춘다면 좋은 객체 모델링을 기대하기 어렵다.

**객체지향 모델링**

-   참조를 사용하는 객체 모델

    ```java
    class Member {
        String id;          // MEMBER_ID 컬럼 사용
        Team team;          // 참조로 연관관계를 맺는다.
        String username;    // USERNAME 컬럼 사용

        Team getTeam() {
            return team;
        }
    }

    class Team {
        Long id;            // TEAM_ID PK 사용
        String name;        // NAME 컬럼 사용
    }
    ```

-   반대로 객체지향 모델링을 사용하면 테이블에 저장하거나 조회하기가 쉽지 않다.
-   쿼리 작성을 위해서는 member.getTeam().getId(); 와 같은 방법을 통해 ID를 확보하고 쿼리를 작성해야 한다.

**JPA와 연관관계**

-   JPA를 사용한다면 아래와 같은 작업이 가능하다.
    ```java
    member.setTeam(team);   // 회원과 팀 연관관계 설정
    jpa.persist(member);    // 회원과 연관관계 함께 저장
    ```
-   객체를 조회할 때 외래키를 참조로 변환하는 일도 JPA가 처리해준다.
    ```java
    Member member = jpa.find(Member.class, memberId);
    Team team = member.getTeam();
    ```

### **1.2.3 객체 그래프 탐색**

-   객체에서 연관된 객체를 찾아가는 것을 객체 그래프 탐색이라고 한다.
-   보통은 참조를 통해 객체 그래프 탐색이 이루어 진다.
-   하지만 QUERY에에 의존적인 DAO 라면 그래프 탐색이 어느 단계에서 끝날지 QUERY에 의존적이게 된다.
-   그렇기 때문에 코드만 보고서는 해당 객체가 가지는 참조를 통해 어느 단계까지 가능할지 알 수 없다.
-   QUERY에 의존적인 경우 대표적으로 상황에 따라 객체를 조회하는 메소드를 여러벌 생성하게 된다.

        ```java
        memberDAO.getMember();
        memberDAO.getMemberWithTeam();
        memberDAO.getMemberWithOrderWithDelivery();
        ...
        ```

**JAP와 객체 그래프**

-   JPA는 연관된 객체를 사용하는 시점에 적절한 SQL을 실행한다.
-   실제 객체를 사용하는 시점까지 데이터베이스 조회를 미루는것도 가능한다.(지연로딩)

### 1.2.4 비교

-   데이터베이스는 각 키를 활용해 ROW를 구분한다.
-   객체는 동일성 비교와 동등성 비교라는 두가지 방법을 사용한다. 따라서 객체와 테이블 로우를 비교하는 방법은 차이가 있다.

    ```java
    class MemberDAO {
        public Member getMember(String member){
            String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";
            ...
            // JDBC API, SQL 실행
            return new Member(...);
        }
    }
    ...
    Member member1 = memberDAO.getMember(memberId);
    Member member2 = memberDAO.getMember(memberId);

    member1 == member2 // false
    ```

-   위 내용처럼 실제 객체를 비교하면 다른 값이 나오지만 객체 측면에서 보았을때는 동일 값이 나와야 한다.

**JPA와 비교**

-   JAP 같은 경우 같은 트랜잭션일 때 같은 객체가 조회되는 것을 보장한다.

    ```java
    String memberId = 100;
    Member member1 = jpa.find(Member.class, memberId);
    Member member2 = jpa.find(Member.class, memberId);

    member1 == member2 // true
    ```

---

## **1.3 JAP란 무엇인가?**

-   JPA란 자바 진영의 ORM 기술 표준이다.
-   애플리케이션과 JDBC 사이에서 동작한다.
    ![JPA](https://lh3.googleusercontent.com/ZRbShc39h0gsy1bmuMmQWMMrG-yqDnET3JMF9SzPvMB67LBWRHxjhJeqGCjZhvzltOxJiffgI9Hci00jAAG5i9JI8IKN3GAR9_KIpKdldx4EwHyxJBfHRCj81giyV8N1jwE8uacLFidkAQfxBvB5t3MyiU6dZnepnHR6HpXwBLLoqoxOkY3rUn54Tty9hQSKnDyq7qGH0CHasHHXqZx4N5TnNjqMR082FurfZSBvHsGLR6FtKajOKgZcetkNIJPaA8QO7re7w9Sy3mOWL8XOnHMGPI_4enm2ofYUHZZcc-IQdAMdbRIx8bjhjHLIpvQG0uYVo6RxN1mtDbpeq8R5eMKnuToWC8pcLbTXB0yoD_ZgFPR2PKlYxp6VNzvouUMD4_Zrizf2u5Snme2Fo4dmguNE3l2PT8-f1S56MuhCE2JUcWKwBE2KIOkJxPrHlc2mTYsqewoIw1XqzhPnatSm9Sr4Jj2cxqXB6bUPuxvqJI918k2KZxkqmmNJLzahjGd6oRZg_2vAohyO3FIi4NdVXUoHfIn-yofWDXp66zdO2YKdT3ENsuEWAm_gMie7QifWc7unJxunbk4KArHAhJJLkFH0XcnrTjw7NjgcOO_20qzL3LglQHOMKbK2_pVEzk_VPel3ArZ45zeZivPkkuxFQjqf2ahwcOcTAVY9eNk3H2GmOmj56a_-qDMM0bIi8Jg=w1510-h448-no?authuser=0)

-   ORM(Object-Relational Mapping)은 객체와 관계형 데이터베이스를 매핑한다는 말이다.
-   ORM 프레임워크는 객체와 테이블을 매핑해서 패러다임의 불일치 문제를 개발자 대신 해결해준다.
-   예를들면 객체를 데이터베이스에 저장할 때 객체를 마치 자바 컬렉션에 저장하듯이 ORM 프레임워크에 저장하면된다.
    ![JPA 저장](https://lh3.googleusercontent.com/ioQFmEiVdANGKdqRMi0AULoZOmALFaCXLbDYUBI_b4C3QusYgWbKa0W3j1pb1bl4CZFydE_utJC9kNABD9ttBwWQ_K-K7c0IGC3I6UrxRsmHyiFIjyzwz-jJTA02pgGw-vKxsbIH1Awr-QnkxYmAJH6g6qgBStGPz3TSL0oo74tzUX_kvlGK4FBrWrMQZNK5ZgTkTMAT73c2fGQ4MpXVNtU6ZD1Z8Ebwfc2HEKe2AXDeydfksd9gvVCcV1j6qnEmi9HxvFsfpm-zA28qaAqHZNSlSVg55GeQ1RTcVCAFklv8NmGopGPcPHF8XxJ4pR9XHslTtumjcOjdD9YTMc5S4GPQWRYsknZ_ZHafsdxh6jwgL2b6P-gv-i2BvqDzkLoBYpspLHDlMPLNnoLQTIv7188xbdmRy3ywUSXuWuTgpdd1OJ9H2VjiHCr_UYRJWZnekW8QYrK0nCl_bJMa-pFpXVFcKEs5-KOtRx3gOQ6AbSZgOPMSFIq_R6lnRJTLEuDTIvQ1H6mZYbDkxCzwOEs5KWieTCuw65bUzLgk3ZKR2Ihrd-2-kB-QaK1GgaXrC5xGxqyyvnW6OG64vv3u3DiM9uZRwqRVBkMBfoasfnEWT-7j_XGTijqvsORR5a-pAeb0iKvIkNRP0CRw0uAtZ57jNQ5qObJP3lqByNj0BbF38A_vfKQ9UmDRHwRSiq_q64M=w1228-h403-no?authuser=0)

-   자바 진영에서는 하이버네이트 프레임워크가 가장 성숙한 ORM 프레임워크로 사용되고있다.

### **1.3.1 왜 JPA를 사용해야 하는가?**

-   **생산성**
    -   자바 컬렉션에 저장하듯 사용가능
    -   반복적인 쿼리 작성 불필요
    -   데이터베이스 설계 중심의 패러다임을 객체 설계 중심으로 역전 가능
-   **유지보수**
    -   SQL에 의존적인 개발은 엔티티 필드 하나만 추가해도 연관된 등록/수정/조회 모두 변경시켜야 했음
    -   JPA를 사용하면 이러한 부분들에 리소스를 확실히 줄일 수 있음
    -   또한 패러다임 불일치 문제를 해결하여 객체지향 언어가 가진 장점을 활용할 수 있음
-   **패러다임의 불일치 해결**
    -   상속, 연관관계, 객체 그래프 탐색, 비교하기등 각종 패러다임 불일치 문제를 해결해줌
-   **성능**
    -   애플리케이션과 데이터베이스 사이에 계층이 하나 더 있으니 최적화 관점에서 시도핼 수 있는 것들이 많다.
    -   앞에서 말했던 것과 같이 동일 트랜잭션에서는 동일한 객체가 조회 되는것을 보장한다.
    -   결국 여러번 조회를 할 때에도 동일한 객체면 이전 객체를 재활용 하는 방법을 사용한다.
    -   SQL 기반 언어라면 동일 객체라도 호출 횟수만큼 쿼리가 동작한다.
-   **데이터 접근 추상화와 벤더 독립성**
    -   관계형 데이터베이스는 벤더마다 사용법이 다른 경우가 있다.
    -   결국 애플리케이션은 처음 선택한 데이터베이스 기술에 종속되고 다른 데이터베이스로 변경하기가 매우 어렵다.
    -   JPA는 데이터베이스와 애플리케이션 사이에 추상화된 데이터 접근 계층을 제공해서 특정 기술에 종속되지 않도록 한다.
-   **표준**
    -   JPA는 자바 진영의 ORM 기술 표준이다.
