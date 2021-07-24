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
- 쿠버네티스 외부 또는 내부에서 포드에 접근하려면 서비스(service)라고 하는 쿠버네티스 오브젝트를 따로 생성해야 한다.
- kubectl logs my-nginx-pod 를 통해 로깅을 할 수 있다.
- 쿠버네티스의 오브젝트는 kubectl delete -f 명령어로 쉽게 삭제할 수 있다.