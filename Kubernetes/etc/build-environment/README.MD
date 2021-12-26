# 1. 개발환경 설치 
## 1.1. Virtualox 설치
*  경로 : https://www.virtualbox.org/wiki/Downloads
## 1.2. Vagrant 설치
* 경로 : https://www.vagrantup.com/downloads
* 프로비저닝을 손쉽게 하기 위한 도구

# 2. Vagrant 구성하고 테스트하기
## 1. 프로비저닝에 필요한 기본 코드 생성
* vagrant init 명령어 입력
```sh
> vagrant init

A `Vagrantfile` has been placed in this directory. You are now
ready to `vagrant up` your first virtual environment! Please read
the comments in the Vagrantfile as well as documentation on
`vagrantup.com` for more information on using Vagrant.

> ls
Vagrantfile
```
> vagrant 명령어
>
> vagrant init : 프로비저닝을 위한 기초 파일을 생성
> 
> vagrant up : Vagrantfile을 읽어 들여 프로비저닝을 진행
>
> vagrant halt : 베이그런트에서 다루는 가성 머신을 종료
>
> vagrant destroy : 베이그런트에서 관리하는 가상 머신을 삭제
>
> vagrant ssh : 베이그런트에서 관리하는 가성 머신에 ssh로 접속
>
> vagrant provision : 베이그런트에서 관리하는 가상 머신에 변경된 설정을 적용


## 2. Vagrantfile을 통한 가상머신 생성
* [VagrantFile](./Vagrantfile)을 원하는 폴더에 넣은 후, vagrant up 실행 

## 3. 노드에 ssh 접속
* VM 에서 생서한 노드를 우측클릭 -> 설정 -> 네트워크 -> 고급 -> 포트포워딩을 선택
* 해당 노드에 호스트 포트를 통해 ssh 접속 
* 호스트 포트가 60010 이라면 ssh -p60010 root@127.0.0.1
* 최초 root 비번은 vagrant

# 4. worker node join 

* join.sh 를 열어 join에 필요한 정보를 출력하여 각 worker node 에 입력

```sh
[root@k8s-master ~]# cat ~/join.sh
kubeadm join 192.168.56.10:6443 --token 47x98p.i2d2z958nrfy1kqn --discovery-token-ca-cert-hash sha256:6b6205c7878151b379180e74437b50c1c58c78d3397522a8c244fb191dfab94d

[root@k8s-node1 ~]# kubeadm join 192.168.56.10:6443 --token 47x98p.i2d2z958nrfy1kqn --discovery-token-ca-cert-hash sha256:6b6205c7878151b379180e74437b50c1c58c78d3397522a8c244fb191dfab94d
```


# 5. node 연결 확인

```sh
[root@k8s-master ~]# kubectl get nodes
NAME         STATUS   ROLES                  AGE     VERSION
k8s-master   Ready    control-plane,master   2d12h   v1.22.4
k8s-node1    Ready    <none>                 2d11h   v1.22.4
```
# 98. dockersim deprecated?!

* [참고1](https://kubernetes.io/blog/2020/12/02/dont-panic-kubernetes-and-docker/)
* [참고2](https://kubernetes.io/docs/setup/production-environment/container-runtimes/#containerd)
* [참고3](https://github.com/kubernetes/kubernetes/blob/master/CHANGELOG/CHANGELOG-1.20.md#deprecation)
* [containerd git repo](https://github.com/containerd/containerd)

![dockershim](https://i1.wp.com/www.opennaru.com/wp-content/uploads/2019/07/Containerd_version.png?fit=1482%2C837)


* **kubectl** 을 통해 컨테이너 생성 명령어를 master node의 **api server** 와 통신을 함
* 그 후 worker node 마다 하나씩 설치된 **kubelet** 라는 컴포넌트에 명령을 전달
* **kubelet**은 컨테이너 생성 명령을 내리는데, 이때 **kubelet**은 **컨테이너 런타임 인터페이스(CRI)** 규약을 따르는 **containerd**에 명령을 내림 
* 문제는 docker 가 CRI 규격을 준수하지 않음
* 중간에 **Dockershim** 이라는 컴포넌트를 두고 명령을 받음
* 1.24 버전에 Dockershim이 제거될 예정
* 다만 도커를 통해 만든 build된 image는 OCI(Open Container Initiative) 형태이기 때문에 문제될 것이 없음
* 결론은 CRI인터페이스 규격을 준수하여 kubelet 명령어를 받아들일 수 있는 런타임 서비스가 필요함
* Dockershim이 Deprecate 예정이어서 다른 런타임 서비스를 설정해야 함!


# 99. dockersim deprecated를 대비한 containerd 로 마이그레이션

* 마스터 노드 부터 진행

## Cordon and Drain node
* 마스터 노드만 진행
* 스케줄링 정지를 위해 cordon 설정과 drain 설정 진행

```sh
> kubectl cordon {노드 이름}
>
> ex)
> 
> kubectl cordon k8s-master
>
> kubectl drain {노드 이름} --ignore-daemonsets
>
> ex)
> 
> kubectl drain k8s-master --ignore-daemonsets
```

* drain 설정은 추가해둔 dashboard pod들 때문에 실패할수있음
* 아래 명령어로 dashboard 와 관련된 오브젝트들을 삭제
* kubectl delete -f https://raw.githubusercontent.com/kubernetes/dashboard/master/aio/deploy/recommended.yaml
* 그리고 다시 drain 실행

## Stop services

```sh
> systemctl stop kubelet
>
> systemctl stop docker
```

## 도커 삭제 (옵셔널)


```sh
> yum remove docker-ce docker-ce-cli
```

## containerd 설정 변경
* k8s의 container runtime tool을 containerd로 변경

* config.toml이 없다면 아래 명령어를 통해 생성
```sh
sudo mkdir -p /etc/containerd
containerd config default | sudo tee /etc/containerd/config.toml
```

* systemd cgroup 드라이버를 runc와 함께 사용하려면 아래와 같이 수정

```sh
# /etc/containerd/config.toml
[plugins."io.containerd.grpc.v1.cri".containerd.runtimes.runc]
  ...
  [plugins."io.containerd.grpc.v1.cri".containerd.runtimes.runc.options]
    SystemdCgroup = true
```

## containerd restart

systemctl restart containerd

## Change runtime

* /var/lib/kubelet/kubeadm-flags.env 파일을 편집
* 기본 설정은 KUBELET_KUBEADM_ARGS="--network-plugin=cni --pod-infra-container-image=k8s.gcr.io/pause:3.6" 으로 되어 있음
* 해당 내용을 아래와 같이 변겨 
```sh
KUBELET_KUBEADM_ARGS="--network-plugin=cni --pod-infra-container-image=k8s.gcr.io/pause:3.6 --cgroup-driver=systemd --container-runtime=remote --container-runtime-endpoint=unix:///var/run/containerd/containerd.sock"
```

## Start kubelet
```sh
> systemctl start kubelet 
```

## Node Cordon and Drain 해제

* 마스터 노드만 진행
```sh
> kubectl uncordeon {노드 이름}
> 
> ex)
>
> kubectl uncordon k8s-master
```

## 