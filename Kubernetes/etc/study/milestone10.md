## 레이블이란

### label이란?

* Node를 퐇마하여 pod,deployment등 모든 리소스에 할당
* key-value 한쌍으로 적용
* selector를 사용
* 많은 label을 통해 구분하는 편
* [https://kubernetes.io/ko/docs/concepts/overview/working-with-objects/labels/](https://kubernetes.io/ko/docs/concepts/overview/working-with-objects/labels/)

```yaml
## ex

metadata:
  name: label-demo
  labels:
    environment: production
    app: nginx

--- 
selector:
  matchLabels:
    component: redis
  matchExpressions:
    - {key: tier, operator: In, values: [cache]}
    - {key: environment, operator: NotIn, values: [dev]}
```

* kubectl get pods -l app=pay /  kubectl get pods --show-labels /  kubectl delete pods -l app=pay 와 같은 명령어들로 응용 가능
* kubectl label 과 같은 명령어로 label 할당 가능

## 워커 노드에 레이블 설정

* 특정 하드웨어 리소스가 있는 node를 선택할 수 있음

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: cuda-test
spec:
  containers:
    - name: cuda-test
      image: "k8s.gcr.io/cuda-vector-add:v0.1"
      resources:
        limits:
          nvidia.com/gpu: 1
  nodeSelector:
    accelerator: nvidia-tesla-p100
```

## 레이블과 어노테이션

* label과 동일하게 key-value를 통해 리소스의 특성을 기록
* k8s에게 특정 정보 및 관리를 위해 필요한 정보를 기록한 용도로 사용
  * 빌드, 릴리즈, 타임스탬프, git 브랜치 등등
  * 롤링 업데이트 정보 기록
  * auth 정보 등등
* label 공통점과 차이점
  * 공통점 : 오브젝트에 메타데이터를 첨부할 수 있음
  * 차이점 : 레이블은 만족하는 오브젝트 컬렉션을 찾을 수 있음, 다만 어노테이션은 오브젝트를 식별하고 선택하는데 사용되지 않음
* [ex) nginx annotaions](https://kubernetes.github.io/ingress-nginx/user-guide/nginx-configuration/annotations/)

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: annotations-demo
  annotations:
    imageregistry: "https://hub.docker.com/"
spec:
  containers:
  - name: nginx
    image: nginx:1.14.2
    ports:
    - containerPort: 80
```

## 레이블과 카나리 배포

* 배포 방법
  * 블루-그린
  * 카나리
  * 롤링
* 카나리 : 기존 버전을 유지한 채로 신규 버전을 올려서 이상이 없는지 확인
