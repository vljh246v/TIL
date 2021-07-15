# 2. 도커 엔진

## 2.1 도커 이미지와 컨테이너

- 도커 엔진에서 사용하는 기본 단위는 이미지와 컨테이너

### 2.1.1 도커 이미지

- 이미지는 컨테이너를 생성할 때 필요한 요소이며, 가상 머신을 생성할 때 사용하는 ISO 파일과 비슷한 개념
- 여러개의 계층으로 된 바이너리 파일이 존재
- 이미지 이름은 기본적으로 [저장소 이름]/[이미지 이름]:[태그] 형태로 구성
  - 저장소 이름은 이미지가 저장된 장소를 의미
  - 이미지 이름은 해당 이미지가 어떤 역할을 하는지 나타냄
  - 태그는 이미지의 버전 관리, 혹은 리비전 관리에 사용

### 2.1.2 도커 컨테이너

- 도커 이미지는 각종 애플리케이션 및 여러 분석 도구까지 존재함
- 이러한 이미지로 컨테이너를 생성하면 해당 이미지의 목적에 맞는 파일이 들어 있는 파일시스템과 격리된 시스템 자원 및 네트워크를 사용할 수 있는 독립된 공간이 생성
- 이미지를 읽기 전용으로 사용하되 이미지에서 변경된 사항만 컨테이너 계층에 저장하므로 컨테이너에서 무엇을 하든지 원래 이미지는 영향을 받지 않음
- 생성된 각 컨테이너는 각기 독립된 파일시스템을 제공 받으며 호스트와 분리돼 있으므로 다른 컨테이너와 호스트는 변화가 없음

## 2.2 도커 컨테이너 다루기

### 2.2.1 컨테이너 생성

- 도커 엔진 버전 확인

```sh
# docker -v
```

- docker run 명령어는 컨테이너를 생성하고 실행하는 역할을 함
- -i, -t 옵션은 컨테이너와 상호 입출력을 가능하게 함

```sh
# docker run -i -t ubuntu:14.04
```

- 해당 명령어를 입력하면 도커 허브에서 자동으로 이미지를 내려받는다.
- -i 옵션으로 상호 입출력, -t 옵션으로 tty를 활성화 해서 배시 셸을 사용하도록 컨테이너를 설정
- 호스트와 파일시스템은 서로 독립적이므로 ls 명령어로 파일시스템을 확인해보면 아무것도 설치되지 않은 상태임을 확인할 수 있음
- docker pull 명령어를 사용해서 이미지를 내려받을 수 있다.

```sh
# docker pull centos:7
```

- docker images 명령어를 활용해 도커 엔진에 존재하는 이미지의 목록을 출력함
- 컨테이너를 생서할때 run 명령어가 아닌 create 명령어를 사용할 수도 있음
- --name 옵션에는 컨테이너 이름을 설정할 수 있음

```sh
# docker create -i -t --name mycentos centos:7
```

- 위 명령어 같은 경우 run 명령어와 달리 컨테이너 내부로 들어가지 않는데, 이는 create ㅁ여령어는 생성만 함
- docker start 명령어로 컨테이너를 시작하고 docker attach 를 사용해서 컨테이너 내부로 들어감

```sh
# docker start mycentos
# docker attach mycentos
```

- exit 명령어가 아닌 Ctrl + P, Q 를 입력해서 컨테이너를 정지시키지 않고 컨테이너를 빠져나올 수 있다.

### 2.2.2 컨테이너 목록 확인

- docker ps 명령어로 정지되지 않은 컨테이너 목록을 확인할 수 있다.
- 정지된 모든 컨테이너를 출력하려면 -a 옵션을 추가한다.
- Exited는 정지된 상태, Up .. seconds는 실행중인 상태
  ![docker-ps-a](https://lh3.googleusercontent.com/pw/AM-JKLUXC7GiVHbiC0GoR-fF5fxKDheqQQcyWKZuV2sbyYIjosx3sjKPD-GtUwZwtkZOirx_kfJ79xPMnV6T5z7fA3GgvFAnyQox3-e_leQFVfnpd6TtrNCgWC4FBWRVwI8UQdrE6TQUCEtFjDd8nMn5W0JP3w=w976-h93-no?authuser=0)

  - CONTAINER ID : 컨테이너에게 자동으로 할당되는 고유한 ID
  - IMAGE : 컨테이너를 생성할 때 사용한 이미지의 이름
  - COMMAND : 커맨드는 컨테이너가 시작될 때 실행될 명령어
  - CREATED : 컨테이너가 생성되고 난 뒤 흐른 시간
  - STATUS : 컨테이너의 상태, Up 실행중, Exited 는 종료, Pause 는 일시중지
  - PORTS : 컨테이너가 개방한 포트와 호스트에 연결한 포트를 나열
  - NAMES : 컨테이너의 고유한 이름

### 2.2.3 컨테이너 삭제

- docker rm 명령어를 사용해 컨테이너를 삭제할 수 있음
  ![docker rm](https://lh3.googleusercontent.com/pw/AM-JKLU213JyQavfBTXPWmRYcJJ5zUgyYj5J_bDCTovQ6VguqQOt0UmbbgunE27YHcjp3wk5A2gazxELlioUMLZG4cJGfjQvYtkN-1rnaW49Md1XBYvAvTRyjj01lFnnP6W3MCf3l4-DJIj1WoF7xV13cGIp8g=w972-h186-no?authuser=0)

- 하지만 실행중인 컨테이너는 docker stop 명령어를 통해 정지 후 삭제를 하거나 -f 옵션을 사용해 강제로 삭제할 수 있음
- docker container prune 명령어를 통해 모든 컨테이너를 삭제할 수 있음

### 2.2.4 컨테이너 외부 노출

- 컨테이너는 가상 머신과 마찬가지로 가상 ip 를 할당받음
- 컨테이너를 새롭게 생성한 후 ifconfig 명령어로 컨테이너의 네트워크 인터페이스를 살펴보자
  ![network_test](https://lh3.googleusercontent.com/pw/AM-JKLXsLlx74Sa7i5gNYXWORtBWjCQ5RyNgQj-L4fYY4vJbZRZmkJH-X5iUNm_4wlfsNzKHVMJkhVMfRAe-I2hCvR4b_on8P-HGB2YAn79VxRazM3ipD-lwUvOw0V1F8xwdy-08UlYWodQWERhobytFBVIHFA=w495-h241-no?authuser=0)
- eth0 인터페이스와 로컬호스트 lo 인터페이스가 있음
- 아무런 설정이 없다면 이 컨테이너는 외부에서 접근할 수 없으며 도커가 설치된 호스트에서만 접근할 수 있음
- 외부에 컨테이너의 애플리케이션을 노출하기 위해서는 eth0 의 ip와 포트를 호스트의 ip와 포트에 바인딩해야 함
- 아래와 같이 -p 옵션을 통해 컨테이너의 포트를 호스트의 포트와 바인딩해 연결할 수 있게 설정함

```sh
# docker run -i -t --name mywebserver -p 80:80 ubuntu:14.04
```

- -p 옵션 입력 형식은 [호스트의 포트]:[컨테이너의 포트] 이다
- 다음 명령어로 아파치 웹서버를 설치할 수 있다.

```sh
root@33e74681cba5:/# apt-get update
root@33e74681cba5:/# apt-get install apache2 -y
root@33e74681cba5:/# service apache2 start
```

- 실제로 아파치 서버가 설치된 것은 컨테이너 내부이므로 호스트에는 어떠한 영향도 주지 않습니다.

### 2.2.5 컨테이너 애플리케이션 구축
