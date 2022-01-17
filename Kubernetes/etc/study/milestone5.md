# 컨트롤러

* Controller 란? : Pod 개수를 보장
* 다양한 컨트롤러 종류가 있음
  * [ReplicationController](https://kubernetes.io/ko/docs/concepts/workloads/controllers/replicationcontroller) : 초기 파드 레플리카를 보장하던 컨트롤러, 현재는 ReplicaSet을 구성하는 Deployment가 권장사항
  * [ReplicaSet](https://kubernetes.io/ko/docs/concepts/workloads/controllers/replicaset) : ReplicationController를 계승한 컨트롤러
  * [Deployment](https://kubernetes.io/ko/docs/concepts/workloads/controllers/deployment) : Replicaset의 상위 오브젝트, Replicaset의 변경사항을 남겨 관리가 가능
  * [DaemonSet](https://kubernetes.io/ko/docs/concepts/workloads/controllers/daemonset) : 모든 노드에 동일한 포드를 하나씩 생성하는 오브젝트
  * [StatefulSet](https://kubernetes.io/ko/docs/concepts/workloads/controllers/statefulset/) : 상태를 가지는 파드를 관리하기 위한 오브젝트
  * [Job](https://kubernetes.io/ko/docs/concepts/workloads/controllers/job/) : 특정 동작을 수행하고 종료해야 하는 작업을 위한 오브젝트, 최종 상태가 '특정 개수의 포드가 실행 중인 것'이 아닌 '포드가 실행되어 정상적으로 종료되는 것' 이 중요
  * [CronJobs](https://kubernetes.io/ko/docs/concepts/workloads/controllers/cron-jobs/) : 반복 일정에 따라 잡을 만드는 오브젝트

## ReplicationController

* 요구하는 Pod의 개수를 보장
* 요구하는 Pod의 개수보다 부족하면 template를 이용해 pod를 추가
* 기본 구성 : selector, replicas, template
* **selector**에 만족하는 **template**을 충족하는 Pod의 **replica**를 보장해줌
* kubectl create --help 명령어를 통해 현재 생성할 수 있는 Commands는 아래와 같다. (버전 v1.23.1)
![Available Commands](https://lh3.googleusercontent.com/pw/AM-JKLUnvYuE0o27c4M7i0gAU5fV5-F21mZg9uTYM6O0dDwKCvql22Y68-f4qbVr-lnbgcw48qVsxVBmdec7zy0VjDO6NUBw523qQyL-RAq2BqJxyDGv1Olz8fjm2TbAb1FaUhKZF1hEANmJfNkel3ImRe9_JQ=w635-h336-no?authuser=0)

```yaml
apiVersion: v1
kind: ReplicationController
metadata:
  name: rc-nginx
spec:
  replicas: 3
  selector:
    app: webui
  template:
    metadata:
      name: nginx-pod
      labels:
        app: webui
    spec:
      containers:
      - name: nginx-contaienr
        image: nginx:1.14
        ports:
        - containerPort: 80
```

* selector 에 있는 label이 pod template에 포함되어 있어야함
* 생성하면 {ReplicationController의 이름}-xxxxx 형태로 pod가 만들어짐
* 동일한 label을 가진 pod를 생성하면 보장된 pod replica에 따라 생성이 불가능하다.
* kubectl scale rc 명령어를 통해 replica를 설정할 수 있다. 이때 최근에 만들어진 pod부터 제거 대상이 된다.

* Q1

```yaml
apiVersion: v1
kind: ReplicationController
metadata:
  name: rc-lab
spec:
  replicas: 2
  selector:
    name: apache
  template:
    metadata:
      name: httpd-pod
      labels:
        name: apache
        app: main
        rel: stable
    spec:
      containers:
      - name: httpd-container
        image: httpd:2.2
```

* Q2 : kubectl scale rc rc-lab --replicas=3

## ReplicaSet

* ReplicationController 유사함
* 차이점은 selector에서 표현식 기반 셀렉터를 사용할 수 있음

```yaml
...
selector:
  matchLabels:
    component: redis
  matchExpressions:
    - {key: tier, operator: In, values: [cache]}
    - {key: environment, operator: NotIn, values: [dev]}
...
```

* matchExpression 연산자: In, NotIn, Exists, DoesNotExist

```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: rs-nginx
  labels:
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

* --cascade=false 옵션을 사용한다면 pod는 남겨두고 pod를 컨트롤하는  ReplicaSet만 삭제한다.