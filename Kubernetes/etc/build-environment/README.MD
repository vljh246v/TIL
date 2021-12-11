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