# 8. 인그레스(Ingress)

- 인그레스는 외부 요청을 어떻게 처리할 것인지 네트워크 7계층 레벨에서 정의하는 쿠버네티스 오브젝트
- 인그레스 오브젝트가 담당하는 기본적이 기능
  - 외부 요청의 라우팅 : 특졍 path로 들어오는 요청을 어떠한 서비스로 전달하맂 정의하는 라우팅 규칙을 설정
  - 가상 호스트 기반의 요청 처리 : 같은 IP에 대해 다른 도메인 이름으로 요청이 도착했을 때, 어떻게 처리할 것인지 정의할 수 있음
  - SSL/TLS 보안 연결 처리 : 여러 개의 서비로 요청을 라우팅할 때 보안 연결을 위한 인증서를 쉽게 제공할 수 있음

## 8.1 인그레스를 사용하는 이유

- 인그레스를 사용하지 않고 서비스로 각각의 디프롤이먼트에 하나씩 연결할 수도 있음
- 다만 그렇게 한다면 SSL/TLS 보안연결, 접근 도메인에 기반한 라우팅 등을 구현하기 위해서는 각각의 디플로이먼트에 대해 일일이 설정을 해줘야 한다.
- 각 디플로이 먼트를 외부로 노출하는 인그레스를 생성한다면 단 하나의 URL 로만 접근하여 정의한 PATH 규칙에의해 각각의 서비스로 트래픽을 분리 시킬수 있다.
- 중요한 점은 라우팅 정보나 보안 연결 등과 같은 세부 설정은 서비스와 디플로이먼트가 아닌 인그레스에 의해 수행된다는 것

## 8.2 인그레스의 구조

- kubectl get ingress 명령어로 인그레스 목록 확인 가능
- 아래는 간단한 ingress 를 정의한 YAML 파일 예제

```YAML
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: ingress-examples
  labels:
    name: ingress-examples
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    kubernetes.io/ingress.class: "nginx"            # 해당 어노테이션은 1.18 버전부터는 Deprecated 
spec:
  - host: alicek106.example.com
    http:
      paths:
      - pathType: Prefix                     
        path: /echo-hostname
        backend:
          service:
            name: hostname-service
            port: 
              number: 80

```

- spec.rules[0].host : 해당 도메인 이름으로 접근하는 요청에 대해서 처리 규칙을 적용, 여러개의  host를 정의해 사용 가능
- spec.rules[0].http.paths[0].path : 해당 경로로 들어온 요청을 어느 서비스로 전달할 것인지 정의. 여러개의 path를 정의해 사용 가능
- spec.rules[0].http.paths[0].backend.service.name / port :  path로 들어온 요청이 전달될 서비스와 포트, 위 예제에서 보면  alicek106.example.com/echo-hostname로 들어온 요청을 hostname-service 의 80포트로 전달
- kubectl get ingress 명려어를 통해 확인해보면 ingress는 생성되었음
- 하지만 해당 ingress는 선언적인 오브젝트일뿐 외부 요청을 받아 들이는 실제 서버는 아니다.
- 인그레스는 인그레스 컨트롤러라고 하는 특수한 서버에 적용해야만 그 규칙을 사용할 수 있다.
- 즉, 실제로 외부요청을 받아들이는 것은 인그레스 컨트롤러 서버이며, 이 서버가 인그레스 규칙으르 로드해서 사용한다.

  ![인그레스 컨트롤러](https://lh3.googleusercontent.com/pw/AM-JKLV15Z9MWHkEleJl7RwXIlEsqnw88NV5KQCRfkB2wZ5fKyJ4jkU9Y3oEhuZr36eTd3QaDUrflrUsChFFDUbU5sGe1bWLobJnH4vB4R4xhb1WnTCQi-ZDe0Y4pszyAV-y9gqsxCzkgTy3zQ_0f9PGWcbkFw=w1582-h1020-no?authuser=0)

- 따라서 쿠버네티스의 인그레스는 반드시 인그레스 컨트롤러라는 서버와 함께 사용해야 한다.
- 인그레스 컨트롤러 서버는 [여러 종류](https://kubernetes.io/ko/docs/concepts/services-networking/ingress-controllers/)가 있으며, 필요에 따라 선택해서 사용하면 됨
- [아래 명령어](https://kubernetes.github.io/ingress-nginx/deploy/#docker-desktop)를 통해 nginx 인그레스 컨트롤러 관련 리소스를 한 번에 설치할 수 있다.

```sh
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v0.48.1/deploy/static/provider/cloud/deploy.yaml
```

- ingress-nginx 네임스페이스의 디플로이먼트와 포드를 확인해보면 Nginx 웹 서버를 확인할 수 있다.

  ![ingress](https://lh3.googleusercontent.com/pw/AM-JKLW3g02Aba54SiFHb8yeNf0cm4che6iv-CxEREmAYWE0CjYB1KcLZArfuXg9weZJISIoo7Stw9Ljp_RCC5wvhu_V7Cs4IBS0VTsP2ERBu29ls_Qv8xWxOOMh09vWSPX06-mPGlqOPEDT0cFI8Jha4uSXqg=w1260-h320-no?authuser=0)

- 그렇지만 Nginx 웹 서버를 외부로 노출하기 위한 서비스는 직접 작성해 줘야 함
- 클라우드 환경이라면 LoadBalancer 타입의 서비스를 생성하겠지만 일반적인 가상머신 환경이므로 NodePort 타입의 서비스를 생성해야 한다.
- 아래는 인그레스 컨트롤러 nginx 포드에 접근하기 위한 NodePort 타입의 서비스 예제이다.

```YAML
apiVersion: v1
kind: Service
metadata:
  name: ingress-nginx
  namespace: ingress-nginx
spec:
  type: NodePort
  selector:
    app.kubernetes.io/name: ingress-nginx 
  ports:
  - port: 80
    name: http
    targetPort: http
  - port: 443
    name: https
    targetPort: https

```

- selector는 인그레스 컨트롤러 포드에 기본적으로 설정된 라벨을 selector에 명시했음
- nginx 디플로이먼트가 위치한 ingress-nginx 네임스페이스에 서비스를 생성해야 한다.
- 아래는 인그레스의 종착점이 될 테스트용 디플로이먼트와 서비스 예제이다.

```YAML
# hostname-service.yaml
apiVersion: v1
kind: Service
metadata:
  name: hostname-service
spec:
  ports:
    - name: web-port
      port: 80
      targetPort: flask-port
  selector:
    app: webserver
  type: ClusterIP

---

# hostname-deployment.yaml
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
      name: my-webserver
      labels:
        app: webserver
    spec:
      containers:
      - name: my-webserver
        image: alicek106/ingress-annotation-test:0.0
        ports:
        - containerPort: 5000
          name: flask-port
```

- <http://localhost/echo-hostname> 접근시 정상적인 화면을 볼 수 있다.

### 인그레스 컨트롤러의 동작 원리 이해

1. [Nginx 인그레스 컨트롤러를 생성](https://kubernetes.github.io/ingress-nginx/deploy/#docker-deskto)
2. Nginx 인그레스 컨트롤러를 외부로 노출하기 위한 서비스를 생성
3. 요청 처리 규칙을 정의하는 인그레서 오브젝트 생성
4. Nginx 인그레스 컨트롤러로 ㄷ르어온 요청은 인그레스 규칙에 따라 적절한 서비스로 전달


## 8.3 인그레스의 세부 기능 : annotation을 이용한 설정

- 이전에 작성된 ingress를 살펴보면 아래와 같은 annotation을 볼 수 있다.

```YAML
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: ingress-examples
  labels:
    name: ingress-examples
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    kubernetes.io/ingress.class: "nginx"            # 해당 어노테이션은 1.18 버전부터는 Deprecated 
```

- kubernetes.io/ingress.class는 해당 인그레스 규칙을 어떤 인그레스 컨트롤러에 적용할 것인지를 의미
- 쿠버네티스 클러스터 자체에서 기본적으로 사용하도록 설정된 인그레스 컨트롤러가 존재하는 경우 컨트롤러를 인그레스에 명시해 주어야함
- nginx.ingress.kubernetes.io/rewrite-target annotation은 인그레스에 정의된 경로로 들어오는 요청을 rewrite-target에 설정된 경로로 전달하는 역할을 함
- 여러 어노테이션들은 각 컨트롤러에 맞는 공식 문서에서 확인 가능, [nginx 어노테이션](https://kubernetes.github.io/ingress-nginx/user-guide/nginx-configuration/annotations/)

## 8.4 Nginx 인그레스 컨트롤러에 SSL/TLS 보안 연결 적용

- 인그레스를 활용하면 디플로이먼트나 서비스가 아닌, 앞쪽 인그레스 컨트롤러에 편리하게 SSL/TLS 보안 연결을 설정할 수 있음 
- 아래는 사용할 인증서를 만드는 명령어이다.

```sh
 openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout tls.key -out tls.crt -subj "/CN=localhost" # /CN 부분은 본인의 호스트를 입력
```

- kubectl create secret tls tls-secret --key tls.key --cert tls.crt 명령어로 secret를 생성
- 그리고 ingress 파일에 tls 옵션을 추가

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: ingress-examples
  labels:
    name: ingress-examples
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    kubernetes.io/ingress.class: "nginx"            # 해당 어노테이션은 1.18 버전부터는 Deprecated 
spec:
  tls:
  - hosts:
      - localhost
    secretName: tls-secret
  rules:
  - host: localhost
    http:
      paths:
      - pathType: Prefix                     
        path: /echo-hostname
        backend:
          service:
            name: hostname-service
            port: 
              number: 80

```

- spec.tls.hosts 항목에서 보안연결을 적용할 도메인 이름을 입력
- 이후 요청을 보내면 정상적으로 접근할 수 있는 것을 볼 수 있다.(브라우저에서는 공인된 인증서가 아니기 때문에 에러가 발생 할 수 있음)

  ![사파리](https://lh3.googleusercontent.com/pw/AM-JKLVYI9cRy5X6IyT8VNXbVSESpycsrVhFFevEVdHUCDlsjW6Q-31Nve-rG60DeY07C7QV94WT6vBpeI1LFMsXwc4xZJV9JpYf7wJW5aj0PFBy95-gnrUoqGB4hzhycukRMV3BY0Gd9Zgud9NnVcUIDeXJQA=w1284-h958-no?authuser=0)

  ![curl](https://lh3.googleusercontent.com/pw/AM-JKLUidhxFbApzSWGDT-q1Pbr-ixVZ4QYx2NtP8pVBJ8j406pkUQLWOe1BcnGiSAEAtzLDY_BNV5aIHySqC9i_u3pJ88BYFsq14WWcxNIAZmeu9z58nt_H00z4V9pDiz4HzRPkcT1B3UOiJQMwJkxOz4z5XA=w850-h324-no?authuser=0)

- 보안 연결을 사용하면 http로 연결하더라도 https로 연결됨, nginx 인그레스 컨트롤러가 https 리다이렉트 어노테이션인 ssl-redirect를 자동으로 true로 설정함

## 8.5 여러 개의 인그레스 컨트롤러 사용하기

- 하나의 쿠버네티스 클러트터에서 반드시 하나의 인그레스 컨트롤러를 사용해야 하는 것은 아님

