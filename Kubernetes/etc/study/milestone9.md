# Ingress

* HTTP나 HTTPS를 통해 클러스터 내부의 서비스를 외부로 노출
* 기능
  * Service에 외부 URL을 제공
  * 트래픽 로드밸런싱
  * SSL 인증서 처리
  * Virtual hosting을 지정

## Ingress 동작 방식

* Ingress Controller
  * 서비스들의 단일 진입점 제공
  * 쿠버네티스가 오픈프로젝트로 제공하거나 서드파티에서 만든 컨트롤러가 있음
* Ingress Rules을 만들어서 Ingress Controller에 적용
  * path 기반으로 Service 등록

## Nginx Ingress Controller

* 기본 namespace는 ingress-nginx

```yaml
## controller pod
apiVersion: apps/v1
kind: Deployment
metadata: ...
spec:
  selector:
    matchLabels:
      app.kubernetes.io/name: ingress-nginx
      app.kubernetes.io/instance: ingress-nginx
      app.kubernetes.io/component: controller
    ...
  template:
    metadata:
      labels:
        app.kubernetes.io/name: ingress-nginx
        app.kubernetes.io/instance: ingress-nginx
        app.kubernetes.io/component: controller
    ...
    spec:
      containers:
        - name: controller
          image: k8s.gcr.io/ingress-nginx/controller:v1.1.1@sha256:0bc88eb15f9e7f84e8e56c14fa5735aaa488b840983f87bd79b1054190e660de
```

```yaml
# NodePort를 통해 클라이언트 커넥션을 받음
apiVersion: v1
kind: Service
metadata:
spec:
  type: NodePort
  ...
  ports:
    - name: http
      port: 80
      protocol: TCP
      targetPort: http
      appProtocol: http
    - name: https
      port: 443
      protocol: TCP
      targetPort: https
      appProtocol: https
  selector:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/instance: ingress-nginx
    app.kubernetes.io/component: controller
```
