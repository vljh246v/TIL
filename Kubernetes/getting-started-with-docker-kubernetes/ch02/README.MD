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