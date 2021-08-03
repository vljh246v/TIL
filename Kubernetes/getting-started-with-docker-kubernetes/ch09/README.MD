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

