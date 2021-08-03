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

