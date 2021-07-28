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

