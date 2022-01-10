# static pod

* kubelet Daemon 에 의해 동작되는 pod
* 기존에 pod 생성절차와는 다르게 kubelet 데몬에 의해 관리
* kubelet config에 설정된 staticPodPath 디렉토리에(ex : /etc/kubernetes/manifests/)  pod 형태의 yaml 파일을 사용해 생성 및 삭제
* 노드별로 구성 가능
* worker node /var/lib/kubelet/config.yaml 파일에 있는 staticPodPath 경로를 수정하여 static pod 디렉토리를 설정할수 있음, kubelect 데몬은 반드시 restart!
* api-server, contorller, scheduler, etcd 는 master node의 static pod

# Pod에 리소스 할당하기

* 모든 node들은 제한된 컴퓨팅 시스템으로 구동
* 제한이 없는 container를 사용할 경우 node의 시스템 리소스를 전부 사용할수도 있음, 그렇기 떄문에 리소스 제한 제한 설정이 필요
* 바람직한 스케쥴링을 위해 명시가 필요
* spec.containers[].resources.requests : 컨테이너가 보장받아야 할 자원의 양, 스케쥴러가 할당시 판단
* spec.containers[].resources.limits : 컨테이너가 사용할 수 있는 최대 리소스
* Overcommit 설정이 가능
* request : 128Mi, limits : 256Mi를 설정했다면 '최소한 128Mi의 메모리 사용은 보장되지만, 유휴 메모리 자원이 있다면 최대 256Mi 까지 사용 가능'

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: memory-demo-2
spec:
  containers:
  - name: stress1
    image: polinux/stress
    resources:
      requests:
        cpu: "1"
        memory: "128Mi"
      limits:
        cpu: "2"
        memory: "256Mi"
    command: ["stress"]
    args: ["--vm", "1", "--vm-bytes", "150M", "--vm-hang", "1"]
  - name: stress2
    image: polinux/stress
    resources:
      requests:
        cpu: "1"
        memory: "128Mi"
      limits:
        cpu: "2"
        memory: "256Mi"
    command: ["stress"]
    args: ["--vm", "1", "--vm-bytes", "150M", "--vm-hang", "1"]
```

![리소스 합](https://lh3.googleusercontent.com/pw/AM-JKLVKD6nDNMuu0vn6tACDC2ngwU_SgtqdrIibsrGuozmgCFX3OCm_14qxDDEiOSr4Fw5_GtgY0hJoWUA7KI8Z928CODfTkaG6IpmFQ5_kpjzqbnisbk9abyonFuhrGqd4lsk54WIbOfY-DzeueS2__dmiZg=w1806-h276-no?authuser=0)

> [1Mib? 1MB?](https://brunch.co.kr/@leedongins/133)

* cpu 설정은 코어수가 아닌 cpu-share 방식
* cpu-share 비율에 따라 사용
* (reqeust에 설정된 CPU 밀리코어 값 * 1024) / 1000 = CPU Share 값
* 1 = 1000m
* 쿠버네티스는
* 최대 cpu reqeust 할당 계산 ex
  * node Allocatable cpu(describe 명령어로 확인 가능) : 4
  * 할당 가능한 밀리코어 : 1024 * 4 = 4096
* cpu 같은 경우 압축가능한(compressible)리소스 이기 때문에  throttle이 발생하면서 할당량을 조절하겠지만, 메모리같은경우 압축불가능한(incompressible)리소스이기 때문에 경합이 발생하고 그에따라 우선순위가 낮은 컨테이너 프로세스가 종료됨

# Pod의 환경변수 설정하기

* Pod 실행 시 미리 정의된 컨테이너 환경변수를 변경할 수 있다.
* env 속성을 사용해 설정

# Pod 구성 패턴의 종류

* [첨부링크](https://matthewpalmer.net/kubernetes-app-developer/articles/multi-container-pod-design-patterns.html)
* multi-container pod
  * Sidecar : 메인 어플리케이션에 필수적이지만 어플리케이션에 포함될 필요가 없는 컨테이너 형태 (로깅, 모니터링 등)
  * Adapter : 메인컨테이너의 데이터 출력을 표준화, 외부데이터를 정제하여 메인 컨테이너에게 전달 (외부 모니터링 데이터 전달 등)
  * Ambassador : 컨테이너가 통신할 외부 네트워크를 담당, 외부 데이터를 r/w 할때  대상을 컨테이너 자체 변수가 아닌 Ambassador 에서 담당하여 데이터를 r/w