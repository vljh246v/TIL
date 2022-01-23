# Deployment

* ReplicaSet을 제어해주는 역할
* Rolling Update / Rolling Back를 지원

> Rolling Update?
> 파드 인스턴스를 새로운 것으로 업데이트하여 서비스 중단 없이 이루어질수 있게 하는 것

* 문법은  ReplicaSet을과 거의 동일

```yaml
apiVersion: apps/v1
kind: Deployment ## kind만 다름
metadata:
  name: deploy-nginx
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

![deploy/rs/pod 관계](https://lh3.googleusercontent.com/pw/AM-JKLV6H-xpKYrArmg2oWRksKOmtkz2A6PWsRFNZ6HVYVFKqIJIaj3YIFp84NbGFT1z83IF_0F-4lgayCbU3bGEI8c72XctrS5vsQcAufA8namxSyeNNZfu987O8B-pgmi1zfHxh4F5ziCO0Pv4h21MoL3QxQ=w525-h214-no?authuser=0)

* Deployment -> ReplicaSet -> Pod 형태로 생성
* pod명 : {Deployment 이름}-{ReplicaSet 이름}-{Pod 이름}
  * ex : Deployment 이름 : deploy-nginx / ReplicaSet 이름 : deploy-nginx-747dd47b7b / Pod 이름 : deploy-nginx-747dd47b7b-ttrlb

* Deployment를 kubectl set 명령어를 통해 업데이트

![kubectl set](https://lh3.googleusercontent.com/pw/AM-JKLVWe-_FODLoE6q4pZFDqKzizmhG1qRbixKIPZhVJ0PC0OtBvjhPF9onXXAFk3hVErErG92T91JTLdF6eHSUMvV6PzkHblFnlzZgZcrrRljCArim6-z6a37zrGKSIoieOpx_iCY7Bgi3PDdeZhrQH_VV7Q=w2275-h737-no?authuser=0)

* --record optin 은 deprecated, 자동으로 revision 기록
* kubectl rollout status deployment {deployment 이름} 명령어를 통해 디플로이먼트 진행사항을 모니터링 할 수 있음
* kubectl rollout pause/resume deployment {deployment 이름}  명령어를 통해 롤아웃 중인 delpoyment를 일시정지/재시작 할수있음
* 일시정지 시켜두고 deployment에 옵션을 여러번 적용 시킬 수 있다.
  * 그렇게 되면 업데이트 할 때마다 롤아웃이 트리거 되는것을 막을 수 있다.
* .spec.progressDeadlineSeconds : 실패 전 대기 시간, 계속해서 retry 하는 시간, .spec.minReadySeconds 보다는 커야 한다.
* .spec.minReadySeconds : 파드가 사용할 수 있또록 준비 되어야 하는 시간
* .spec.strategy.rollingUpdate
  * maxSurge : 설정값이 20%면 업데이트하는 동안 실행하는 총 파트수가 replicas에 120%가 되도록 보장
  * maxUnavailable : 설정값이 20%면 업데이트하는 동안 사용할 수 있는 총 파트수가 replicas에 80% 이상이 되도록 보장
* kubectl rollout undo deployment {deployment 이름} --to-revision={리비전 번호}로 롤백 가능, --to-revsion은 생략가능, 생략시 전 단계로 이동
  * undo를 연속으로 한다고해서 모든 revision을 거슬로 올라가는건 아님, 대상 revision이 사라지고 가장 마지막 revision 쓰이는 방식
* annotations을 사용해 REVISION에 CHANGE-CAUSE 관리 가능

# DaemonSet

* 전체 노드에서 Pod가 한 개씩 실행되도록 보장
* 로깅, 모니터링, 네트워킹 등을 위한 에이전트를 각 노드에 생성해야 할 때 유용

![master-node](https://lh3.googleusercontent.com/pw/AM-JKLU7a569ZHFjJgOJJGeOpd5fcE-O5UH55CT9FrRJ4YYNlQzSK9soPsP3J0cRfpAf6PbNLHZkZ-zz8m5EIzfyf2H6LaCY4a4OUWgwEJflFQRPrNb5lZKTTbcCUD4cuFIS_m7WuYDbkSsyvvJ2pGvSEWyM_g=w687-h88-no?authuser=0)

* 노드당 파드 실행을 보장
* Rolling Update를 지원

> 마스터 노드에서도 실행되는 파드를 만드려면!?
> tolerations 톨러레이션을 설정하면 됨
> 
> ```yaml 
> ...
> spec:
>   template:
>     spec:
>       tolerations:
>       # this toleration is to have the daemonset runnable on master nodes
>       # remove it if your masters can't run pods
>       - key: node-role.kubernetes.io/master
>         operator: Exists
>         effect: NoSchedule
> ```
>
> [Taints 와 Tolerations](https://kubernetes.io/ko/docs/concepts/scheduling-eviction/taint-and-toleration/)
> lable 값이 아닌 taint 라는 방법으로 파드 할당할 노드를 컨트롤할 수 있음
> taint(더러움/얼룩)
> taint 라는 기능을 통해 특정 노드에 포드가 할당되는 것을 막는 기능
> ![master-taint](https://lh3.googleusercontent.com/pw/AM-JKLVHOhcYzQLQllE0uutfR1Y3Qvfd9q71GvvFV8laE2eYEbzh-8h7BsV-Zx-c_b-C2mD-ssH5v6aAitfSB8T0Tumv8E8cF8_cko3zs1immfK-a09rNZs5JNfdUoECRmrCVHoP1DY46DlbTb8CnRY_ciiYRg=w496-h43-no?authuser=0)
> taint에 대응하는 tolerations(관용/용인) 을 설정가능
> 더러움을 용인한다는 뜻으로 생각?
> 

# StatefulSet

* 상태를 보존해주는 컨트롤러
* Pod의 이름을 보존해줌
* Pod 이름을 정의하는 이유는 추후 서비스를 사용할때 접근을 {pod 이름}-{번호}.{서비스 이름} 식에 접근이 가능하게 하기 위함이다.
* serviceName을 정의하는 이유는 특정 서비스만 파드로 접근할 수 있게 하기 위함
* 만약 데이터베이스라면 master가 생성되고 slave 가 생성되야 한다면 podManagementPolicy: OrderedReady로 설정이 필요