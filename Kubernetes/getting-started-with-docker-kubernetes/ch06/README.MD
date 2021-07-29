# 6 쿠버네티스 시작하기

## 6.1 쿠버네티스를 시작하기 전에

### 모든 리소스는 오브젝트 형태로 관리됩됩니다.

- 쿠버네티스틑 대부분의 리소스를 '오브젝트'라고 블리는 형태로 관리합니다.
- 컨테이너 집합(Pods), 컨테이너 집합을 관리하는 컨트롤러 (Replica Set), 사용자(Service Account), 노드(Node)까지도 하니의 오브젝트로 사용할 수 있음
- kubeclt api-resources 명령어를 사용해 사용할 수 있는 오브젝트 목록을 볼 수 있음

### 쿠버네티스는 명령어로도 사용할 수 있지만, YAML 파일을 더 많이 사용함

- 쿠버네티스에서는 YAML 파일을 사용해서 컨테이너뿐만 아니라 거의 모든 리소스 오브젝트들에 사용될 수 있음
- 컨테이너 설정값(ConfigMap), 비밀값(Secrets) 등도 모드 YAML로 정의 가능

### 쿠버네티스는 여러개의 컴포넌트로 구성돼 있습니다.

- 쿠버네티스 노드의 역할은 크게 마스터와 워커로 나누어져 있음
- 마스터 노드는 쿠버네티스가 제대로 동작할 수 있게 클러스터를 관리하는 역할을 담당하며, 워커 노드에는 애플리케이션 컨테이너가 생성됩니다.
- 클러스터 구성을 위해 kubelet이라는 에이전트가 모든 노드에서 실행
- kubelet은 컨테이너의 생성, 삭제, 마스터-워커 노드간 통신 역할을 함께 담당

## 6.2 포드(Pod) : 컨테이너를 다루는 기본 단위

### 6.2.1 포드 사용하기

- 컨테이너 애플리케이션의 기본 단위
- 포드는 1개 이상의 컨테이너로 구성된 컨테이너의 집합입니다.
- 1개의 포드에는 1개 이상의 컨테이너가 존재할 수 있음
- 아래는 Nginx 컨테이너로 구성된 포드 예제이다.

    ```YAML
    apiVersion: v1
    kind: Pod
    metadata:
        name: my-nginx-pod
    spec:
        containers:
        - name: my-nginx-container
          image: nginx:latest
          ports:
          - containerPort: 80
            protocol: TCP
    ```

#### apiVersion

- YAML 파일에서 정의한 오브젝트의 API 버전을 나타냄

#### kinkd

- 이 리소스의 종류를 나타냄

#### metadate

- 라벨, 주석(Annotation), 이름 등과 같은 리소스의 부가 정보를 입력함

#### spec

- 리소스를 생성하기 위한 자세한 정보를 입력함
- 컨테이너 정보를 정의하는 containers 항목을 작성한 뒤, 하위 항목인 image에서 사용할 도커 이미지를 지정함, ports 에서는 nginx 컨테이너가 사용할 포트인 80을 입력
- kubectl apply -f 명령어로 쿠버네티스 생성할 수 있음
    ![apply](https://lh3.googleusercontent.com/pw/AM-JKLX3uMicuESbLxOsIc-qGOt90gXFlLDmrs3PpFnYnItluAPZkJOqAxaYUimqujaVtUI7P4I2WNZPF54jCiJGzFiEusckkvpaYy248fC4qzcDv8iZPDqkSiENMtpNv3q8AVssywY9zC_oIS6Qzq4F38ky0g=w390-h61-no?authuser=0)
- kubectl get <오브젝트 이름>을 통해 특정 오브젝트의 목록을 확인할 수 있음
- kubectl describe 명령어를 통ㅌ해 리소스의 자세한 정보를 얻어올 수 있음
    ![describe](https://lh3.googleusercontent.com/pw/AM-JKLWQArej9KMF8beYORph4kxsiAwtD6o5Tw9Ih2AU-kX5B8vL05Wc1J_lDDGNAmInEG0fe3ixUwslqLWcQfgWc5MCxnFDPmJjCCH7BwJXhnKUODGo6EUBsmP6pbqKbivAN51Mzn5rsDRvoJp57XGt_DwCig=w342-h242-no?authuser=0)
- 내부 ip로 접근을 해야만 포드의 Nginx 서버로 요청을 보낼 수 있음
- kubectl exec -it <pod 이름>  bash 명령어로 접근 가능
- 쿠버네티스 외부 또는 내부에서 포드에 접근하려면 서비스(service)라고 하는 쿠버네티스 오브젝트를 따로 생성해야 한다.
- kubectl logs my-nginx-pod 를 통해 로깅을 할 수 있다.
- 쿠버네티스의 오브젝트는 kubectl delete -f 명령어로 쉽게 삭제할 수 있다.

### 6.2.2 포드 vs 도커 컨테이너

- 기능만 놓고 본다면 포드는 docker run 으로 생성한 단일 nginx 컨테이너와 크게 다르지 않아 보이기도 함
- 쿠버네티스가 포드를 사용하는 이유는 여러 리눅스 네임스페이스를 공유하는 컨테이너들을 추상화 된 집합으로 사용하기 위함이다.
- kubectl get pods 명령어로 포드 목록을 출력했을 때, READY 항목에서 1/1라고 표시되어 있다.
- 이는 Nginx 포드에 1개의 컨테이너가 정의돼 있으며, 이 컨테이너는 정상적으로 준비됐다는 뜻
    ![pods](https://lh3.googleusercontent.com/pw/AM-JKLXKreJEUrkOXjAx0GrJ1vK3p36xtagIZ4MJNNajsBMX6lFZwt0oYxwKbIRBtJMRePBInFmUvNemnAky9SREhw87Kw_wFrYGPlnk1WsweSMFSXgT_ltHIjBLC0yTL4ikrGMPpU7ltUHQTdyLrtkN941KUg=w704-h124-no?authuser=0)
- 하지만 포드는 1개의 컨테이너로 구성해야 하는 것은 아님
- READY 항목은 컨테이너 개수에 따라 2/2도 될 수 있고, 3/3도 될 수 있다.
- 아래는 위 포드에 우분투 컨테이너를 추가하는 예제이다.

    ```YAML
    apiVersion: v1
    kind: Pod
    metadata:
        name: my-nginx-pod
        labels:
            name: my-nginx-pod
    spec:
    containers:
    - name: my-nginx-container
        image: nginx:latest
        ports:
        - containerPort: 80
          protocol: TCP
    - name: ubuntu-sidecar-container
        image: alicek106/rr-test:curl
        command: ["tail"]
        
        args: ["-f", "/dev/null"]
    ```

- 해당 포드에 우분투 컨테이너를 추가 했음
    ![container2](https://lh3.googleusercontent.com/pw/AM-JKLXI8PsECYXHRM8FHneDN2YPO9qpcAnTgCCI0uRw12PSqV3FrbjyuajO7vXYP9M9pAU_aIdD9t_iXS7B0CqRKd-POc5w4hQ-FADT4brIiJdeBFqiMa_TCiNPDMlhjOtftSyLiJVDVyXycIjYel-p9wha-g=w722-h124-no?authuser=0)

- 그리고 exec 명령어를 통해 새롭게 추가된 우분투 컨테이너 내부로 들어가 로컬호스트로 http 요청을 전송하면 nginx 서버의 응답이 도착하는것을 확인할 수 있댜.
    ![localhost](https://lh3.googleusercontent.com/pw/AM-JKLX_B41chPeeBXnzC-i4UQJ6yKgRdBbEpFjOBYgB_AjQ6OgjMoZACugi4TXD2Gt2lhAb8-3nnHh0OQaWRt1HB9CnGJzXgwbV8KF55LIF46RnJ3NyMZwWAH-vHOj1LxbLWG6Y8zRMB5JOeStljKbMhlyE6w=w958-h316-no?authuser=0)
- 이는 포드 내의 컨테이너들이 네트워크 네임스페이스 등과 같은 리눅스 네임스페이스를 공유해 사용하기 때문

### 6.2.3 완전한 애플리케이션으로서의 포드

- '하나의 포드는 하나의 완전한 애플리케이션' 이라는 점이 중요
- 위 예제에서는 nginx 컨테이너는 그 자체만으로도 완전한 애플리케이션
- 그러나 컨테이너가 실행되기 위해 부가적인 기능이 필요하다면 '사이드카(sidecar)' 컨테이너를 포드에 포함 시킬 수 있음

## 6.3 레플리카셋(Replica Set) : 일정 개수의 포드를 유지하는 컨트롤러

### 6.3.1 레플리카셋을 사용하는 이유

- YMAL파일에 포드만 정의해 생성하게 된다면 이 포드의 생애 주기는 어떻게 될까?
- kubectl delete로 삭제된 포드는 포드의 컨테이너 또한 삭제된 뒤 쿠버네티스에서 영원히 사라지게 됨
- 쿠버네티스 사용자에 의해서만 라이프사이클이 관리됨.
- 실제로 외부 사용자의 요청을 처리해야 하는 마이크로 서비스의 구조라면 이러한 방식을 사용하기 어려움
- 만약 삭제되거나 장애가 발생해 더 이상 포드에 접근하지 못하게 됐을 때, 직접 포드를 삭제하고 다시 생성하지 않는 한 해당 포드는 다시 복구되지 않음
- 이러한 한계점을 해결하기 위해 레플리카셋이라는 오브젝트를 함께 사용
- 레플리카셋이 수행하는 역할
    - 정해진 수의 동일한 포드가 항상 실행되도록 관리
    - 노드 장애 등의 이유로 포드를 사용할 수 없다면 다른 노드에서 포드를 다시 생성

### 6.3.2 레플리카셋 사용하기

- 아래는 nginx 포드를 생성하는 레플리카셋 예제이다.

    ```YAML
    apiVersion: apps/v1
    kind: ReplicaSet
    metadata:
        name:  replicast-nginx
    spec:
        replicas: 3
        selector:
            matchLabels:
                app: my-nginx-pods-label
        template:
            metadata:
                name:  my-nginx-pod
                labels:
                    app: my-nginx-pods-label
            spec:
                containers:
                    - image:  nginx:latest
                      name:  nginx
                      ports:
                      - containerPort: 80
    ```

    ![ReplicaSet](https://lh3.googleusercontent.com/pw/AM-JKLVsC_5gJBeI65CK8l0Wy6_LJxocfl_k-qxR-FjHPdvSetsat4B_D2erfd3gbBdf-_wQnx3w0ZFbibWiNq2Jo1aUtJEfGB_ThiX7rd_wRuW3ny8TcYDlfosIW9QFE7F2_kCGwuzghEnf7WZN-9FRpKYhxw=w894-h204-no?authuser=0)

- spec.replicas : 동일한 포드를 몇개 유지시킬 것인지 설정
- spec.template 아래의 내용들 : 포드를 생성할 때 사용할 템플릿을 정의

### 6.3.3 레플리카셋의 동작 원리
- 레플리카셋은 포드와 연결돼 있지 않음
- 라벨 셀렉터를 이용해 느슨한 연결을 포드와 사용

    ```YAML
    apiVersion: apps/v1
    kind: ReplicaSet
    metadata:
        name:  replicast-nginx
    spec:
        replicas: 3
        selector:
            matchLabels:
                app: my-nginx-pods-label # 해당 주석 위부터 레플리카셋 정의
        template:  # 해당 주석 아래 부터 포드 정의
            metadata:
                name:  my-nginx-pod
                labels:
                    app: my-nginx-pods-label
            spec:
                containers:
                    - image:  nginx:latest
                      name:  nginx
                      ports:
                      - containerPort: 80
    ```

- 포드를 생서할 때 부가 정보를 설정할 수 있는데, 부가 정보중에는 랍ㄹ, 주석 등도 포함
- 특히 라벨은 포드 등의 쿠버네티스 리소스를 분류할 때 유용하게 사용할 수 있는 메타데이터
- 라벨은 부가적인 정보를 표현할 뿐만 아니라, 서로 다른 오브젝트가 서로를 찾아야 할 때 사용되기도 함
- 레플리카셋은 spec.selector.matchLabels에 정의된 라벨을 통해생성해야 하는 포드를 찾음
- 즉 app: my-nginx-pods-label을 가진 포드의 개수가 spec.replicas 에 정의된 숫자만큼 생성
- 생성된 포드를 강제로 삭제하면 새로운 포드를 생성함

    ![remove-pod](https://lh3.googleusercontent.com/pw/AM-JKLV_74qJIioOIcrYvPDT6r0qLTFKhinzV4kIsuukEY4wCh6bz1lOqFAZszJwJnAoQ9DYsXJcSgFu0bwLRFH2K0GusiIvDFpfHwrt72CxqQa9dXdJmNXbcDdTlV6qYchSEWquWehZq0cOa_2YdSpEZ45U0g=w890-h542-no?authuser=0)

- kubectl edit 명령어를 통해 pod에 있는 라벨을 삭제한다면 관리 대상으로 간주하던 파드가 하나 사라졌기 때문에 새로운 pod를 생성함
    ![edit](https://lh3.googleusercontent.com/pw/AM-JKLWwBKCIjsCJF6Xt0q8UHeBf3c4y_X5V6LBOG1W1HbCtfypMZXMS4r7neWL_bfVuhIRGZ9tmizT5SJO9-cnVFh51Z82PNI1vmIuR9s2-7mhiffZSuWTYKGgmDDGZRn4KSoVIVI3CV-5yLKrUh-bL4EvwAA=w1272-h728-no?authuser=0)
- 레플리카셋의 목적은 '포드를 생성하는 것' 이 아닌 '일정 개수의 포드를 유지하는 것'

### 6.3.4 레플리케이션 컨트롤러 vs 레플리카셋
- 이전 버전의 쿠버네티스에서는 레플리카셋이 아닌 레플리케이션 컨트롤러를 사용해서 포드의 개수를 유지함
- 다른점은 레플리케이션 컨트롤러는 표현식 기반의 라벨 셀렉터를 사용한다는 점
- 현재 버전에서는 [사용을 권장하지 않음](https://kubernetes.io/ko/docs/concepts/workloads/controllers/replicationcontroller/)

## 6.4 디플로이먼트(Deployment) : 레플리카셋, 포드의 배포를 관리

### 6.4.1 디플로이먼트 사용하기

- 운영환경에서는 레플리카셋보다는 레플리카셋과 포드의 정보를 정의하는 **디플로이먼트(Deployment)**라는 이름의 오브젝트를 YAML 파일에 정의해 사용
- 디플로이먼트는 레플리카셋의 상위 오브젝트 
- 디플로이먼트를 생성하면 해당 디플로이먼트에 대응하는 레플리카셋도 함께 생성됨
- 아래 예시는 디플로이먼트를 사용하는 예시이다.

```YAML
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-nginx-deployment
spec:
  replicas: 3
  selector:
    matchLabels:
      app: my-nginx
  template:
    metadata:
      name: my-nginx-pod
      labels:
        app: my-nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.10
        ports:
        - containerPort: 80

```

    ![dep](https://lh3.googleusercontent.com/pw/AM-JKLWOLWnq5cf3ezikoifhhP128DTaB3BoR67Jl_J2mV5hVQAjRfBAUp68YzhBOniFqXhtvdlwdIOP9jSmyQqWxBU9RqGV2sePcenfYIwBGRuqiJqHYNm3Zi_j4wDKfRva8uEUoSWLWxi_EgXeFSDqYCfmkw=w1272-h728-no?authuser=0)

- 실제로 레플리카셋 또한 생성된 것을 보 수 있다.

### 6.4.2 디플로이먼트를 사용하는 이유

- 핵심적인 이유 중 하나는 애플리케이션의 업데이트와 배포를 더욱 편하게 만들기 위함
- 예를 들어 애플리케이션을 업데이트 할 때 레플리카셋의 변경 사항을 저장하는 리비전을 남겨 롤백을 가능하게해주고, 무중단 서비스를 위해 포드의 롤링 업데이트의전략을 지정할 수도있음
- 아래는 레플리카셋 리비전 정보를 저장하는 예제이다.
- --record 옵션을 통해 리비전을 기록할 수 있다.
- 우선  Deployment를 생성하고 --record 옵션을 통해 리비전을 기록

    ![step1](https://lh3.googleusercontent.com/pw/AM-JKLV149KC7GsssprnLinIs29zE419psQILnrx1V1BFtvnr9M5Tru1ZnQdD6XuSA0cfpfbWQxL-UdglUwWMBwwtcarTrqLCJAV0GjuU1Ze_UExExE-_1zlj5itqA8karX7aNyoTBo7QLFioZMxZfsODcNhSA=w1030-h458-no?authuser=0)

- 그리고 set image 명령어를 통해 이미지를 변경

    ![step2](https://lh3.googleusercontent.com/pw/AM-JKLW6t7A5yo1bWEbVVhPV7xuaLc8OPfgq0uIKDmr75h_w77PuOsYWMLj90tZeS_TYT4RfPXvblVukwuAsXMXZJ3Xl6G0sNXq9ENTtUj6bgxIwnLFbpP8ejTjQBm-WABa5zozKRV09iwBI_96_YdHg7dT_ZQ=w1078-h94-no?authuser=0)

- 다시 포드 목록을 보면 이미지를 업데이트 했으므로 새롭게 생성된 포드 목록을 볼 수 있음

    ![step3](https://lh3.googleusercontent.com/pw/AM-JKLV5GrHq_pyJix3gipsVrjQRF-bX5EtkCkzKtkGTl3edxM1HtmrYFxmk9uOE82PZG_vRjp-HEpFiA2aQuAopzIsq_z7dEzTSshBcurd1hcL4I-Q0Y6rRs88ZpY0tkzZqV8ADUJ9hjwm4HYPZ1Jd94wXJAQ=w1058-h214-no?authuser=0)

- 그 뒤에 레플리카셋의 목록을 출력해보면 두개의 레플리카셋을 볼 수있음

    ![step4](https://lh3.googleusercontent.com/pw/AM-JKLU7GAkICzhNpS5BLAxbbIMQ3iNgfJmHGcQyCTh9Rf7FqFc8Rz9H8DntU8Pf6SZ08HPwV0SrHXIBjd2PB3cRoLY1UvyHYzj0x4nodrbMBhxzZwKX1B9U-cJYPOFKJv7P1qp5f4VxhU2cy9FrqO0kPGG7GA=w958-h158-no?authuser=0)

- 해시값을 보면 금방 생성한 레플리카셋과 이전에 생성해둔 레플리카셋임을 알 수 있음
- 디플로이먼트의 포드 정보를 업데이트함으로써 새로운 레플리카셋과 포드를 생성했어도, 이전 버전의 레플리카셋을 삭제하지 않고 남겨 둠
- 리비전 정보를 확인해보면 아래와 같음

    ![step5](https://lh3.googleusercontent.com/pw/AM-JKLWpLLS1ekrfPRbYEQMlC7NakJ8logZMblkGCiB781mjmZcjhO_Hb780hdjqHeFl2bXiHOULtSg5dtv5litv_HQkSd_gOFV92pPuROlWAw59kx0PoAUugnf4LqGBrxrZIZqwwzL3Hg1SUDnhYZbog1YJRg=w1300-h206-no?authuser=0)

- --record=true 옵션으로 디플로이먼트를 변경하면 이전 사항을 위아 같이 디플로이먼트에 기록함으로써 레플리카셋을 보존할 수 있음
- 만약 이전 버전의 레플리카셋으로 롤백을 하고 싶다면  --to-revision={REVISION NUMBER} 를 통해 되돌리기가 가능

    ![step6](https://lh3.googleusercontent.com/pw/AM-JKLXIRQP_Phj6AX4eptx3mjOZAN3Jo04S7YliTsDfJp2QMrrhFQlq3wrWraPglkE2OE7zGak7bCm0DUXoSY5TKOsyG0YuY2gh0TEcdSmEE752btD7ReuzlK6njkjvpZDfTfwUDfxo-dXxGhtkcDv8APe5vw=w1044-h274-no?authuser=0)

- 이처럼 디플로이먼트는 여러 개의 레플리카셋을 관리하기 위한 상위 오브젝트임

## 6.5 서비스(Service) : 포드를 연결하고 외부에 노출

- 디플로이먼트를 통해 생성된 포드에는 어떻게 접근할 수 있는 방법은?
- 포드의 ip는 영속적이지 않아 항상 변할 수 있다는 점도 유의해야 함
- 여러개의 디플로이먼트를 하나의 완벽한 애플리케이션으로 연동하려면 포드 IP가 아닌, 서로를 발견(Discovery)할 수 있는 다른 방법이 필요
- 디플로이먼트의 YAML 파일에는 단지 포드의 애플리케이션이 사용할 내부 포트만 정의함
- 해당 포트를 외부로 노출해 사용자들이 접근하거나, 다른 디플로이먼트의 포드들이 내부적으로 접근하려면 서비스(service)라고 부르는 별도의 쿠버네티스 오브젝트를 생성해야 함
- 서비스의 핵심 기능
  - 여러 개의 포드에 쉽게 접근할 수 있도록 고유한 도메인 이름을 부여
  - 여러 개의 포드에 접근할 때, 요청을 분산하는 로드 밸런서 기능을 수행
  - 클라우드 플랫폼의 로드 밸런서, 클러스터 노드의 포트 등을 통해 포드를 외부로 노출

### 6.5.1 서비스의 종류

- 아래는 서비스 예제를 활용할때 필요한 디플로이먼트 예제이다.

```YAML
apiVersion: apps/v1
kind: Deployment
metadata:
  name: hostname-deployment
spec:
  replicas: 3
  selector:
    matchLabels:
      app: webserver
  template:
    metadata:
      labels:
        app: webserver
    spec:
      containers:
      - name: my-webserver
        image: alicek106/rr-test:echo-hostname
        ports:
        - containerPort: 80

```

- 서비스는 포드에 어떻게 접근할 것이냐에 따라 종류가 여러개로 나눠짐
- 대표적 서비스 타입 
  - ClusterIP 타입 : 쿠버네티스 내부에서만 포드들에 접근할 때 사용함, 외부로 포드를 노출하지 않기 때문에 쿠버네티스 클러스터 내부에서만 사용되는 포드에 적합
  - NodePort 타입 : 포드에 접근할 수 있는 포트를 클러스터의 모든 노드에 동일하게 개방함. 따라서 외부에서 포드에 접근할 수 있는 서비스 타입
  - LoadBalancer 타입 : 클라우드 플랫폼에서 제공하는 로드 밸런서를 동적으로 프로비저닝해 포드에 연결, 클라우드 플랫폼 환경에서만 사용 가능

### 6.5.2 ClusterIP 타입의 서비스 - 쿠버네티스 내부에서만 포드에 접근하기

- 아래 내용은 간ㄷ나한 CLusterIP 타입 예제이다.

```YAML
apiVersion: v1
kind: Service
metadata:
  name: hostname-svc-clusterip
spec:
  selector:
    app: webserver
  ports:
  - port: 8080
    targetPort: 80
    name: web-port
  type: ClusterIP
```

- spec.selector : selector 항목은 이 서비스에서 어떠한 라벨을 가지는 포드에 접근할 수 있게 만들 것인지 결정
- spec.ports.port : 생성된 서비스는 쿠버네티스 내부에서만 사용할 수 있는 고유한 ip를 할당받음. port 항목에는 서비스의 IP에 접근할 때 사용할 포트를 설정
- spec.ports.targetPort : selector 항목에서 정의한 라벨에 의해 접근 대상이 될 포드들이 내부적으로 사용하고 있는 포드를 입력. 즉 포드 템플릿에 정의된 containerPort와 같은 값으로 설정해야 함
- spec.type : 서비스가 어떤 타입인지 나타냄
- kubectl apply 명령어를 통해 서비스를 생성할 수 있음
    ![svc](https://lh3.googleusercontent.com/pw/AM-JKLVkrDaEjKK9zvTZ_ng2Ih4g-PQXdxhLIoMrnbop0em0OpsVnun1XnRIkYZtwlDwyQkBMWVBkj-qbRN1w0P5FqPZvpXJoQTbN2Mk9GB0u6Ys1sazQJ39PDPmUCVrmBc0aizzTBRU2-D-jZiwGXgkrBQavQ=w1208-h240-no?authuser=0)

- 서비스를 사용해 포드에 접근하는 방법은 위 출력 내용중 CLUSTER-IP 항목의 IP와 PORT 항목의 포트를 통해 요청을 보내면 된다.
- 임시 포드를 만들어 요청을 전송하면 아래와 같은 결과를 볼 수 있다.
    ![pod](https://lh3.googleusercontent.com/pw/AM-JKLUiDyXxeJCytJO_WkXBXbd039Z1cV41VBUHKV2HjC9gmK8vnd7SYk2OXQoxjqZXLv1ja-v2qdq8wWnhtl8aHbiOWwQPfvtXD6V1mODXksVy-h6Pax3d1kyhcts7xl8SbxSl_P79KoObAs-9MZvpxK8fEA=w1248-h616-no?authuser=0)

- 서비스의 IP:PORT 를 통해 포드에 접근이 가능하다.
- 또한 자동을 요청을 분산하고 있음.
- 서비스를 생성할 때 별도의 설정을 하지 않아도 서비스는 연결된 포드에 대해 로드 밸런싱을 수행한다.
- 서비스는 IP 뿐만 아니라 서비스 이름 그 자체로도 접근할 수 있음
- 애플리케이션이 서비스나 포드를 쉽게 찾을 수 있도록 내부 DNS를 구동하고 있음
    ![dns](https://lh3.googleusercontent.com/pw/AM-JKLUBb_Qg4kxo39wfXWPszaXxODL0vJn15c7gWCcKO7Ulemtoo7RHd42_9lZuH8I4gw1sWM6dnrqsjofOY578k84EE4SdFPcLXr8KaDpGVvAyFUv_yInEirCyP0J2QPxbwA-2APe6oLSCP3Zkdfgchmqakg=w1112-h78-no?authuser=0)
- 서비스의 이름과 같은 도메인 이름을 사요하는 것이 일반적임
- 정리하면 아래와 같다.
  1. 특정 라벨을 가지는 포드를 서비스와 연결하기 위해 서비스의 YAML 파일에 selector 항목을 정의
  2. 포드에 접근할 때 사용하는 포트(포드에 설정된 containerPort)를 YAML 팡리의 targetPort 항목에 정의
  3. 서비스를 생성할 때, YAML 파일의 port 항목에 8080을 명시해 서비스의 Cluster IP와 8080 포트로 접근할 수 있게 설정 한다.
  4. kubectl apply -f 명령어로 ClusterIP  타입의 서비스가 생성되면 서비스는 쿠버네티스 클러스터 내부에서만 사용할 수 있는 고유한 내부 IP를 할당받음
  5. 쿠버네티티스 클러스터에서 서비스의 내부 IP 또는 서비스 이름으로 포드에 접근할 수 있다.

    ![정리](https://lh3.googleusercontent.com/pw/AM-JKLUcKFV0ECsyYKTDdbfxzMnYGHSSWa-ZMKkpyXLS-UAiZFuOl0BsO2GbxXiMJnyPOVfU2JPLNDryHCGmZcs5_XoVmdjUSWMx-AWMfu1uywk3yjq31yW-zy5cEiHkmwCgPy5V58x1Jaqc9Vb16qnKRktwsA=w1920-h1080-no?authuser=0)

### 6.5.3 NodePort 타입의 서비스 - 서비스를 이용해 포드를 외부에 노출하기

- NodePort 타입의 서비스는 클러스터 외부에서도 접근할 수 있음
- 모든 노드의 특정 포트를 개방해 서비스에 접근하는 방식
- 아래는 NodePort 타입의 서비스 예제이다.

```YAML
apiVersion: v1
kind: Service
metadata:
  name: hostname-svc-nodeport
spec:
  selector:
    app: webserver
  ports:
  - port: 8080
    targetPort: 80
    name: web-port
  type: NodePort
```

- type 항목을 변경한것 빼고는 모두 동일

    ![svc](https://lh3.googleusercontent.com/pw/AM-JKLU6msGE9u6kOT9EuEAZF1X64vXbZRSTaoRuS_AxCqtGQ2HSrJmVPmAOQ4Vr5M5dh1aFcB36t_02PcBUcy4IlucauHiA2bQ_hPUhz455dKgAFqJ1xSrYXqzKRR2FnM0E6IH5lCm5GQfkQTJl1GtiCjQPyA=w1250-h236-no?authuser=0)

- PORT(S) 항목에 출력된 숫자는 모든 노드에서 동일하게 접근할 수 있는 포트를 의미함
- NodePort 타입의 서비스는 CLusterIP의 기능을 포함하고 있음
- 실제 NodePort 를 통해 서비스를 외부에 제공하는 경우는 많지 않음 (Ingress 활용)

### 6.5.4 클라우드 플랫폼의 로드 밸런서와 연동하기 - LoadBalancer 타입의 서비스

- 클라우드 플랫폼에서 사용하는 기능 -> TODO로 남김

### 6.5.5 트래픽의 분배를 결정하는 서비스 속성 : externalTrafficPolicy

- LoadBalancer 타입과 NodePort 타입을 사용했을 때 각 노드로 들어오는 요청은 다시 포드 중 하나로 전달 됨
- 하지만 이러한 요청 방법은 경우에 따라 효율적이지 않을 때도 있음
- 아래 그림을 예로들어보면

    ![externalTrafficPolicy](https://lh3.googleusercontent.com/pw/AM-JKLUvTnceu0lTnOa5bwPavZ1eWbkjgZn8ZCLp1dFWblexgaO3CA-4zCztWHdpsDosL-yeov2X_tX5cLVulTGY1ZigxjSbOtTK-L-YS0rGh7X80_0e8B4pT9dyF9WRcI2P8DKOp9iYUtE3BOClnXCS9eTtSQ=w2336-h1226-no?authuser=0)

- 워커노드 A로 들어오는 요청은 (1) A에 위치한 a 포드 또는 (2) B에 위치한 b 포드중 하나로 전달 된다.
- 다만 A 노드로 들어오는 요청이 굳이 B 노드로 전달되지 않고 A 노드 내부(a 포드)에서 처리될 수 있다는 점
- 부맆룡한 네트워크 홉(hob)이 한 단계 발생할 수 있고, 노드간 리다이렉트로 인한 트래픽 출발지 주소가 바뀌는 SNAT가 발생할 수 있음ㄴㄴㄴ
- 이러한 요청 전달 메커니즘은 서비스의 속성 중 externalTrafficPolicy 항목에 정의돼 있음

    ![항목](https://lh3.googleusercontent.com/pw/AM-JKLUtVU9EhMGdFFSn_We0sdqcuUb7Bk6uNtU8o9TIDsbwGYvHnR9rlMITkQEOUPlvG4a2mT9OzGsGTA-eOtzPwACdbKLmYDng2gYcxGBre2ECB1ZgtErE3hy2e-fXe6eVcu4iRW1oV3VxL7h9Z8wKyR2SZg=w680-h694-no?authuser=0)

- 기본 값은 Cluster임
- externalTrafficPolicy 값을 Local로 설정하면 포드가 생성된 노드에서만 포드로 접근할 수 있다.
- 다만 externalTrafficPolicy 값을 Local로 설정할 경우 각 노드에 포드가 고르지 않게 스캐줄링 됐을 때, 요청이 고르게 분산되지 않을 수 있다.

### 6.5.6 요청을 외부 리다이렉트하는 서비스 : ExternalName

- NodePort, ClusterIP, LoadBalancer 타입말고, 쿠버네티스를 외부 시스템과 연동해야 할 때는 ExternalName 타입의 서비스를 사용할 수도 있음
- 아래는 지정한 CNAME을 반환하는 예제이다.

```YAML
apiVersion: v1
kind: Service
metadata:
  name: externalname-svc
spec:
  type: ExternalName
  externalName: my.database.com

```

- 쿠버네티스와 별개로 존재하는 레거시 시스템에 연동해야 하는 상황에서 유용하게 사용할 수 있음