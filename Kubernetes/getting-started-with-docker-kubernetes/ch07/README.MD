# 7 쿠버네티스 리소스의 관리와 설정

## 7.1 네임스페이스(Namespace) : 리소스를 논리적으로 구분하는 장벽

- 네임스페이스는 포드, 레플리카셋, 디플로이먼ㅌ, 서비스 등과 같은 쿠버네티 리소스들이 묶여 있는 하나의 가상 공간 또는 그룹

### 네임스페이스 기본 개념 이해

- kubectl get namespace  명령어로 namespace 목록 확인 가능

    ![namespace](https://lh3.googleusercontent.com/pw/AM-JKLV9qNCRt2vLEmA9J1_D9Dv2LWAcwNIv-XLQCu7N2rB-S8Q3CaGjh3SHZsdALDWY2yX9bP6bZXlgFlz1zX9hsKzoWf5ZsaY3fBz8THuI6GFJ_-fm605FpS1ifHinIyoSPfiw7LaIEp1autq-zZRfbpE_tg=w508-h240-no?authuser=0)

- 특정 네임스페이스에 생성된 리소스를 확인하려면 --namespace 또는 -n 옵션을 사용한다.

    ![namespace](https://lh3.googleusercontent.com/pw/AM-JKLXMsen6O9Bs71sWk-eCujo_gSqTFIzHuYKEPchMCclrzMV6EVoyGL1WFYH4tBPU6qrN2xG6XJNwbhX8hN3maAv55Roa_uaCsiygJvF29qG5sJ9Qm6U-G3lpcFeQssVEe7qobmqW6uHpXHMc0rWv6_Kxig=w1080-h428-no?authuser=0)

- -namespace 속성을 사용하지 않는다면 기본적으로 default namespace를 사용
- 이처럼 네임스페이스는 쿠버네티스의 리소스를 논리적으로 묶을 수 있는 가상 클러스터처럼 사용할 수 있음

### 네임스페이스 사용하기

- 네임스페이스는 아래와 같이 YAML 파일에 정의하거나 kubectl create namespace 명령어로 생성할 수 있다.

```YAML
apiVersion: v1
kind: Namespace
metadata:
  name: production
```

    ![create](https://lh3.googleusercontent.com/pw/AM-JKLW4mA8MbR3pTCJlrDA8Sw9RPGJ7XfMqTFBbnFtGqmG4Le1JRuct2jjTYUhAged9kU5Mgl5JjAAas_0uYyAfFIDBSK0T4vj9i0RbnVxIj173F-jfWVt22slYnfuHr2XFrPEYvX-ahu2nbyoNqhMUVuiGoQ=w582-h394-no?authuser=0)

- 아래는 특정 네임스페이스에 속하는 리소스를 생성하는 예제이다. 단순 metadata.namespace 항목을 아래와 같이 설정하면 된다.

```YAML
apiVersion: apps/v1
kind: Deployment
metadata:
  name: hostname-deployment
  namespace: production
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

--- 

apiVersion: v1
kind: Service
metadata:
  name: hostname-svc-nodeport
  namespace: production
spec:
  selector:
    app: webserver
  ports:
  - port: 8080
    targetPort: 80
    name: web-port
  type: NodePort
```

- production 네임스페이스에 서비스와 디플로이먼트가 생성됨ㄴ

![namespace](https://lh3.googleusercontent.com/pw/AM-JKLWJT0Rrst1nKcxzQ0PhhSKcT47TgTU2DcNR63St60nu0TfI5_p4ujKmwdb52vRsLnppiSKdeDJhjIGqMr8CvJBv8FkGPIE-Yi1v4AB5LmdK7xvhUV80_3j8gPUSlaC4JsTlQIu8xhQEQP20B_RaKDiAYg=w1398-h316-no?authuser=0)

### 네임스페이스 서비스에 접근하기

- <서비스 이름>.<네임스페이스 이름>.svc 와 같은 방식으로 다른 네임스페이스의 서비스에도 접근할 수 있다.

### 네임스페이스에종속되는 쿠버네티스 오브젝트와 독립적인 오브젝트

- 네임스페이스 A 에서 포드를 만들면 A 에서만 보이고, B 에서는 보이지 않는다. 이것을 오브젝트가 네임스페이스에 속한다 라고 표현함
- kubectl api-resources --namespaced=true 명령어로 네임스페이스에 속하는 오브젝트의 종류 확인 가능
- 노드나 네임스페이스는 네임스페이스에 종속되지 않는다.
