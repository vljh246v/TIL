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

- 아래는 생성된 네임스페이스 목록이다.

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

## 7.2 컨피그맵(Configmap) 시크릿(Secret) : 설정값을 포드에 전달

- 설정값을 도커 이미지 내부에 정의할 수도 있고, 포드를 정의하는 YAML에 직접 적어 놓을 수도있다.
- 하지만 상황에 따라서는 환경 변수의 값만 다른, 동일한 여러 개의 YAML이 존재할 수도 있음. 예를 들면 운영/개발 환경에 따른 변수가 다를 수 있음
- 이러한 설정 분리를 쿠버네티스는 컨피그맵과 시크릿이라는 오브젝틀르 통해 할 수 있음
  ![configmap](https://lh3.googleusercontent.com/pw/AM-JKLWsXhZUCPiGsgK0BLCo_tQlHP28gOhOwwcTOFSBqTk5me0wSlg8NoyhPFj-h603FpL6hqofyjwh5aciJJaVU-9NFTU9weCFaE15r5rTyTL_-SLqz-34qu6iBmPz-QNagZfnrV8Fj9gE_rYy-Vvle9q0vw=w2106-h952-no?authuser=0)
- 컨피그맵을 활용하면 1개의 포드 YAML 파일만을 사용하되 환경에 따라 다른 컨피그맵을 생성해 사용하면 됨

### 7.2.1 컨피그맵(Confimap)

#### 컨피그맵 사용 방법 익히기

- 일반적으로 설정값을 담아 저장할 수 있는 쿠버네티스 오브젝트
- kubectl create configmap ㅁ여령어를 사용하면 수비게 컨피그맵 생성 가능
- --from-literal 옵션을 여러번 사용해 여러 개의 키-값을 컨피그맵에서 사용하도록 설정 가능
  ![create](https://lh3.googleusercontent.com/pw/AM-JKLWlmuqZ47wHi6BkU6jsFge-8xMmhV6vhNVVH41XCAcqC9rBbhIKIPAyxRP2IdkNsOyAuvCUSpcXBL2Z3dxLqFb_CAw5azIGc1SVZ8ONZ1M5eQ9UkXr8YNIJYt6Uj1wLXuIAXuzPBld0qZkPdnDNUVpr6Q=w1124-h268-no?authuser=0)
- 저장된 설정값은 kubectl describe configmap 명령어 또는 kubectl get configmap -o yaml 명령어로 확인 가능
  ![describe](https://lh3.googleusercontent.com/pw/AM-JKLXANJ-_KWuZs80i4e4XSgwHXLWwRKA4ZPuT8Ycrko5aCrJW6J3WqcqXQ4untusklsoT_F9a2FsegppGC1KB00CBQ9tjKxAkdRUEZxEW4elTTPfUUwEYgZmE-8_e5B8Wr0RD8eSBcUHx4Nhc2EoUX9U1mQ=w748-h952-no?authuser=0)
- 생성된 컨피그맵을 포드에서 사용하려면 디플로이먼트 등의 YAML 파일에서 포드 템플릿 항목에 컨피그맵을 사용하도록 설정하면 됨
- 컨피그맵을 포드에서 사용하는 방법은 크게 두 가지가 있음

#### 컨피그맵의 값을 컨테이너 환경 변수로 사용

- 컨피그맵의 값을 포드의 컨테이너 환경 변수로 가져옴
- 애플리케이션이 시스템 환경 변수로부터 설정값을 가져온다면 이 방법이 좋음

#### 컨피그맵의 값을 포드 내부의 파일로 망누트해 사용

- 컨피그맵의 값을 포드 컨테이너 내부의 특정 파일로 마운트 함
- 애플리케이션이 nginx.conf 등의 파일을 통해 설정값을 읽어 들인다면 이 방법을 사용하는 것이 좋음

#### 컨피그맵의 데이터를 컨테이너의 환경 변수로 가져오기

- 아래는 컨피그맵의 값을 환경 변수로 사용하는 포드를 생성하는 예제이다.

```YAML
apiVersion: v1
kind: Pod
metadata:
  name: container-env-example
spec:
  containers:
  - name: my-container
    image: busybox
    args: ['tail', '-f', '/dev/null']
    envFrom:
      - configMapRef:
          name: log-level-configmap # 키-값 쌍이 1개 존재하는 컨피그맵
      - configMapRef:
          name: start-k8s # 키-값 쌍이 2개 존재하는 컨피그맵

```

- 위 예제중 envFrom 항목은 하나의 컨피그맵에 여러 개의 키-값 쌍이 존재하더라도모두 환경 변수로 가져오도록 설정
  ![envFrom](https://lh3.googleusercontent.com/pw/AM-JKLXdYbCzBb8wXaEK4tx5B1fwKzBnCQPDxWtb9BxeFWAidFc1K2kM2k1ZprSsX67vmGJ6-tf2jtXK45ABHA-F8cGlv_IBdt_Xwl78mWaGow1X_PrCdR8rVRZ6Q9fVeRWjzUi9jvsQIR_FCnl3HiW5tJKeJg=w974-h438-no?authuser=0)

- 이전에 만들어둔 3개의 환경 변수를 확인할 수 있다.
- valueFrom 과 configMapKeyRef 를 활용하면 여러개의 키-값 상이 들어 있는 컨피그맵에서 특정 데이터만 선택해 환경 변수로 가지고 올 수 있음

```YAML
apiVersion: v1
kind: Pod
metadata:
  name: container-selective-env-example
spec:
  containers:
  - name: my-container
    image: busybox
    args: ['tail', '-f', '/dev/null']
    env:
      - name: ENV_KEYNAME_1 # 컨테이너에 새롭게 등록될 환경 변수 이름
        valueFrom:
          configMapKeyRef:
            key: LOG_LEVEL
            name: log-level-configmap
      - name: ENV_KEYNAME_2 # 컨테이너에 새롭게 등록될 환경 변수 이름
        valueFrom:
          configMapKeyRef:
            key: k8s # 가져올 데이터 값의키
            name: start-k8s # 참조할 컨피그맵의 이름

```

  ![valueFrom](https://lh3.googleusercontent.com/pw/AM-JKLXRvQPc7CfG-7BjhS8lx9py8IXAIsX6fpulcZr6Xj69A8rmTOHhfpJliw0aq_W1BnEF3hZR9yfMFrh7aVYX5uTIIswg_xYwuu4BvF5U6XzLKydlsBhiOIXBim3KJICMpG3ptTOn9I9OdWfJllMq02x_NA=w746-h300-no?authuser=0)

- **envFrom** : 컨피그맵에 존재하는 모든 키-값 쌍을 가져옴
- **valueFrom 과 configMapKeyRef** : 컨피그맵에 존재하는 키-값 쌍 중에서 원하는 데이터만 선택적으로 가져옴

#### 컨피그맵의 내용을 파일로 포드 내부에 마운트 하기

- 아래 예제는 start-k8s 컨피그맵에 존재하는 모든 키-값 쌍을 /etc/config 디렉터리에 위치시킴

```YAML
apiVersion: v1
kind: Pod
metadata:
  name: configmap-volume-pod
spec:
  containers:
  - name: my-container
    image: busybox
    args: ['tail', '-f', '/dev/null']
    volumeMounts:
      - mountPath: /etc/config
        name: configmap-volume
  volumes:
    - name: configmap-volume
      configMap:
        name: start-k8s
```

- **spec.volumes**
  - yaml 파일에서 사용할 볼륨의 목록을 정의 합니다.
  - start-k8s라는 이름의 컨피그맵을 통해 configmap-volume 볼륨을 정의했습니다.
  - volumes 항목에서 정의한 볼륨은 spec.containers 항목에서 참조해 사용하고 있습니다.
- **spec.containers.volumeMounts**
  - volumes 항목에서 정의된 볼륨을 컨테이너 내부의 어떤 디렉터링 ㅔ마운트할 것인지 명시함
  - 위 예시에서는 /etc/config 디렉터리에 컨피그맵의 값이 담긴 파일이 마운트 됨

  ![volumes](https://lh3.googleusercontent.com/pw/AM-JKLX9AD_TEYK7u0a0Bvr8vttUh-w8kCn7NxbqdfSW2uGUjThfJSNX3c4c7DLSlq75edJt0Eo2R-8tiuhrDoE3Stg6kDcL4uUE82m_fjEn71q9RdzVHM0u7_LiUqeIzDlUPzOQnEp69BgWgwTmuEpf2sVa3A=w1822-h436-no?authuser=0)

- 컨피그맵에 저장돼 있던 두 개의 키-쌍 데이터의 키 이름이 파이롤 존재하고 있음
- 파일 이름은 키의 이름과 같고, 컨피그맵의 모든 키-값 쌍 데이터가 마운트

  ![키-값](https://lh3.googleusercontent.com/pw/AM-JKLUzUaLegdvuy_4b0cHFFYpKTAYJkpO5WZ1XB_Wbc0E3s9kgf4AtmanatEj7YTGcljzpYg-PLdTduv78KGVp-U1DL9HavCihg0GOca7wxqc2QhIXI-jRA8YISB5DHst2B2186kYKqBI4ZzBKyLSfA732dw=w2070-h282-no?authuser=0)

- 원하는 키-쌍 데이터만 선택해서 포드에 파일로 가져올 수 있음

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: selective-cm-volume-pod
spec:
  containers:
  - name: my-container
    image: busybox
    args: ['tail', '-f', '/dev/null']
    volumeMounts:
      - mountPath: /etc/config    # 마운트 위치는 변경되지 않음
        name: configmap-volume
  volumes:
    - name: configmap-volume
      configMap:
        name: start-k8s
        items:                    # 컨피그맵에서 가졍로 키-값의 목록을 나열
          - key: k8s              # k8x라는 키에 대응하는 값을 가져옴
            path: k8s_fullname    # 최종 파일 이름은  k8s_fullname
```

- **items 항목** : 컨피그맵에서 가져올 키-값의 목록을 의미하며, k8s 라는 키만 가져오도록 명싱
- **path 항목** : 최종적으로 디렉터리에 위치할 파일의 이름을 입력하는 항목으로, k8s_fullname이라는 값을 입력
- 따라서 k8s 라는 키에 해당하는 값이 k8s_fullname이라는 파일로 포드 내부에 존재

  ![items](https://lh3.googleusercontent.com/pw/AM-JKLWGkzfR7wv54ATImUcMOHdkw7JYH8RpXvxIUkH_2N4SOHOm36MX4720CaFYonRwIC1Npi6zziQmXJwOpStamreA9n6q3pHpEb_yGmUnK4LYRVlkRtgwodd8xFT-08hLth1TYdAI1Ic3pE2JzukAspZLgw=w1832-h402-no?authuser=0)

#### 파일로부터 컨피그맵 생성하기

- 컨피그맵을 볼륨으로 포드에 제공하 때는 대부분 설정 파일 그 자체를 컨피그맵으로 사용하는 경우가 많음
- 예를 들어 nginx.confg 또는 nysql.conf의 내용을 아예 통째로 ㅋ너피그맵에 저장한 뒤 이름 볼륨 파일로 포드 내부에 제공하면 더 효율적인 서정 관리가 가능하다.
- 단순 문자열 값을 이용해 컨피그맵을 생성할 때는 --from-literal 옵션을 사용했지만, 파일로 부터 컨피그맵을 생성하려면 --from-file 옵션을 사용
- kubectl create configmap <컨피그맵 이름> --from-file <파일이름>

  ![configmap](https://lh3.googleusercontent.com/pw/AM-JKLUNKw2CjqTI09Z0CYG1Cz9yb_zXqzFoPhYcphufuznCltEM76Afrz7sZSv335k7UYgL1APz5OgMJoYseNA7TLkDSw_AlETRKv6iROZYbD9D291VVTY8PBFbdrcOQA8SSBVDsWrhH6eCNgK1ZwXeDw8_Dg=w896-h700-no?authuser=0)

- --from-file  옵션에서 별도의 키를 지정하지 않으면 파일 이름이 키로, 파일 내용이 값으로 저장됨
- 또는 --from-env-file 옵션으로 여러 개의 키-값의 형태의 내용으로 구성된 설정 파일을 한꺼번에 컨피그맵으로 가져올 수도 있습니다.

  ![from-env-file](https://lh3.googleusercontent.com/pw/AM-JKLWHaXdKwbll-LuG5eJhxmT2SVs3Ql16IfVXgo4ztF4ZxMHB9KT0j-Q9h3xYbwebxDS-mX2sie8qeh0sNjZ7K8XVfDzCjVzhK85Yxy6zEeWaJjiCRlpMtGHapJgojhPjMxlCxpezxvl8fzBuqbp244xXKA=w1034-h806-no?authuser=0)

- 일단 컨피그맵으로 생성되고 나면, 컨피그맵의 내용이 파일이든지 문자열이든지 상관없이 사용 방버 자체는 같음
- 아래는 nginx.conf 파일을 컨피그맵으로 변경한 예제
  ![nginx](https://lh3.googleusercontent.com/pw/AM-JKLXsZuKXbTay0EkmSShhUM0D7QKaT-6i8inYTl9uPK3RuvNzfo0zUTdqFSF5BQ9f3aYSGeFgVrdCE2b4aYWhO6K3HhxmZpU1EPGu6faBdpQasi8RQtDfIulsh9VT0A9pe5uTHfVxRy48IoOA4MGWijz54w=w762-h604-no?authuser=0)
- key값이 파일 이름으로 설정된 것을 볼 수 있다.

#### YAML 파일로 컨피그맵 정의하기

- kubectl create 명령어에서 --dry-run 과 -o yaml 옵션을 사용하면 컨피그맵을 생성하지 않은 채로 YAML 파일의 내용을 출력할 수 있다.

  ![ry-run](https://lh3.googleusercontent.com/pw/AM-JKLXbi4YmnE-KfScu5pDOk_kenEh-mbxcWvy1Oblr_VruHX_ABmqFSuRAViKOr0rwjD-UC0CAcFdAOihVi7vaKSwnv6nuW1HWnNJ5TheZrCTkwzPwJNdv4hnqOTsrgLBsbPMhiHILPNcBQGn3DNad4h0itw=w1606-h610-no?authuser=0)

### 7.2.2 시크릿(Secret)

#### 시크릿 사용 방법 익히기

- 시크릿은 SSH  키, 비ㅁ리번호 등과 같이 민감한 정보를 저장하기 위한 용도로 사용되며, 네임스페이스에 종속되는 쿠버네티스 오브젝트
- 컨피그맵과 사용 방법이 비슷함
- 아래는 시크릿을 생성해서 패스워드를 저장하는 예제이다.

```sh
kubectl create secret generic \
my-password --from-literal password=1q2w3e4r
```

- 컨피그맵처럼 --from-literal 대신 --from-file 이나 --from-env-file 옵션을 이용해 파일로부터 값을 읽어와 사용가능

```sh
kubectl create secret generic \
our-password --from-file pw1 --from-file pw2
```

- generic 옵션은 데이터 사용목적에 따른 시크릿 종류를 나타낸 옵션
- 아래 내용은 저장된 시크릿 목록과 내용을 표시한 것이다.

  ![secret](https://lh3.googleusercontent.com/pw/AM-JKLUlrFaQZ5I3x8idwBhJ9MpzIDaq1R63ANJncZl_nHLqyVpIHvh8txUB4Lyg88K5yPFU_kRrO50sommrdRUUnVa3DxOmgvrfbGBclCCltfgEAE_nhLgBKmpUdQgodB_xqdEGqmZcU3Ce723SesysZVIvFA=w1052-h1226-no?authuser=0)

- 키-값 쌍에서 기본적으로 base64 값으로 인코딩하여 저장한다.
- 그렇기 때문에 YAML 파일로부터 secret를 생성할 때 데이터 값을 base64로 인코딩해서 YAML  파일을 작성해야 한다.
- 이렇게 생성된 시크릿은 컨피그맵과 비슷하게 사용할 수 있음
- 모든 키-값 쌍을 다 가지고 오려면 secretRef를 사용

```YAML
apiVersion: v1
kind: Pod
metadata:
  name: secret-env-example
  labels:
    name: secret-env-example
spec:
  containers:
  - name: my-container
    image: busybox
    args: ['tail', '-f', '/dev/null']
    envFrom:
      - secretRef:
          name: my-password

```

- 특정 데이터만 산태해 원하는 이름의 환경변수만 가지고 오려면 secretKeyRef를 사용

```YAML
apiVersion: v1
kind: Pod
metadata:
  name: selective-secret-env-example
  labels:
    name: selective-secret-env-example
spec:
  containers:
  - name: my-container
    image: busybox
    args: ['tail', '-f', '/dev/null']
    env:
      - name: YOUR_PASSWORD
        valueFrom:
          secretKeyRef:
            name: our-password
            key: pw2

```

- 또한 시크릿의 키-값 데이터를 파일로 포드의 볼륨에 마운트 할 수도 있음

```YAML
apiVersion: v1
kind: Pod
metadata:
  name: secret-volume-pod
spec:
  containers:
  - name: my-container
    image: busybox
    args: ['tail', '-f', '/dev/null']
    volumeMounts:                         
      - mountPath: /etc/secret            # 시크릿의 데이터가 위취할 경로
        name: secret-volume               # volumes에서 정의한 시크릿 볼륨 이름
  volumes:
    - name: secret-volume                 # 시크릿 볼륨 이용
      secret:
        secretName: our-password                # 키-값 쌍을 가져올 시크릿 이름
```

- 여러개의 키-값 쌍이 존재하는 시크릿에서 선택적으로 사용할 수도 있음

```YAML
apiVersion: v1
kind: Pod
metadata:
  name: selective-secret-volume-pod
spec:
  containers:
  - name: my-container
    image: busybox
    args: ['tail', '-f', '/dev/null']
    volumeMounts:                         
      - mountPath: /etc/secret            # 시크릿의 데이터가 위취할 경로
        name: secret-volume               # volumes에서 정의한 시크릿 볼륨 이름
  volumes:
    - name: secret-volume                 # 시크릿 볼륨 이용
      secret:
        secretName: our-password          # 키-값 쌍을 가져올 시크릿 이름(our-password )
        items:
          - key: pw1                      # pw1 라는 키에 대응하는 값이 사용됨
            path: password1               # 최종 파일은 /etc/config/password1 이 됨
```

- 시크릿을 YAML 파일로 생성할 때 파일에 base64로 인코딩한 값을 입력했더라도 시크릿을 포드의 환경변수나 볼륨 파일로서 가져오면 디코딩된 값을 사용

#### 이미지 레지스트리 접근을 위한 docker-registry 타입의 시크릿 사용하기

- 사용 목적에 따라 여러 종류의 시크릿을 사용할 수도 있음
- kubectl get secrets  명령어로 확인했을 때 TYPE 항목이 시크릿 종륭에 해당
  ![secrets](https://lh3.googleusercontent.com/pw/AM-JKLVaUPwrQA5gMz8sDXmEWGancCo4GL5MJyWvQUe9_J7pY8ihGS5I5ZtMc8v6UGHJU9oxBkOFL1VG-VgZcfjzTShVYHNGBeKZsCs5ZyeSrKRSAL_Vu6iPVU95XWA3ZM7GZAY37yanxWIwXpHDvBu6vXMnew=w1034-h164-no?authuser=0)

- 기본적으로 내부의 데이터를 들여다 볼 수 있는 Opaque 타입으로 설정돼어 있음
- 앞에 명시 했었던 generic 옵션이 Opaque 타입에 해당하는 종류
- 시크릿은 비공개 레지스트리에 접근할 때 사용하는 인증 용으로도 사용 가능
- 포드를 생성할 때 YAML  파일에 정의된 이미지가 로컬에 존재하지 않으면 공개된 허브에서 자동으로 받아옴
- 도커에서 사설 레지스트리에 접근하듯이 쿠버네티스에서는 docker login 명령어 대신에 레지스트리의 인증 정보를 저장하는 별도의 시크릿을 생성해 사용
- 레지스트리 인증을 위해 시크릿을 생성하는 방법은 두가지가 있음
- 첫번째는 docker login에 성공했을때 도커 엔진이 자동으로 생성하는 ~/.docker/config.json 파일을 사용하는 방법

```sh
kubectl create secret generic registry-auth \
--from-file=.dockerconfigjson=/root/.docker/config.json \
--type=kubernetes.io/dockerconfigjson

```

- 또는  시크릿을 생성한느 명령어에서 직접 로그인 인증 정보를 명시할 수도 있음
- --docker-username=<유저 이름>,  --docker-password=<유저 패스워드>, --docker-server=<사설 레지스트리 주소> 같은 옵션을 사용해 사용 가능

```sh
kubectl create secret docker-registry registry-auht-by-cmd \
--docker-username=alicek106 \
--docker-password=1q2w3e4r \
--docker-server=alicek106.registry.com
```

- 위 명령어로 생성된 시크릿은 kubernetes.io/dockerconfigjson 타입으로 설정
  ![dockerconfigjson](https://lh3.googleusercontent.com/pw/AM-JKLVzsbhe8NibRKH04pEvMHvVjYN_ELd6UE4Lv8zYnc9RvV-JBStmx5_tM5nfH34HNvHyzvOnfh5N4dpFIkQT3YYVRlS4BuIZaKsQTdnIk8qculKij9QWHdkuBIn6wmMe_-pWiwvbDpCAt5G2xjUigufWbQ=w1016-h476-no?authuser=0)

- 이 시크릿은 디플로이먼트 또는 포드 등에서 사설 레지스트리로부터 이미지를 받아 올 때 사용할 수 있다.

```YAML
apiVersion: apps/v1
kind: Deployment
metadata:
  name: deployment-from-private
spec:
  selector:
    matchLabels:
      app: deployment-from-private
  template:
    metadata:
      labels:
        app: deployment-from-private
    spec:
      containers:
      - name: deployment-from-private
        image: my-image                     # 개인이 만든 이미지 이름
        imagePullSecrets:
          - name:  registry-auht-by-cmd     # 시크릿 이름
```

#### TLS 키를 저장할 수 있는 tls 타입의 시크릿 사용하기

- 시크릿은 TLS ㅇ녀결에 사용되는 공개키, 비밀키 등을 쿠버네티스에 자체적으로 저장할 수 있도록 tls 타입을 지원
- 시크릿의 종류로 generic 이나 docker-registy 대신 tls를 사용하고, --cert와 --key 옵션을 사용해 인증서와 키를 직접 명시해 줌

```sh
-x509 -subj "/CN=example.com" -keyout cert.key -out cert.crt
Generating a 4096 bit RSA private key
```

- 시크릿 정보를 확인해보면 cert.crt와 cert.key 파일의 내용이 tls.cert와 tls.key 라는 키로 저장돼 있음을 알 수 있다. 모두 base64로 저장
  ![cert](https://lh3.googleusercontent.com/pw/AM-JKLUDnv6g6hDspRUGFV99qnYw3Uvou1UlVbeCc_4VzuMAPMcWt5tY76wGLsrIAs3HJdeNOuKRVcViBsKww7ZKWpN4Pv0lzqL77pqi56YbnqEovQ6PpdR9-uTTliMnNDxoiHr1wXxxPEWiuWl36cWLdGFKcw=w678-h904-no?authuser=0)

#### 좀 더 쉽게 컨피그맵과 시크릿 리소스 배포하기

- YAML 파일로 시크릿 정보를 배포하려면 데이터를 YAML 파일에 함께 저장해 두어야 하는데 가독성이나 파일과 데이터가 분리되지 않아 관리가 불편하다.
- kustomize 기능을 사용하면 시크릿을 생성하기 위한 제너레이터 파일을 관리할 수 있다.
- 컨피그맵도 사용 가능