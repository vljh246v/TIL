# Pod 개념 및 사용하기

## Pod란?

* 컨테이너를 표현하는 k8s api의 최소단위

> api 에서 컨테이너 동작은 불가능  
> api에서 컨테이너를 동작하는 api가 pod이고 pod로 컨테이너를 동작시킴  

* pod에는 하나 또는 여러개의 컨테이너가 포함될 수 있음

## Pod 생성하기

* kubectl run 명령으로 생성
* yaml 을 이용해 생성

## Pod 확인

* yaml, json 형태로 확인 가능
* pod가 정상동작하지 않을때 describe 명령어를 통해 확인

## Multi-Container

* pod 내부에 multi 컨테이너가 있다면 get pods 로 확인했을 때 READY 필드에 container 숫자가 표시
* kubectl exec {파드 이름} -c {컨테이너 이름} -it -- /bin/bash 으로 접속하고 싶은 컨테이너 접근 가능
  * ex) kubectl exec multipod -c nginx-container -it -- /bin/bash
* 컨테이너간 ip 주소 동일, 호스트 이름을 pod 이름을 사용

## Pod 동작 flow

* pod 생성 요청 후 문법 확인 및 스캐쥴하기 전까지의 상태를 pending이라고 한다.
* [Pending 상태에 계속해서 멈춰 있다면](https://kubernetes.io/ko/docs/tasks/debug-application-cluster/debug-pod-replication-controller/#%ED%8C%8C%EB%93%9C%EA%B0%80-pending-%EC%83%81%ED%83%9C%EB%A1%9C-%EC%9C%A0%EC%A7%80), 리소스 부족이나 스케줄링을 방해하는 다른 요인이 있다는 뜻이다.
* [컨테이너가 waiting 상태에 계속해서 멈춰 있다면](https://kubernetes.io/ko/docs/tasks/debug-application-cluster/debug-pod-replication-controller/#%ED%8C%8C%EB%93%9C%EA%B0%80-waiting-%EC%83%81%ED%83%9C%EB%A1%9C-%EC%9C%A0%EC%A7%80) 워커 노드에 스케줄 되었지만, 해당 장비에서 사용할 수 없는 경우이다, 가장 일반적으로는 이미지를 가지고 오지 못하는 경우이다.
* [pod의 라이프사이클](https://kubernetes.io/ko/docs/concepts/workloads/pods/pod-lifecycle/)

## Pod 관리하기

* 동작중인 파드 정보 보기
  * kubectl get 또는 describe 명령어 사용
  * kubectl get pods --all-namespaces 옵션으로 전체 파드 확인
* 동작중인 파드 수정
  * kubectl edit 명령어 사용
* 동작중인 파드 삭제
  * kubectl delete 명령어 사용

## Q&A

1. kubectl get pods
2. kubectl get pods --all-namespaces
3. kubectl run nginx-pod --image=nginx --port=80
4. kubectl describe pod nginx-pod
5. kubectl get pods -o wide
6. 5번 답 READY 필드 확인 (1/1)
7. 5번 답 STATUS 필드 확인 (1/1)
8. kubectl describe 명령어에서 Containers-State 항목
9. 현재 준비된 컨테이너 / 전체 컨테이너 (영상에서는 파드라고 알려줌)
10. kubectl delete pod
11. kubectl run redis123 --image=redis123 --dry-run=client -o yaml > redis.yaml
12. kubectl edit pod redis123 명령어 사용