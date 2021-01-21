# **3. 영속성 관리**

-   JPA가 제공하는 기능은 크게 두가지가 있음
    1. 엔티티와 테이블을 매핑하는 설계 부분
    2. 매핑한 엔티티를 실제로 사용하는 부분
-   엔티티 매니저는 엔티티를 저장 / 수정 / 삭제 / 조회 하는 등 엔티티와 관련된 일을 한다.
-   엔티티 매니저는 엔티티를 저장하는 가상의 데이터베이스로 생각하면 된다.

## **3.1 엔티티 매니저 팩토리와 엔티티 매니저**

-   데이터베이스를 하나만 사용하는 애플리케이션은 일반적으로 EntityManagerFactory 를 하나만 생성하고, 엔티티 매니저 팩토리에서 엔티티 매니저를 생성하여 사용한다.
-   엔티티 매니저 팩토리를 생성하는 비용은 상당히 크고 팩토리에서 엔티티 매니저를 생성하는 비용은 적다.
-   엔티티 매니저 팩토리는 여러 스레드가 동시에 접근해도 안전하므로 서로 다른 스레드간 공유 가능
-   엔티티 매니저는 동시성 문제가 발생하므로 스레드간 절대 공유 하면 안된다.
-   엔티티 매니저는 데이터베이스 연결이 꼭 필요한 시점까지 커넥션을 얻지 않고 보통 트랜잭션을 시작할 때 커넥션을 획득한다.
-   JPA 구현체들은 EntityMangerFactory 를 생성할때 커넥션풀도 만드는데, JEE 환경(스프링 프레임워크 포함)에서 사용하면 해당 컨테이너가 제공하는 데이터소스를 사용한다.

## **3.2 영속성 컨텍스트란?**

-   영속성 컨텍스트는 우리말로 해석하면 '엔티티를 영구 저장하는 환경' 이라는 뜻이다.
-   엔티티 매니저를 통해 엔티티를 사용하면 엔티티 매니저는 영속성 컨텍스트에 엔티티를 보관하고 관리한다.
-   persist 메소드는 단순히 엔티티를 저장한다고 표현하기 보다는, **'엔티티 매니저를 사용해서 회원 엔티티를 영속성 컨텍스트에 저장 한다'** 가 더 정확한 말이다.
-   영속성 컨텍스트는 논리적 개념에 가깝다.
-   영속성 컨텍스트는 엔티티 매니저를 생성할 때 하나 만들어진다. 그리고 그 엔티티 매니저는 영속성 컨택스트에 접근할 수 있고, 영속성 컨텍스트를 관리할 수 있다.

## **3.3 엔티티의 생명 주기**

![생명주기](https://lh3.googleusercontent.com/iXRwZ_0Drs_I7kHbiz2gkPkvycIs8diRT-HPW0S-SvxzU1Z-skl7SaIqqrRgYsmSn5xZ8hzD19MwLye44PvR-XHicn3b4fIg7jD1euTl8JJOP_Jv0u-8KR8PLwxKCbEEPQ5zcwslkYJ0cQWYKzgdKtBtdB9YWz3Vfk_yQjafh9krS_zvJoeEVHP9IlL-KlkRhLuTZ9gsyyX6NRMlyLP-U46viOpx8WOP5fS0dunLCoY-ATMnm_IowSk3EXISlwiye3_bnTcBVUDFRa2bVuFyi_k5wv0zqi6Rc2H_EkhDW1X2Smx392lsC5ZHOVIzg8VGKIl0ZTI8wgnW1_313JvqMe-3T97FXaZsGGcntB8R103hr9YROCPywejpdZL3rWeeXr0p860is0MwV2JPXYQP5SPBSv7tVkkaN51NhCx6Lonm9KEzZ3U9PJcpFLfsHOb2WDOxmYoB-75h0jKmS_ugrw_oNI8kLxw40kK8KA18SjZCPr5lNMznS2JxQR5PMJZIFbiYUvQq5_9o01qHM5dVUsBbuGfBDHmMfwgPabGOgDRY-8YcjUrGzE0VUwa-lgyILGbgL5JSV7ZiupaHFA4514sA7sFCQUQxIUObRBF2MxL88v-oS92wbXhJeBBKwG8GPBwTZ0bxVB-e7Qgkeb0GjMdOZ-Z7w19XEtX5JW2RB0UVVF-Y2wADfVhkuXStgqs=w779-h630-no?authuser=0)

-   엔티티에는 4가지 상태가 존재한다.
    1. 비영속(new/transient) : 영속성 컨텍스트와 전혀 관계가 없는 상태
    2. 영속(managed) : 영속성 컨텍스트에 저장된 상태
    3. 준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
    4. 삭제(removed) : 삭제된 상태

**비영속**

-   엔티티 객체를 생성하였고, 순수한 객체 상태이다.
-   영속성 컨텍스트나 데이터베이스와는 전혀 관련이 없다.
    ```java
    // 객체를 생성한 상태
    Member member = new Member();
    member.setId("membeer1");
    member.setUsername("demo");
    ```

**영속**

-   영속성 컨텍스트가 관리하는 엔티티를 영속 상태라고 한다.
-   영속성 컨텍스트에 의해 관리된다는 뜻
-   em.find()나 JPQL을 사용한 조회 / 엔티티 매니저를 통한 저장 했을때 영속 상태가 된다.
    ```java
    em.persist(member);
    ```

**준영속**

-   영속 상태의 엔티티를 영속성 컨텍스트가 관리하지 않으면 준 영속 상태가 된다.
-   준영속 상태로 만드려면 em.detach() 메서드를 호출한다.
-   em.close() 를 호출해 영속성 컨텍스트를 닫거나, em.clear()를 호출해서 영속성 컨텍스트를 초기화해도 준영속 상태가 된다.
    ```java
    em.detach(member);
    ```

**삭제**

-   엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제한다.
    ```java
    em.remove(member);
    ```
