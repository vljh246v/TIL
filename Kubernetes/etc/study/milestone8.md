# Service

## Kubernetes Service의 개념

* 동일한 서비스를 제공하는 Pod 그룹의 단일 진입점을 제공
* pod의 label을 기준으로 묶음
* LB 역할

```yml
apiVersion: v1
kind: Service
metadata:
  name: webui-svc
spec:
  clusterIP: 10.96.100.100
  selector:
    app: webui
  ports:
  - port: 80
    targetPort: 80

--- 

apiVersion: apps/v1
kind: Deployment
metadata:
  name: webui-deployment
spec:
  replicas: 3
  selector:
    matchLabels:
      app: webui
  template:
    metadata:
      labels:
        app: webui
    spec:
      containers:
      - name: nginx-container
        image: nginx:1.14

```

## Service Type (요약)

* 4가지 Type 지원
  * ClusterIP(default)
    * Pod 그룹의 단일 진입점(Virtual IP) 생성
    * 내부에서만 Pod들에 접근할 때 사용
  * NodePort
    * Pod에 접근할 수 잇는 포트를 클러스터의 모든 worker node에 동일하게 개방
    * 외부에서 접근 가능
    * 접근 포트는 랜덤 또는 직접 지정 가능
    * ClusterIP 기능을 포함하고 있기 때문에 내부/외부에서 모두 접근 가능
    * NodePort로 서비스를 외부에 제공하는 경우는 많지 않음
    * DefaultNodePort 범위 : 30000-32767
  * LoadBalancer
    * 클라우드 인프라스트럭처나 오픈스택 클라우드에 적용
    * LoadBalancer를 자동으로 프로비전하는 기능 지원
    * NodePort와 마찬가지로 외부에서 pod에 접근할 수 있는 서비스 타입
  * ExternalName
    * 클러스터 안에서 외부에 접속 시도 시 사용할 도메인을 등록해서 사용
    * 클러스터 도메인이 실제 외부 또메인으로 치환되어 동작
    * ExternalName 서비스의 이름으로 요청을 보낼 경우, 설정한 ExternalName 속성에 설정한 CNAME 레코드를 반환

>
> netstat 명령어 사용 방법 : yum install net-tools 실행
>


* 서비스에 selector 가 없다면?
  * endpoint 오브젝트를 직접 만들어 매핑 가능

## Headless Service

* ClusterI가 없는 서비스
* 서비스의 이름으로 Pod 위치를 알아내기 위해 사용
* Service와 연결된 Pod의 endpoint로 DNS레코드가 생성
* Headless Service / 일반적 서비스(ClusterIp type)

![nslookup1](https://lh3.googleusercontent.com/pw/AM-JKLVJRbykBHCdfBWupVpSf9GYacvZBbo9zexDlKbBWGYeRBE3vDVuFnbXTOejg7rT6mAz1PxdxGNQ2oYXqt2cJMs1FAIzAX7kcSK0GJQZ6pDLXgi_j8ryHXFtocybb0BAgbvepOsISAc3Hb-QggbDW0LdPw=w919-h418-no?authuser=0)

![nslookup2](https://lh3.googleusercontent.com/pw/AM-JKLWHhewKxJ_ljuKNcmIh8VaBedvdUEVr2udjvAzhYUwkEOphw-VUJ8t9r8i0ASvs69cZAJ8psh4ppwjAIPAk0p3jXwW5wQTRG8WRtfIZhJVHqXpI5VZ_Z8-zslLE7sS_bVdxaSm2C4gPMDsNMhHLwGJS5g=w899-h366-no?authuser=0)

## kube-proxy

* service 를 만들게 되면 클러스터 ip에 해당하는 iptables rule을 만듬
* NodePort로의 접근과 Pod 연결을 구현
* [iptables 프록시 모드](https://kubernetes.io/ko/docs/concepts/services-networking/service/#proxy-mode-iptables)


![iptables 프록시 모드](https://d33wubrfki0l68.cloudfront.net/27b2978647a8d7bdc2a96b213f0c0d3242ef9ce0/e8c9b/images/docs/services-iptables-overview.svg)