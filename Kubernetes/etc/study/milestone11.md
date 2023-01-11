# ConfigMap

## ConfigMap 생성

* ConfigMap : 컨테이너 구성 정보를 한곳에 모아서 관리
* 컨테이너 안에서 정보를 관리 한다면 구성정보 관리가 힘들어짐
* key-value 형태

```sh
# 아래와 같이 명령어를 통해서도 생성 가능
kubectl create configmap ttabae-config --from-literal=INTERVAL=2 --from-literal=OPTION=boy --from-file=config.dir
```

* vlaue 사이즈는 1MiB를 초과할 수 없음

## ConfigMap 일부분을 적용하기

* 영상 예제에서는 환경변수 형태로 추가하였기 때문에 configMap을 변경하고 pod를 restart해주어야 한다.
* volume 형태로 추가할 경우 configMap이 업데이트 될 경우 주기적으로 동기화 한다.
* 구성 파일과 소스코드 분리가 가능하다.

```yaml

apiVersion: v1
kind: ConfigMap
metadata:
  name: game-demo
data:
  # 속성과 비슷한 키; 각 키는 간단한 값으로 매핑됨
  player_initial_lives: "3"
  ui_properties_file_name: "user-interface.properties"

  # 파일과 비슷한 키
  game.properties: |
    enemy.types=aliens,monsters
    player.maximum-lives=5    
  user-interface.properties: |
    color.good=purple
    color.bad=yellow
    allow.textmode=true 

---

apiVersion: v1
kind: Pod
metadata:
  name: configmap-demo-pod
spec:
  containers:
    - name: demo
      image: alpine
      command: ["sleep", "3600"]
      env:
        - name: PLAYER_INITIAL_LIVES
          valueFrom:
            configMapKeyRef:
              name: game-demo 
              key: player_initial_lives
        - name: UI_PROPERTIES_FILE_NAME
          valueFrom:
            configMapKeyRef:
              name: game-demo
              key: ui_properties_file_name
```

## ConfigMap 전체를 적용하기

```yaml 
apiVersion: v1
kind: ConfigMap
metadata:
  name: special-config
  namespace: default
data:
  SPECIAL_LEVEL: very
  SPECIAL_TYPE: charm

---

apiVersion: v1
kind: Pod
metadata:
  name: dapi-test-pod
spec:
  containers:
    - name: test-container
      image: k8s.gcr.io/busybox
      command: [ "/bin/sh", "-c", "env" ]
      envFrom:
      - configMapRef:
          name: special-config
  restartPolicy: Never

```

* kubectl exec {pod 이름} -- env : 적용된 환경변수 출력


## ConfigMap의 볼륨으로 적용하기

```yaml

apiVersion: v1
kind: ConfigMap
metadata:
  name: game-demo
data:
  # 속성과 비슷한 키; 각 키는 간단한 값으로 매핑됨
  player_initial_lives: "3"
  ui_properties_file_name: "user-interface.properties"

  # 파일과 비슷한 키
  game.properties: |
    enemy.types=aliens,monsters
    player.maximum-lives=5    
  user-interface.properties: |
    color.good=purple
    color.bad=yellow
    allow.textmode=true 

---

apiVersion: v1
kind: Pod
metadata:
  name: configmap-demo-pod
spec:
  containers:
    - name: demo
      image: alpine
      command: ["sleep", "3600"]
      env:
        # 환경 변수 정의
        - name: PLAYER_INITIAL_LIVES 
          valueFrom:
            configMapKeyRef:
              name: game-demo 
              key: player_initial_lives
        - name: UI_PROPERTIES_FILE_NAME
          valueFrom:
            configMapKeyRef:
              name: game-demo
              key: ui_properties_file_name
      volumeMounts:
      - name: config
        mountPath: "/config"
        readOnly: true
  volumes:
    # 파드 레벨에서 볼륨을 설정한 다음, 해당 파드 내의 컨테이너에 마운트한다.
    - name: config
      configMap:
        # 마운트하려는 컨피그맵의 이름을 제공한다.
        name: game-demo
        # 컨피그맵에서 파일로 생성할 키 배열
        items:
        - key: "game.properties"
          path: "game.properties"
        - key: "user-interface.properties"
          path: "user-interface.properties"
```

# Secret

* ConfigMap과 유사
* base64로 인코딩해서 관리
* 컨테이너가 사용하는 password, auth token, ssh key와 같은 중요한 정보를 저장

* kubectl create secret {Available Commands} {Secret Name} 형태로 생성 가능
* Available Commands에는 docker-registry / generic / tls 가 있음
  * tls 를 사용할 경우 사용할 수 있는 옵션에는 public/private key가 
  들어간다.
  * docker-registry를 사용할 경우 docker registry 인증에 사용되는 유저 정보가 들어감
  * generic같은경우 일반적인 유저 정보가 들어감
* ConfigMap과 동일하게 환경변수 및 volume 형태로 전달 가능하다

* [시크릿 타입](https://kubernetes.io/ko/docs/concepts/configuration/secret/#secret-types)
  * Opaque : 임의의 사용자 정의 데이터
  * kubernetes.io/service-account-token : k8s api서버에 요청을 보내기 위해서 사용하기 위한 secret
  * kubernetes.io/dockercfg :  도커 레지스트리 접속 자격 증명을 저장하기 위한 시크릿, line 전용 docker registry인 [harbor](https://harbor.linecorp.com/)를 사용하기 위해서 등록 필요