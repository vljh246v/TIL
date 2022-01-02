# self-healing

* [self-healing](https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller/#how-a-replicationcontroller-works)
* 건강한 컨테이너로 서비스를 보장해주겠다.
* liveness probes : 정상적이라면 실행, 아니라면 재실행
* livenessProbe 옵션을 컨테이너에 설정해줘야함
* 건강하지 않다면 컨테이너를 restart

```yaml
...
spec:
  containers:
  - ...
    livenessProve:
      httpGet:
        path: /
        port: 80 # web 서비스라면 해당 포트와 path 로 healing check 를 함
```

# livenessProve

* [livenessProve](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/)
* 3가지 형태의 livenessProve 제공
  * httpGet : 지정한 ip / path / port로 http get 요청을 통해 200응답인지 확인
  * tcpSocket : 지정한 port에 tcp연결을 시도
  * exec : 특정 명령을 전달하고 명령의 종료코드가 0인지 확인
* initialDelaySeconds : 컨테이너 runngin 이후 정해진 시간(초) 후 부터 검사, default : 0, minimum : 0
* timeoutSeconds : 정해진 시간(초) 만큼 검사 후 대기 시간 (이후 에는 에러로 처리)
* periodSeconds : 검사 하는 주기(초), 인터벌을 잘 조절 필요
* successThreshold, failureThreshold : 몇번 실행을 성공/실패로 볼 것인지

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: liveness-exam
spec:
  containers:
  - image: busybox
    name: busybox-container
    args:
      - /bin/sh
      - -c
      - touch /tmp/healthy; sleep 30; rm -rf /tmp/healthy; sleep 600
    livenessProbe:
      exec:
        command:
        - ls
        - /tmp/healthy
      initialDelaySeconds: 10
      failureThreshold: 2
      successThreshold: 1
      periodSeconds: 5
```

# init contaienr를 적용한 pod

[Init Containers](https://kubernetes.io/docs/concepts/workloads/pods/init-containers/)

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: myapp-pod
spec:
  containers:
  - name: myapp-container
    ...
  initContainers:
  - name: init-myservice
    ...
  - name: init-mydb
    ...
```

* 특정 컨테이너 실행 전 전단계를 실행하는 컨테이너 (초기화, 설정 등등)
* ![ddd](https://lh3.googleusercontent.com/pw/AM-JKLWD0CyqD_NgDPgQvJFARcLpvOFHp46LjrtvvCSs4j_W8oZB_4rWcy44GyVpXJWnyVih0F0hiZ4GZVmZhTU-uqtLKIxrjq0mwgL1gqa4iazF7DBgAekX6y6_NDcsVbnYgrIFu0r1be0Jqn2UmkmR_xXVfQ=w219-h59-no?authuser=0)
* 초기화 컨테이너가 실행 되어야 main 컨테이너가 실행
* n개의 init container를 설정 가능

# infra container(pause) 이해하기

* 리눅스의 네임스페이스를 공유하기 위해 파드별로 생성되는 컨테이너
* pod 내부의 사용자가 생성한 container들을 위한 부모 container 역할
* pause 컨테이너를 상속받아서 
* 각 파드별에 대해 자동으로 생성
* 파드별로 동일한 pause 컨테이너 값을 상속받고, 동일한 네트워크 환경을 가짐
* 그렇기 떄문에 파드 내부에서 컨테이너들이 localhost로 통신이 가능
* child container가 좀비 프로세스가 될 경우 제거하는 역할도 함
* [pasue container 의 소스 코드](https://github.com/kubernetes/kubernetes/blob/master/build/pause/linux/pause.c)