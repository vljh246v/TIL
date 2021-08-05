# 9 퍼시스턴트 볼륨(PV)과 퍼시스턴트 볼륨 클레임(PVC)

- 상태가 있는 애플리케이션의 경우 데이터 저장이 필요함
- 포드에 저장시 포드가 삭제된다면 그와 동시에 데이터도 삭제됨
- 워커 노드 중 하나에 저장을 하거나 호스트에 위치한 디렉토리에 저장할 수도 있지만 문제점이 많음
- 이를 해결하기 위해 일반적으로 어느 노드에서도 접근해 사용할 수 있는 퍼시스턴트 볼륨을 사용
- 퍼시스턴트 볼륨은 워커 노드들이 네트워크상에서 스토리지를 마운트해 영속적으로 데이터를 저장할 수 잇는 볼륨을 의미

## 9.1 로컬 볼륨 :  hostPath, emptyDir

- hostPath는 호스트와 볼륨을 공유하기 위해서 사용하고, emptyDir은 포드의 컨테이너 간에 볼륨을 공유하기 위해 사용

### 9.1.1 워커 노드의 로컬 디렉터를 볼륨으로 사용 : hostPath

- 아래는 호스트와 디렉터리를 공유하는 포드를 새성하는 예제이다.

```YAML
apiVersion: v1
kind: Pod
metadata:
  name: hostpath-pod
  labels:
    name: hostpath-pod
spec:
  containers:
  - name: my-container
    image: busybox
    args: ["tail", "-f", "/dev/null"]
    volumeMounts:
      - mountPath: /etc/data
        name: my-hostpath-volume
    resources:
      limits:
        memory: "128Mi"
        cpu: "500m"
    ports:
      - containerPort: 80
  volumes:
    - name: my-hostpath-volume
      hostPath:
        path: /Users/jaehyun/Documents/workspace/etc

```

- spec.volumes[0].hostPath 항목을 정의해서 호스트의 디렉터리를 포드의 /etc/data에 마운트함
- 포드에서 /etc/data 디렉터리에 파일을 생성하면 호스트의 디렉터리에 파일을 확인할 수 있음

  ![mydata](https://lh3.googleusercontent.com/pw/AM-JKLXuH2yQBhTRzi5bVjSXClfFQbC3TpdktJ1gebapu9mEz-T_m7PPjB0BgKuSOWHOxdPZbg5CGMD-hrP9YHxyiyxgSVtDIbWf58oqwfs32Pn2moRGeup2Q_8oD361JTP3nWc4QVNU0ej-XuB7jQQW0aaqvg=w842-h272-no?authuser=0)

- 그렇지만 이러한 데이터 저장 방식은 디플로이먼트에 장애가 생겨서 다른 노드로 포드가 옮겨갈 경우 데이터를 사용할 수 없음
- 하지만 모니터링 툴과 같은 모드 워커에 배포가 일어나는 특수한 상황에서는 사용할 수 있음

### 9.1.2 포드 내의 컨테이너 간 임시 데이터 공유 : emptyDir

- emptyDir 볼륨은 외부 볼륨을 사용하는것이 아닌, 포드가 실행되는 도중에만 각 컨테이너가 함께 사용할 수 있도록 임시 저장 공간 생성
- 아래는 아파치  웹 서버를 실행하는 포드를 생성하는 예제이다.

```YAML
apiVersion: v1
kind: Pod
metadata:
  name: emptydir-pod
  labels:
    name: emptydir-pod
spec:
  containers:
  - name: content-creator
    image: alicek106/alpine-wget:latest
    args: ["tail", "-f", "/dev/null"]
    volumeMounts:
      - mountPath: /data                        # 해당 컨테이너가 /data 에 파일을 생성하면
        name: my-emptydir-volume

  - name: apache-webserver
    image: httpd:2
    args: ["tail", "-f", "/dev/null"]
    volumeMounts:
      - mountPath: /usr/local/apache2/htdocs/   # 아파치 웹 서버에서 접근할 수 있음
        name: my-emptydir-volume

  volumes:
    - name: my-emptydir-volume
      emptyDir: {}                              # 포드 내에서 파일을 공유하는 emptydDir
```

- content-creator 컨테이너에서 웹 컨텐츠를 생성하면  apache-webserver 컨테이너 htdocs 디렉터리에도 동일한 웹 컨텐츠가 생성 됨

## 9.2 네트워크 볼륨

- 쿠버네티스틑 별도의 플러그인을 설치하지 않아도 다양한 종류의 네트워크 볼륨을 포드에 마운트할 수 있다.

### NFS를 네트워크 볼륨으로 사용하기

- NFS는 NFS 클라이언트와 NFS 서버로 구성
- 클라이언트는 워커 노드의 기능을 사용할
- 아래는클러스터 내부에 임시 NFS 서버를 생성하는 예제이다.

```YAML
# nfs-deployument.yaml

apiVersion: apps/v1
kind: Deployment
metadata:
  name: nfs-server
spec:
  selector:
    matchLabels:
      role: nfs-server
  template:
    metadata:
      labels:
        role: nfs-server
    spec:
      containers:
      - name: nfs-server
        image: gcr.io/google_containers/volume-nfs:0.8
        ports:
          - containerPort: 2049
            name: nfs
          - containerPort: 20048
            name: mountd
          - containerPort: 111
            name: rpcbind
        securityContext:
          privileged: true

---

# nfs-service.yaml

apiVersion: v1
kind: Service
metadata:
  name: nfs-service
spec:
  selector:
    role: nfs-server
  ports:
  - port: 2049
    name:  nfs
  - port: 20048
    name:  mountd
  - port: 111
    name:  rpcbind

```

- 아래는 NFS 서버의 볼륨을 마운트한 포드 예제이다.

```YAML
apiVersion: v1
kind: Pod
metadata:
  name: nfs-pod
spec:
  containers:
  - name: nfs-mount-container
    image: busybox
    args: ["tail", "-f", "/dev/null"]
    volumeMounts:
      - mountPath: /mnt                 # 포드 컨테이너 내부의 /mnt 디렉터리에 마운트
        name: nfs-volume
  volumes:
    - name: nfs-volume
      nfs:                              # NFS 서버의 볼륨을 포드의 컨테이너에 마운트
        path: /
        server: {NFS_SERVICE_IP}
```

- 컨테이너 내부에서 /mnt 디렉터리에 저장하면 실제 NFS 서버에 저장됨
- {NFS_SERVICE_IP} 내용에는 서비스의 DNS 이름이 아닌 NFS 서버의 IP가 설정돼야함
- 이유는 컨테이너 내부에서는 DNS를 통해 찾을 수 있지만 NFS 볼륨의 마운트는 컨테이너가아닌 노드에서 발생함
- ip를 확인해서 적용하는 방식으로 진행해야 한다.

```sh
# NFS 서버에 접근하기 위해 서비스의 Cluster IP를 얻음
export NFS_SERVICE_IP=$(kubectl get svc/nfs-service -o jsonpath='{.spec.clusterIP}')

# nfs-pod의 server 항목을 NFS_SERVICE_IP로 교체해 생성
cat nfs-pod.yaml | sed "s/{NFS_SERVICE_IP}/$NFS_SERVICE_IP/g" | kubectl apply -f -
```

- 포드가 정상적으로 실행 =됐는지 확인하면 저장 공간을 마운트해 사용하고 있는 것을 볼 수 있다.

  ![NFS](https://lh3.googleusercontent.com/pw/AM-JKLVlTgHK_7nAuDjiDFNrP-0s_i_Wc6cAugUqzDB59M-ntEF8jXnpPsHQe3RBPKht-rJqBjIvDv5PnxVmAcC2eJ49LKvvzHhU3yU-BpyQ2S917Rcitppo4lpsH2eYcx1MHJ33e-NII56lW6O4C9KwfHt2IQ=w1012-h326-no?authuser=0)

## 9.3 PV, PVC를 이용한 불륨 관리

### 9.3.1 퍼시스턴트 볼륨과 퍼시스턴트 볼륨 클레임을 사용하는 이유

- 실제 어플리케이션 개발시에는 볼륨에 대한 내용을 YAML 파일에 직접 입력
- 여러 종류의 NFS 를 사용하기도 하고, 디플리오먼트를 다른 개발 부서에 배포하거나 웹상에 공개할 경우 고정된 NFS 정보는 문제가 됨
- 볼륨과 애플리케잉션의 정의사 서로 밀접하게 연관돼 있어 분리할 수 없는 상황이 생김
- 이러한 불편함을 해결하기 위해 퍼시스턴트 볼륨(Persistent Volume : PV) 과 퍼시스턴트 볼륨 클레임(Persistent Volume Claim : PVC)이라는 오브젝트를 사용
- 포드가 볼륨의 세부적인 사항을 몰라도 볼륨을 사용할 수 있도록 추상화 해주는 역할을 함

- 인프라 관리자와 사용자가 나눠져 있다면 아래와 같은 과정을 거침
  1. 인프라 관리자는 네트워크 볼륨의 정보를 이용해 퍼시스턴트 볼륨리소스를 미리 생성해 둠. 네트워크 볼륨의 정보에는 NFS나 iSCSI와 같은 스토리지 서버에 망누트하기 위한 엔트포인트를 포함할 수 있음
  2. 사용자는 포드를 정의하는 YAML 파일에 **'이 포드는 데이터를 영속적으로 저장해야 하므로 마운트할 수 있는 외부 볼륨이 필요하다'**라는 의미의 퍼시스턴트 볼륨 크렐임을 명시하고 생성
  3. 쿠버네티스틑 생성해둔 퍼시스턴트 볼륨의 속성과 사용자가 생성한 퍼시스턴트 볼륨클레임의 요구 사항이 일치한다면 두 개의 리소스를 매칭 시켜 바인딩함

- 위 과정에서 중요한 점은 **'사용자는 디플로이먼트의 YAML 파일에 볼륨의 상세한 스펙을 정의하지 않아도 된다.'**는 것
- 디플로이먼트는 외부 볼륨을 필요로 한다는 사실만 포함해 배포함

### 9.3.2 퍼시스턴트 볼륨과 퍼시스턴트 볼륨 클레임 사용하기

- 퍼시스턴트 볼륨과 클레임 오브젝트는 각각 pv, pvc 라는 이름으로 줄여서 사용한다.

- 아래는 이전에 만들었던 nfs를 사용해 pv를 작성한 예제이다.

```YAML
apiVersion: v1
kind: PersistentVolume
metadata:
  name: local-pv
spec:
  capacity:
    storage: 5Gi                              # 이 볼륨의 크기
  volumeMode: Filesystem
  accessModes:
    - ReadWriteOnce                           # 하나의 포드에 의해서만 마운트 될 수 있음
  persistentVolumeReclaimPolicy: Recycle
  storageClassName: slow
  mountOptions:
    - hard
    - nfsvers=4.1
  nfs:
    path: /
    server: 10.105.173.246
```

- kubectl get pv 명령어로 확인해보면 정상적으로 생성된 것을 확인할 수 있다.
- 아래는  개발자의 입장에서 PersistentVolumeClaim 과 Pod를 함계 생성한 예제이다.

```YAML
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: my-local-pvc              # 1. my-local-pvc 라는 이름의 pvc를 생성
spec:
  resources:
    requests:
      storage: 5Gi                # 2.2 볼륨크기가 최소 5Gi 인 퍼시스턴트 볼륨과 연결
  accessModes:
    - ReadWriteOnce               # 2.1 속성이 ReadWriteOnce인 퍼시스턴트 볼륨과 연결
  storageClassName: "slow"

---

apiVersion: v1
kind: Pod
metadata:
  name: local-mount-container
  labels:
    name: local-mount-container
spec:
  containers:
  - name: local-mount-container
    image: busybox
    args: ["tail", "-f", "/dev/null"]
    volumeMounts:
      - mountPath: /mnt
        name: local-volum
    resources:
      limits:
        memory: "128Mi"
        cpu: "500m"
    ports:
      - containerPort: 80
  volumes:
    - name: local-volum
      persistentVolumeClaim:
        claimName: my-local-pvc    # 3. my-local-pvc라는 이름의 pvc 사용
```
- 정상적으로 연결 후 pod도 정상 Running 상태인 것을 볼 수 있다.

  ![pod](https://lh3.googleusercontent.com/pw/AM-JKLXFP8sV92Htx5gPkF5d6Dx-9ESjYvqT-gT88fmKdTJL2h4sg2zv89K5jiEefDJY_q2CeQ9X2zJgyfV7To4cAVYFTj-um7z4K_5jt9YDlVIowGzLct5on0xrLyNBrLB2ftj6nIMAjJO8AzODzc_pXEDsRQ=w1680-h420-no?authuser=0)

- 퍼시스턴트 볼륨의 사용 방법을 정리해 보면
  1. nfs를 쿠버네티스에서 퍼시스턴트 볼륨으로 등록, 또한 볼륨의 읽기 및 쓰기 속성(accessModes), 볼륨의 크기를 별도로 설정
  2. 퍼시스턴트 볼륨 클레임을 먼저 정의 후, 퍼시스턴트 볼륨 클레임에는 원하는 볼륨의 조건을 나열(크기 : 5Gi, accessModes : ReadWriteOnce)
  3. 퍼시스턴트 불륨 클레임과 퍼시스턴트 볼륨 조건이 일치하기 때문에 두 리소스가 연결, kubectl get pv,pvc 확인시에 리소스 상태가 연결(Bound) 상태로 바뀐 것을 확인 할 수 있음
  4. 정의한 pod 에서는 퍼시스턴트 볼륨 클레임을 사용하도록 설정

### 9.3.3 퍼시스턴트 볼륨을 선택하기 위한 조건 명시

- 사용하는 볼륨의 크기가 애플리케이션에 필요한 최소한의 조건인지, 여러 개의 포드에 의해 마운트 가능한지, 읽기 전용인지 등의 조건은 쿠버네티스에게 알려 줄 필요가 있다.

#### accessModes와 볼륨 크기, 스토리지클래스, 라벨 셀렉터를 이요한 퍼시스턴트 볼륨 선택

- accessModes는 볼륨에 대해 읽기 및 스기 작업이 가능한자, 여러개의 인스턴스에 의해 마운트 될 수 있는지 등을 의미
  ![옵션](https://lh3.googleusercontent.com/pw/AM-JKLXcCr8ZtCBkVjTnm3McLrsaaF7ro4edXsvQaCtZJzxYi9qnWBA0hbgW11jiJRVrnIvAiAFoNyEwL07IuaX3QZfKxHf__ghHLwPEw9zg6ugsDNgtGWQn7Iuv9K5Vw4Q9chdncflLh0Ar9Uy2nqkd7aaA7w=w1680-h408-no?authuser=0)

- 또한 자주 사용되는 조건으로는 볼륨의 크기가 있음
- spec.resources.requests.storage 항목
- 단, accessModes모드나 볼륨의 크기는 해당 볼륨의 메타데이터일 뿐, 볼륨이 정말로 그러한 속성을 가지도록 강제하지는 않음
- 예를 들어 퍼시스턴트 볼륨 설정에서 spec.capacity.storage 에 크기를 설정 한다고 해서 해당 크기의 파티션이 생기는건 아님
- 적절한 볼륨을 찾아주는 라벨 정도로 생각

### 9.3.4 퍼시스턴트 볼륨의 라이프사이클과 Reclaim Policy

- 퍼시스턴트 볼륨을 갓 생성하면 Status 항목이 Avaliable 로 설정됨
- 퍼시스턴트 볼륨 클레임을 새로 생성해 바인드 하면 STATUS 항목이 Bound로 바뀜
- 퍼시스턴트 볼륨 클레임을 삭제했을 때, 퍼피스턴트 볼륨의 데이터를 어떻게 처리할 것인지 별도로 정의 가능, 이를 쿠버네티스에서는 Reclaim Policy 라고 부름
- Reclaim Policy 에는 크게 Retain, Delete, Recycle가 있음
  - Retain
    - 퍼시스턴트 볼륨의 사용이 끝난 뒤에도 스토리지에 저장된 데이터를 계속해서 보존 가능
    - Avaliable -> (pvc 생성) -> Bound -> (pvc 삭제) -> Retain
  - Delete
    - 퍼시스턴트 볼륨의 사용이 끝나고 나면 퍼시스턴트 볼륨이 삭제
    - 파일도 모두 유실
  - Recycle
    - 데이터를 모두 지운 뒤에 다시 Avaliable 상태로 만들어 줌
    - 다만 해당 상태는 Deprecated 됨