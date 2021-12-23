# 쿠버네티스 동작 flow

1. hub에 image를 push
2. 만들어질 object(pod, deployment 등등)와 image 이름을  kubectl 을 통해 master(controll-plane)에게 요청
3. master 에서 api server가 요청을 받음
4. 스케줄러에게 어떤 worker node에 해당 이미지를 실행할지 문의
5. 선택된 node에 kubelet에게 생성 요청 
6. kubelet 가 도커데몬에게 요청하고 도커 데몬이 image를 pull 받아 pod를 생성

# 쿠버네티스 컴포넌트

## 마스터 컴포넌트

* api 서버 : 쿠버네티스 클러스터의 중심 역할, 대부분의 요소들이 api 서버를 중심으로 통신
* etcd : 구성 요소들의 상태값이 모두 저장, 해당 내용만 백업되어있다면 복구 가능, key-value 형태
* 스케줄러 : 파드를 어떤 워커 녿에 생성할 것인지를 결정하고 할당.
* 컨트롤러 : 컨트롤러 매니저는 쿠버네티스 클러슽의 오브젝트 상태를 관리

## 워커 노드 컴포넌트

* kubelet : 파드의 구성 내용을 받아서 컨테이너 런타임으로 전달, 파드 안의 컨테이너들이 정상적으로 작동하는지 모니터링
* kube-porxy : iptables rule을 구성
* 컨테이너 런타임 : 컨테이너를 실행하는 엔진

## etc

* dns 애드온 : dns 서버, \coreDns를 주로 사용
* 네트워크 플러그인 : 일반적으로 CNI로 구성

# 쿠버네티스 namespace

* 클러스터에서 사용되는 리소스들을 구분해 관리하는 그룹
* kubectl create namespace {이름}
* 기본은 default
* 삭제는 신중하게

## 명령어

* kubectl get namespaces : 네임스페이스 목록 조회
* kubectl get {오브젝트} -n {네임스페이스 이름} : 특정 네임스페이스의 오브젝트 목록 조회
* kubectl get {오브젝트}  --all-namespaces : 전체 네임스페이스의 오브젝트 목록 조회
* kubectl create namespace {이름} : 네임스페이스 생성
* kubectl config view : kubeconfig 정보를 확인 context 내용도 있음
* kubectl config set-context [NAME | --current] [--cluster=cluster_nickname] [--user=user_nickname] [--namespace=namespace] : context 추가
* kubectl config current-context : 현재 context
* kubectl config use-context {context 이름}

# yaml 템플릿

* 들여쓰기 주의!
* key-value


```yaml
apiVersion: v1
kind: Pod
metadata:
  name: mypod
spec:
  containers:
  - image: nginx:1.14
    name: nginx
    ports:
    - containerPort: 80
      containerPort: 443
```

* kubectl explain pod : api 버전 확인 가능