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

- 서비스를 컨테이너화 할 때 여러개의 애플리케이션을 한 컨테이너에 설치할 수도 있음
- 그러나 컨테이너에 애플리케이션을 하나만 동작시키면 독립성과 버전관리, 소스코드 모듈화 등이 더욱 쉬워짐
- 구분해서 관리하는것이 도커에서 공식적으로 권장하는 구조
- 데이터베이스, 워드프레스 웹 서버 컨테이너를 연동해 예제 애플리케이션을 만들어 보자

```sh
docker run -d \
--name wordpressdb \
-e MYSQL_ROOT_PASSWORD=password \
-e MYSQL_DATABASE=wordpress \
mysql:5.7

docker run -d \
-e WORDPRESS_DB_PASSWORD=password \
--name wordpress
--link wordpressdb:mysql \
-p 80 \
wordpress
```

- 워드프레스 웹 서버 컨테이너 옵션에 -p 80을 입력했으므로 호스트의 포트 중 하나와 컨테이너의 80포트가 연결됨
- docker -ps 명령어로 호스트의 어느 포트와 연결 되었는지 확인 가능
  ![docker-ps](https://lh3.googleusercontent.com/pw/AM-JKLXdK2weo9tD7JUbX4vgfWbbJJQ0kYoeO0bPI5mfgxSSJnomP9x6kJi_FCKwokxbnIpUH4ScAJKmFDiV3aOmrb3G_L_sFnPJRJvkK2yuOiXxDmpZI1-EAaRg0fPXDJc919G-lttxWJxfhKfxelu-vEdXvg=w875-h88-no?authuser=0)

- -d 옵션
  - -t, -i가 attach 가능한 상태로 설정한다면 -d는 Detached 모드로 컨테이너를 실행
  - Detached 는 컨테이너를 백그라운드에서 동작하는 애플리케이션으로 설정함
  - 우분투나 CentOS 컨테이너같은경우 -i, -t 옵션으로 run을 실행하면 표준 입출력이 활성화된 셀 환경을 사용할 수 있음
  - 대부분의 기본 이미지들은 컨테이너를 시작할 때 /bin/bash를 커맨드로 설정해 실행함으로써 배시 셸을 쓸 수 있게 설정함
  - 그러다 -d 옵션으로 run을 실행하면 입출력이 없는 상태로 컨테이너를 실행
  - 컨테이너 내부에서 프로그램이 터미널을 차지하는 포그라운드로 실행되 사용자 입력을 받지 않음
  - Detached 모드인 컨테이너는 반드시 커넽이너에서 프로그램이 실행돼야 함
  - mysql은 하나의 터미널을 차지하는 mysqld를, 워드프레스는 하나의 터미널을 차지하는 apache2-foreground를 실행하므로 -d 옵션을 지정해 백그라운드로 설정한 것
- -e 옵션
  - 컨테이너 내부의 환경변수를 설정
- --link 옵션
  - 컨테이너 간에 이름으로 서로를 찾을 수 있게 도와주는 옵션
  - 하지면 해당 옵션은 DEPRECATED 된 옵션임
  - 도커 브리지 네트워크를 사용하는 것을 권장

### 2.2.6 도커 볼륨

- 도커 이미지로 컨테이너를 생성하면 이미지는 읽기 전용이 되며 컨테이너의 변경 사항만 별도로 저장해서 각 컨테이너의 정보를 보존
- 이전에 만들었던 mysql 컨테이너는 mysql:5.7 이라는 이미지로 생성되었지만 데이터베이스 등의 정보는 컨테이너가 가지고 있음, 이것을 컨테이너 계층(레이어) 라고 부름
- 컨테이너 계층에는 데이터베이스를 운용하면서 쌓이는 데이터가 저장됨
- 문제는 컨테이너를 삭제하면 컨테이너 계층에 존재하는 데이터베이스의 정보도 삭제됨
- 이를 방지하기 위해 컨테이너의 데이터를 영속적 데이터로 활용할 수 있는 방법이 있음
- 가장 간단한 방법이 불륨을 활용하는 것
- 볼륨을 이용한 방법에는 호스트와 볼륨을 공유할 수 있고, 도커가 관리하는 볼륨을 생서할 수도 있으며, 볼륨 컨테이너를 활용할 수도 있음

#### 2.2.6.1 호스트 볼륨 공유

- 아래 명령어를 입력해서 컨테이너를 생성한다.

```sh
docker run -d \
--name wordpressdb_hostvolume \
-e MYSQL_ROOT_PASSWORD=password \
-e MYSQL_DATABASE=wordpress \
-v D:/workspaces/jaehyun/TIL/Kubernetes/getting-started-with-docker-kubernetes/volume:/var/lib/mysql \
mysql:5.7
```

```sh
docker run -d \
-e WORDPRESS_DB_PASSWORD=password \
--name wordpress_hostvolume \
--link wordpressdb_hostvolume:mysql \
-p 80 \
wordpress
```

- -v 옵션이 추가했고, 그 값을 D:/workspaces/jaehyun/TIL/Kubernetes/getting-started-with-docker-kubernetes/volume:/var/lib/mysql 로 설정했음
- 이는 호스트의 D:/workspaces/jaehyun/TIL/Kubernetes/getting-started-with-docker-kubernetes/volume 디렉토리와 컨테이너의 var/lib/mysql 디렉터리를 공유한다는 뜻이다.
- 미리 호스트의 디렉터리를 생성하지 않아도 자동으로 이를 생성함
- 컨테이너를 삭제해도 호스트의 디렉터리는 남아있음
- 이미지에 원래 존재하던 디렉터리에 호스트의 볼륨을 공유하면 컨테이너의 디렉터리 자체가 덮어씌워짐

#### 2.2.6.2 볼륨 컨테이너

- 두번째 방법으로 볼륨을 사용하는 컨테이너를 다른 컨테이너와 공유하는 것
- --volumes-from 옵션을 통해 -v 또는 -volume 옵션을 적용한 컨테이너의 볼륨 디렉터리를 공유할 수 있음

#### 2.2.6.3 도커 볼륨

- 도커 자체에서 제공하는 볼륨기능을 사용하는 것
- 볼륨을 다루는 명령어는 docker volume으로 시작
- 다음 예시는 myvolume이라는 볼륨을 생성함

```sh
docker volume create --name myvolume
```

- docker volume ls 명령어로 볼륨확인이 가능하다.
  ![docker-volume](https://lh3.googleusercontent.com/pw/AM-JKLU20UnMaliECdul-AgKcmmO6ht4tU07IKyI-VULNehWij4arvr7scz4gPt9Ub421aXJeeIbj4A2-Ekh4N78Qy6h8CryWzn5rXWrOClDhmWOt8egEz7ozuVF-cU40Ae4GjUMbkx6-_QmoOqy_YjkhavSYA=w581-h92-no?authuser=0)

- 플러그인 드라이버를 설정해 여러 종류의 스토리지 벡엔드를 쓸 수 있음
- 현재 예제에서는 기본적으로 제공되는 드라이버인 local을 사용
- 이 볼륨은 로컬 호스트에 저장되며 도커 엔진에 의해 생성되고 삭제됨
- -v [볼륨의 이름]:[컨테이너의 공유 디렉터] 명령어 형식을 사용

```sh
docker run -i -t --name myvolume_1 \
-v myvolume:/root/ \
ubuntu:14.04
```

- echo hello, volume! >> /root/volume 명령어를 통해 volume 파일 생성
- 다른 컨테이너도 myvolume 볼륨을 쓰면 볼륨을 활용한 디렉토리에 volume 파일이 존재

```sh
docker run -i -t --name myvolume_2 \
-v myvolume:/root/ \
ubuntu:14.04
```

- cat /root/volume 명령어를 통해 확인
  ![myvolume_2](https://lh3.googleusercontent.com/pw/AM-JKLVhpP1zlj9YQ-gU0T160NWsTMgQ5UQg3ououDMFRKho3FvcYcEXubXvbJVyVgNgwf-6J_g1l_3a5lzFde-5QoY2owv3d5MBNuuOFLCOGkNwCVIpE-P8QsgxANiiIsWrNnChzasqIroINwJbYrw-cM37ng=w279-h94-no?authuser=0)

- docker inspect 명령어를 사용하면 볼륨이 실제 어디에 저장되는지 알 수 있음
  ![docker inspect](https://lh3.googleusercontent.com/pw/AM-JKLUgqOHFBDYpt2RCRjxYBVOtVxLQlJDVhsfMpkt9RUT2p_QXLlJ4ebRDo8ba6TqrQsJQ0ZU-fLByL3ssE0fg88hW8QR4yaXcxgwziAenYo67Dmg2MGg_Th513pRRC99EXBw7vQcWzcCnHiPXIxjr6QWSQg=w487-h211-no?authuser=0)

- Driver 는 볼륨이 쓰는 드라이버를 표시
- Labels는 볼륨을 구분하는 라벨
- Mountpoint는 해당 볼륨이 실제로 호스트의 어디에 저장됐는지는 의미
- -v 옵션을 수행할 때 볼륨 이름을 명시하지 않고 공유할 디렉터리 위치만 입력하면 자동으로 생성함
- 도커 볼륨은 컨테이너를 삭제해도 자동으로 삭제되지 않기 때문에 사용자가 지워줘야함
- docker volume prune 명령어를 사용

- 컨테이너가 아닌 외부에 데이터를 저장하고 컨테이너는 그 데이터로 동작하도록 설계하는 것응ㄹ 스테이트리스 하다고 함
- 컨테이너가 삭제돼도 데이터는 보존되므로 스테이트리스한 설계는 도커를 사용할 때 매우 바람직한 설계

### 2.2.7 도커 네트워크

- 도커는 컨테이너 내부에 ip를 순차적으로 할당, 재시작시 변경될 수 있음
- 컨테이너 내부 ip 를 보면 eth0과 lo 네트워크를 볼 수 있음
  ![ifconfig](https://lh3.googleusercontent.com/pw/AM-JKLU9QxVMhYmgSNdHsToRzLpqQXROcEcra9yr7EhaIzVmCwPzj5Iwg_1uEzZOLdUPScS9fdmT4kTlhMPoElRstfTHXQWw7l0KvR8TsMjWMNycvGiS0si1RysbcMP9DKCd5cPT7dWirtnqlAIi1iRHkXkXOA=w1036-h572-no?authuser=0)
- 이 내부 ip는 도커가 설치된 호스트, 즉 내부 망에서만 쓸 수있는 ip이므로 외부와 연결될 필요가 있음
- 이 과정은 컨테이너가 시작할 때마다 호스트에서 veth로 시작하는 네트워크 인터페이스를 생성함으로써 이뤄짐 (mac, windows는 안보임)
- veth  인터페이스는 사용자가 직접 생성할 필요는 없으며 커넽이너가 생성될 때 도커 엔진이 자동으로 생성
- 각 veth 인터페이스는 각 컨테이너의 eht0 인터페이스와 연결됨
- docker0라는 브릿지도 존재하는데 해당 브리지는 각 veth 인터페이스와 바인딩돼 호스트의 eth0 인터페이스와 이어주는 역할을 함

#### 2.2.7.2 도커 네티워크 기능

- 기본적으로 docker0 브리지를 통해 외부와 통신할 수 있는 환경을 사용할 수 있음
- 사용자의 선택에 따라 여러 네트워크 드라이버를 사용할 수 있음
- 도커 자체적으로 제공하는 네트워크 드라이버로는 브리지, 호스트, 논 컨테이너, 오버레이가 있음
- docker network ls 로 기본적으로 사용 가능한 네트워크 목록을 볼 수 있음

##### 브리지 네트워크

- docker0 브리지가 아닌 사용자가 직접 생성하여 사용할 수 있음
- docker network create --driver bridge {브리지 명} 명령어로 생성 가능
- 컨테이너 생성 시 --net 옵션의 값을 설정하여 해당 네트워크를 사용하게 설정 가능
- 이렇게 생성된 사용자 정의 네트워크는 docker network disconnect, connect  명령어를 통해 자유롭게 붙이고 뗄 수 있음

#### 호스트 네트워크

- 호스트로 설정하면 호스트의 네트워크 환경을 그대로 쓸 수 있음
- --net 옵션에 host를 선택

#### 논 네트워크

- 아무런 네트워크를 쓰지 않는 것을 뜻함

#### 컨테이너 네트워크

- 다른 컨테이너의 네트워크 네임스페이스 환경을 공유할 수 있음
- 공유되는 속성은 내부 ip, 네트워크 인터페이스의 맥(mac) 주소 등이 있음

#### 브리지 네트워크와 --net-alias

- 브리지 타입 네트워크와 --net-alias {호스트명} 옵션을 함께 쓰면 특정 호스트 이름으로 컨테이너 여러 개의 접근할 수 있음
- 그 후 해당 브리지에 포함되는 컨테이너를 만든 후 지정한 호스트명으로 ping 요청을 해보면 라운드로빈 방식으로 호스트들에게 접근하는 것을 볼 수 있음
- --net-alias {호스트명} 옵션 설정시 해당 컨테이너 ipsms 도커에 내장된 DNS에 지정한 호스트 이름으로 등록됨
- 지정한 호스트 명으로 접근하면 라운드 로빈 방식을 이용해 컨테이너의 IP를 반환함

### 2.2.8 컨테이너 로깅

#### 2.2.8.1 json-file 로그 사용하기

- 애플리케이션 레벨에서 로그가 기록할 수 있지만, 컨테이너의 표준 출력(StdOut)과 에러(StdErr) 로그를 별도의 메타데이터 파일로 저장하며 이를 확인하는 명령어를 제공
- 백그라운드 모드로 컨테이너를 생성하는 경우 애플리케이션이 잘 구동되는지 여부를 알 수 없다.
- docker logs 명령어를 사용해 컨테이너의 표준 출력을 확인함으로써 애플리케이션 상태를 알 수 있다.
- -e 옵션을 제거해서 필수 환경변수 없이 mysql 컨테이너를 생성하고, docker logs 명령어를 사용하면 아래와 같이 필수 환경벼수를 입력하라는 에러를 볼 수 있다.
  ![-e](https://lh3.googleusercontent.com/pw/AM-JKLVG2Si8FzUgwUylS1WrtkHJgHetZ5RXXLgPmtU6nlIoyB0HTjf0F7liW0vJHHwpKoXKOm_35DAcFoJzgNuK6gZxg_C7Yl-uPQ1R9hMeKC9Efj4FBqfm4fmBQm2sI1gSXd_r0sOJ2k17ZwqRuV-HNx4BBg=w1578-h348-no?authuser=0)
- 기본적으로 위와 같은 컨테이너 로그는 JSON 형태로 도커 내부에 저장
- json 파일 크기 설정은 --log-opt 옵션을 통해 [설정](https://docs.docker.com/config/containers/logging/configure/) 가능
- 각종 로깅 드라이버를 사용해 컨테이너 로그를 수집할 수 있음(syslog, journald, fluentd, awslogs 등)

#### 2.2.8.2 syslog 로그

- syslog는 유닉스 계열 운영체제에서 로그를 수집하는 오래된 표중 중 하나
- --log-driver=syslog 옵션을 통해 설정 가능

#### 2.2.8.4 fluentd 로깅

- fuentd는 각종 로그를 수집하고 저장할 수 있는 기능을 제공하는 오픈소스 도구

### 2.2.9 컨테이너 자원 할당 제한

- 컨테이너를 생성하는 run, create 명령어에서 컨테이너의 자원 할당량을 조정하도록 옵션을 입력할 수 있음
- 컨테이너에 설정된 자원 제한을 확인하는 가장 쉬운 방법은 docker inspect 명령어

#### 2.2.9.1 컨테이너 메모리 제한

- docker run 명령어에서 --memory옵션을 지정해 컨테이너의 메모리를 제한할 수 있음
- 기본적으로 컨테이너의 Swap 메모리는 메모리의 2배로 설정

#### 2.2.9.2 컨테이너 CPU 제한

##### --cpu-shares

- --cpu-shares옵션은 컨테이너에 가중치를 설정해 컨테이너가 CPU를 상대적으로 얼마나 사용할 수 있는지를 나타냄
- cpu를  한 개씩 할당하는 방식이 아닌, 시스템에 존재하는 CPU를 어느 비중만큼 나눠 쓸 것인지를 명시하는 옵션
- 해당 옵션은 상대적인 값을 가짐, 아무런 설정을 하지 않았을 때 컨테이너가 가지는 값은 1024로 이것은 cpu 할당에서 1의 비중을 뜻함

##### --cpuset-cpus

- 호스트에 여러 개의 cpu가 있을 때 해당 옵션을 통해 컨테이너가 특정 cpu만 사용하도록 설정할 수 있음

##### --cpu-period, --cpu-quota

- 컨테이너의 CFS(Completely Fail Scheduler) 주기는 기본적으로 100ms로 설정되지만  --cpu-period와 --cpu-quota 옵션을 통해 주기를 변경할 수 있음
- --cpu-period=100000 설정은 기본값이며, 이는 100ms 를 뜻함
- --cpu-quota는 -cpu-period에 설정된 시간 중 CPU 스캐줄리에 얼마나 할당할 것인지를 설정함, 만약 -cpu-period=25000 이라면 cpu주기가 1/4로 줄어듬

##### --cpus

- --cpus 옵션은 --cpu-period, --cpu-quota 동일한 기능을 하지만 좀 더 직관적으로 CPU 개수를 직접 지정함

#### 2.2.9.3 Block I/O 제한

- 컨테이너를 생성할 때아무런 옵션도 설정하지 않으면 컨테이너 내부에서 파일을 읽고 쓰는 대역폭에제한이 설정되지 않음
- 단, Direct I/O 의 경우에만 블록 입출력이 제한되며, Bufferd I/O는 제한되지 않음
- --device-write-bps, -- device-read-bps는 각기 쓰고 읽는 작업의 초당 제한을 설정하며, kb/mb/gb 단이로 제한할 수 있음
- --device-write-iops, --device-read-iops 에는 상대적인 값을 입력함

#### 2.2.9.4 스토리지 드라이버와 컨테이너 저장 공간 제한

- 도커 엔진은  컨테이너 내부의 저장 공간을 제한하는 기능을 보편적으로 제공하지는 않지만, 도커의 스토리지 드라이버나 파일 시스템 등이 특정 조건을 만족하는 경우에만 이 기능을 제한적으로 사용 가능
- 대부분 컨테이너 자체가 상태를 가지는 것은 그다비 바람직하지 않음

## 2.3 도커 이미지

- 모든 컨테이너는 이미지를 기반으로 생성
- 기본적으로 도커 허브라는 중앙 이미지 저장소에서 이미지를 내려 받음
- 단 누구나 이미지를 올릴 수 있기 때문에 이미지 사용법을 찾거나 제대로 동작한다는 보장은 없음
- 도커 엔진에서 docker search 명령어를 사용해서 이미지를 찾을 수 있음
  ![docker-search](https://lh3.googleusercontent.com/pw/AM-JKLW_82rGdy6khZQbsM2lfhoair184CKplagULb8RrDu3zqkXMqud-CUu4HEqMk9kWlN9Lv5h3AOJdc3YJzTsZb91Tg8zwtB2fHAJ_NRZSl_92mxYePR-UJF2gmkU5R3dNIkVX8EzYJ_L6HOgFnnA1V_LtQ=w1972-h442-no?authuser=0)

### 2.3.1 도커 이미지 생성

- 아래 명령어를 통해 컨테이너 내부에 first라는 이름의 파일을 하나 생성해 기존의 이미지로부터 변경사항을 만듬

```sh
docker run -i -t --name commit_test ubuntu:14.04
root@ec6060cdc581:/# echo tst_first! >> first
```

- 그후 빠져나와 docker commit 명령어로 컨테이너를 이미지로 만듬
- 옵션은 docker commit [OPTIONS] CONTAINER [REPOSITORY[:TAG]]

```sh
docker commit \
-a "vljh246v" -m "my first commit" \
commit_test \
commit_test:first

sha256:9fc9ce8d1c9689d52fe186e69acd5c0868599121fe3f2fa75a6c271a5ef53e67
```

- 저장소 이름은 입력하지 않아도 상관없지만 이미지의 태그를 입력하지 않으면 자동으로 latest로 설정
- 위 명령어는 이름을 commit_test로, 태그를 first로 설정
- -a 옵션은 author를 뜻하며, 이미지의 작성자를 나타내는 메타데이터를 이미지에 포함
- -m 옵션은 커밋 메시지를 뜻함
- docker images 명령어로 이미지 생성 확인 가능
- commit_test:first 이미지로 새로운 이미지를 생성하는 예제는 아래와 같다.

```sh
docker run -i -t --name commit_test2 commit_test:first
root@c589f35f031e:/# echo test_second! >> second

docker commit \
-a "vljh246v" -m "my second commit" \
commit_test2 \
commit_test:second

sha256:42df2530314241920f75f09c10ab67421ffff8f50291bc61319b738f08cf0b8c
```

  ![commit_test:second](https://lh3.googleusercontent.com/pw/AM-JKLUxwe84HsYrjvHTxfTu594mkV6eGWQu-dRbofWc_d1HvyiqVPLYRDjCy4vxexL7AkIx5ns90nuaHFFDaBO9KVqE6eOipDhZIsgsC6je75JJcgDsD8wySjJzChPF3vX2FP5TsfONpYgASn6IoLZSJ21XWQ=w900-h214-no?authuser=0)

### 2.3.2 이미지 구조 이해

- inspect 명령어로 이미지의 좀 더 자세한 정보를 확인할 수 있다.

```sh
docker inspect ubuntu:14.04
docker inspect commit_test:first
docker inspect commit_test:second
```

- Layers 항목을 보면 아래와 같이 표현된다. 

```json
// ubuntu:14.04
"Layers": [
                "sha256:59199d90878e4fda42bcd8a43bfe3ecd964265b2008746c6b18ae3d6ca659033",
                "sha256:66a563a2553e1b125306d482d02b4579289d982d5e98aef489b3573d6ab4d8b3",
                "sha256:da6f3a71ac1f04c0f6704f7066cbdb68c227368ea60b070cf12b074bc02e5bf9"
            ]

// commit_test:first
"Layers": [
                "sha256:59199d90878e4fda42bcd8a43bfe3ecd964265b2008746c6b18ae3d6ca659033",
                "sha256:66a563a2553e1b125306d482d02b4579289d982d5e98aef489b3573d6ab4d8b3",
                "sha256:da6f3a71ac1f04c0f6704f7066cbdb68c227368ea60b070cf12b074bc02e5bf9",
                "sha256:1c99ba01daf258cdef41d2ad14caae0796b49f05ec390ad45ad5b8ce4894866f"
            ]

// commit_test:second
"Layers": [
                "sha256:59199d90878e4fda42bcd8a43bfe3ecd964265b2008746c6b18ae3d6ca659033",
                "sha256:66a563a2553e1b125306d482d02b4579289d982d5e98aef489b3573d6ab4d8b3",
                "sha256:da6f3a71ac1f04c0f6704f7066cbdb68c227368ea60b070cf12b074bc02e5bf9",
                "sha256:1c99ba01daf258cdef41d2ad14caae0796b49f05ec390ad45ad5b8ce4894866f",
                "sha256:d02e91eb316f153a3deee002a12f070dff87ff7a3d585135e2bb82d5d898b6cf"
            ]
```

- 보는것과 같이 이미지를 커밋할 때 컨테이너에서 변경된 사항만 새로운 레이어로 저장하고, 그 레이어를 포함해 새로운 이미지를 생성함
- 그렇기 때문에  commit_test:second 이미지의 실제 크기는 ubuntu:14.04 + commit_test:first + commit_test:second 파일 크기임
- 이미지 삭제는 docker rmi [이미지 이름] 형태입니다.
- 다만 commit_test:first 를 삭제한다고 가정하면, commit_test:first를 기반으로 한 commit_test:second가 존재하기 때문에 레이어에 부여된 commit_test:first라는 이름만 삭제하는 것임
- commit_test:second 까지 삭제를 즉 해당 레이어를 부모로 가지는 이미지가 없을때 이미지가 삭제됨

### 2.3.3 이미지 추출

- docker save 명령어로 컨테이너의 커맨드, 이미지 이름, 태그 등 모든 메타데이터를 포함하여 하나의 파일로 추출 가능
- docker load 명령어로 불러올 수 있음
- export, import 명령어도 존재

### 2.3.4 이미지 배포

- 도커 허브를 통해 가능
- 도커 허브는 도커 이미지를 저장하기 위한 클라우드 서비스
- 사설 레지스트리를 사용 가능

## 2.4 Dockerfile

### 2.4.1 이미지를 생성하는 방법

- 개발한 애플리케이션을 컨테이너화할 때 가장 먼저 생각나는 방법은 아래와 같음
  1. 아무것도 존재하지 않는 이미지로 컨테이너 생성
  2. 어플리케이션을 위한 환경을 설치하고 소스코드 등을 복사해 잘 동작하는 것을 확인
  3. 컨테이너를 이미지로 커밋

- 이 방법을 사용하면 일일이 수작업으로 패키지를 설치하고 소스코드를 깃에서 복제하거나 호스트에서 복사해양 함
- 도커는 일련의 과정을 손쉽게 기록하고 수행할 수 있는 빌드 명령어를 제공함
- 컨테이너에 설치해야 하는 패키지, 추가해야 하는 소스코드, 실행해야 하는 명령어와 쉘 스크립트 등을 하나의 파일에 기록
- 도커는 이 파일을 읽어 컨테이너에서 작업을 수행한 뒤 이미지로 만들어냄
- 이러한 작업을 기록한 파일의 이름을 Dockerfile이라고 함
- 깃과 같은 개발 도구를 통해 어플리케이션의 빌드 및 배포를 자동화할 수 있음
- 생성한 이미지를 도커 허브등을 통해 배포할 때 이미지 자체를 배포하는 대신 이미지를 생성하는 방법을 기록해 놓은 Dockerfile을 배포할 수도 있음

### 2.4.2 Dockerfile 작성

- Dockerfile 작성을 위해 간단한 웹 서버 이미지를 생성해보자
- 예제에서 사용할 디렉터리를 생성하고, 디렉터리 안에 HTML 파일을 미리 만들어 둠

```sh
# mkdir dockerfile && cd dockerfile
# echo test >> test.html

# vi Dockerfile


FROM ubuntu:14.04
LABEL maintainer "vljh246v <vljh246v@gmail.com>"
LABEL "purpose"="practice"
RUN apt-get update
RUN apt-get install apache2 -y
ADD test.html /var/www/html
WORKDIR /var/www/html
RUN ["/bin/bash", "-c", "echo hello >> test2.html"]
EXPOSE 80
CMD apachectl -DFOREGROUND
```

- Dockerfile은 한 줄이 하나의 명령어가 되고, 명령어를 명시한 뒤에 옵션을 추가하는 방식
- 명령어는 일반적으로 대문자로 표시
  - **FRON**
    - FROM 명령어는 Dockerfile을 작성할 때 반드시 한 번 이상 입력해야 함
    - 이미지 이름의 포맷은 docker run 명령어에서 이미지 이름을 사용했을 때와 같음
    - 사용하려는 이미지가 도커에 없다면 자동으로 pull
  - **LABEL**
    - 이미지에 메타데이터를 추가합니다.
    - 키:값 형태로 저장
    - 추가된 메타데이터는 docker inspect 명령어로 이미지의 정보를 구해서 확인 가능
  - **RUN**
    - 이미지를 만들기 위해서 컨테이너 내부에서 명령어를 실행
    - apt-get update 와 apt-get install apache2 명령어를 실행
    - 단 이미지를 빌드하는 과정에서 별도의 입력이 불가능하기 때문에 설치를 선택하는 Y/N을 yes로 설정해야 한다.
    - ["/bin/bash", "-c", "echo hello >> test2.html"] 명령어는 /bin/bash 셀을 사용해 "echo hello >> test2.html" 를 실행함
    - RUN ["실행 가능한 파일", "명령줄 인자1", "명령줄 인자2", ...]
  - **ADD**
    - 파일을 이미지에 추가함
    - JSON 형태로 ["추가할 파일 이름1", "추가할 파일 이름2",... "컨테이너에 추가될 위치"] 사용 가눙
  - **EXPOSE**
    - Dockerfile의 빌드로 생성되 이미지에서 노출할 포트를 설정
    - 반드시 이 포트가 호스트의 포트와 바인딩 되는 것은 아니며, 단지 컨테이너의 입력한 포트를 사용할 것임을 나타내는 것
  - **CMD**
    - CMD는 컨테이너가 시작될 때마다 실행할 명령어를 설정
    - Dockerfile에서 한번만 사용 가능
    - 위 예제에서는 컨테이너를 생성할 때 별도의 커맨드를 입력하지 않아도 apachectl -DFOREGROUND 커맨드가 적용되어 컨테이너가 시작될 때 자동으로 아파치 웹 서버가 실행 됨

### 2.4.3 Dockerfile 빌드

- 빌드 명령어 예제는 docker build -t mybuild:0.0 ./
- -t 옵션은 생성될 이미지의 이름을 설정
- build 끝에는 Dockerfile이 있는 경로
- 최종적으로 mybuild:0.0 이름의 이미지가 생성
  ![mybuild](https://lh3.googleusercontent.com/pw/AM-JKLVdfVZstW1C5wqk5JTvoG5QVKYBNeNFkSMEUzYorJXaDuoMtaqxWidMMESspguPaIzZd10GDWMfVc4OTcd5vk4RQJ89ddUZFD7_02KBjVNOOqOhteZ5MS_mbp9d4jRnPRC0eU7D3qYwJncKEZLWN-GGkw=w474-h62-no?authuser=0)
- 컨테이너가 실행될 때 예제에서 CMD 를 설정했기 때문에 별다른  설저 없이도 웹 서버가 실행됩니다.
- 만들어진 이미지로 컨테이너를 실행시키기 위한 명령어 예제를 입력해보자 docker run -d -P --name myserver mybuild:0.0
- -P 옵션은 이미지에 설정된 EXPOSE의 모든 포트를 호스트에 연결하도록 설정
- Dockerfile을 작성하는 개발자로서는 EXPOSE를 이용해 이미지가 실제로 사용될 때 어떤 포트를 사용돼야 하는지 명시할 수 있음
- 이미지를 사용하는 입장에서는 컨테이너의 어플리케이션이 컨테이너 내부에서 어떤 포트를 사용하는지 알 수 있게 됨
  ![P](https://lh3.googleusercontent.com/pw/AM-JKLUgsxyHVHNVkwpZBPAVkKPS_3UTFHDf9OIUQcs-TUpd3F0cJXjp8OLWTHhBBITxLDxuKi3jMKg7GjUPNuF3iHmX7k5W5ZVRlZvVHS8q9LBPAQejqMgKmoHibdu5DUM7zMtYsebDGrGTH33fqZWAsSqRyg=w1033-h119-no?authuser=0)
- Dockerfile에 이미지의 LABEL을 "purpose"="practice" 설정했으므로 images 명령어의 필터에 이 label을 적용할 수 있음
  ![filter](https://lh3.googleusercontent.com/pw/AM-JKLWeZhDgsiCC5eg_kN_EnWcBYnUUNfk8v9jpnQ703TUQKMpxhuNjcfvp-IE3C3q_pIA2_Kq56uU_XmhoZ47GuscIP5cbNPjigBSwW4Xe-z9qEwKLWK1Bo9HSa6ofpjBBN4cifzPNTbPNCEp9lD38Wu9iLg=w438-h62-no?authuser=0)

#### 2.4.3.2 빌드 과정 살펴보기

- 이미지 빌드를 시작하면 도커는 가장 먼저 빌드 컨텍스트를 읽음
- 빌드 컨텍스트는 이미지를 생성하는 데 필요한 각종 파일, 소스코드, 메타데이터 등을 담고 있는 디렉터리를 의미
- Dockerfile이 위치한 디렉터리가 빌드 컨텍스트가 됨
- 빌드 컨텍스트는 Dockerfile에서 빌드될 이미지에 파일을 추가할 때 사용
- ADD, COPY 명령어들은 빌드 컨텍스트의 파일을 이미지 추가함
- 컨텍스트는 build 명령어의 맨 마지막에 지정된 위치에 있는 파일을 전부 포함함
- 깃과 같은 외부 URL에서 Dockerfile을 읽어 들인다면 해당 저장소에 있는 파일과 서브 모듈을 포함함
- 따라서 이미지 빌드에 필요한 파일만 있는것이 바람직함, 빌드 속도와 메모리를 지나치게 점유할 수 있음
- .dockerignore라는 파일을 작성해서 빌드 시 이 파일에 명시된 이름의 파일을 컨텍스트에서 제외

##### Dockerfile을 이용한 컨테이너 생성과 커밋

- build 명령어로 이미지를 만들어 낼 때 하나의 컨테이너에서 일어나는 것은 아님
- 각 step은 Dockerfile에 기록된 명령어 단위로 실행
- ADD, RUN 등의 명령어가 실행될 때마다 새로운 컨테이너가 하나씩 생성
- Dockerfile에서 명령어 한 줄이 실행될 대마다 이전 step에서 생성된 이미지에 의해 새로운 컨테이너가 생성
- Dockerfile에 적힌 명령어를 수행하고 다시 새로운 이미지 레이어로 저장
- 명령어 줄 수만큼의 레이어가 존재하고, 컨테이너도 같은 수만큼 생성되고 삭제됨

##### 캐시를 이용한 이미지 빌드

- 한 번 이미지 빌드를 마치고 난 뒤 다시 같은 빌드를 진행하면 이전의 이미지 빌드에서 사용했던 캐시를 사용
- 실제 변경된 내용에 대해서도 캐시를 사용하는 경우도 있음, 이러한 경우 --no-cache 옵션을 추가하여 기존 빌드된 캐시를 사용하지 않음

#### 2.4.3.3 멀티 스테이지를 이용한 Dockerfile 빌드하기

- 너무 많은 의존성과 패키지 및 라이브러리들을 포함한 이미지일 경우 크기가 매우 클 수 있음
- 이미지 크기를 줄이기 위해 멀티 스테이지 빌드 방법을 사용할 수 있음
- 아래는 go 소스코드로 HelloWorld 를 출력하는 Dockerfile 이다.

  ```go
  package main

  import "fmt"
  func main() {
   fmt.Println("Hello, World!")
  }
  ```

  ```Dockerfile

  FROM golang
  ADD main.go /root
  WORKDIR /root
  RUN go build -o /root/mainApp /root/main.go
  CMD ["./mainApp"]
  ```

- 위 Dockerfile을 통해 나온 이미지 파일 크기는 아래와 같이 단순 어플리케이션이어도 많은 용량을 차지한다.
 ![go](https://lh3.googleusercontent.com/pw/AM-JKLU_EoDP5TgidIsJqQ0t53Kncf85k_mnG42vP5gPxL6TNtNoC9jgyeHUG7AXDx2HMEOgiLUzth4yyHM5ZKXDz-nNo4kG_ZSST6lFmXMNlUpaNCuwxLqk_w8Sgb1tX0om3KpQm2bV5tsQaqLXPdWidFKluw=w948-h118-no?authuser=0)

- 이러한 결과를 멀티 스테이지 빌드를 통해 이미지를 빌드한 예제는 아래와 같다.

  ```Dockerfile
  FROM golang
  ADD main.go /root
  WORKDIR /root
  RUN go build -o /root/mainApp /root/main.go

  FROM alpine:latest
  WORKDIR /root
  COPY --from=0 /root/mainApp .
  CMD ["./mainApp"]
  ```

- 일반적인 Dockerfile과 다르게 FROM을 통해 2개의 이미지가 명시됨
- 두 번째 FROM 아래에서 사용된 COPY 명령어는 첫 번째 FROM에서 사용된 이미지의 최종 상태에 존재하는 /root/mainApp 파일을 두 번째 이미지인 alpine:latest에 복사함
- --from=0은 첫 번째 FROM에서 빌드된 이미지의 최종 상태를 의미
- 위 도커 파일을 빌드한 뒤 이미지의 크기는 아래와 같음
  ![go2](https://lh3.googleusercontent.com/pw/AM-JKLX5lyqaUQqBtTRo8Zk5eh1rV6lkukq1-Fb4gLsaO5ZLY7I7wE1nEMN8fPJyXRJY2qMwXJnhTM59YCEIYEh3cnUM8N5-PQvUn8j54hWemyghHm3TSFQh4IwJAS7QyIM1z9kkVaHnnRu7ZHR8PMKuBgWjxA=w936-h82-no?authuser=0)
- 이전과 동일한 역할을 하는 이미지임에도 불구하고, 최종 크기가 많이 줄어 들었다.
- 멀티 스테이지 빌드는 필요한 실행 파일만 최종 이미지 결과물에 포함시킴으로써 이미지 크기를 줄일 때 유용하게 사용할 수 있다.

### 2.4.4 기타 Dockerfile 명령어
