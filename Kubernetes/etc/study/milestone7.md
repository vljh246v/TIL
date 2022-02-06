# Job

* batch 처리에 적합
* k8s는 기본적으로 pod가 running인 상태를 보장해준다.
* spot 형태의 작업에 적합
  * 비정상 종료 시 다시 실행
  * 정상 종료 시 완료
* 정상 종룔시 pod를 삭제하지는 않음
  * container의 상태를 보존하기 위해
* completed을 보장
* apiVersion -> batch/v1
* [restartPolicy](https://kubernetes.io/ko/docs/concepts/workloads/pods/pod-lifecycle/#restart-policy) 는 Never와 OnFailure 만 설정 가능
  * OnFailure : 파드는 노드에 그대로 유지, 컨테이너는 다시 실행
  * Never : 잡 컨트롤러는 실패시 새 파드를 시작
* backoffLimit : 잡을 실패로 간주하기 이전에 재시도할 횟수, 해당 횟수 이후에 파드 삭제
* .spec.completions : 설정한 컨테이너를 실행할 횟수
* .spec.parallelism : 병렬로 실행 가능한 job
* [잡 패턴](https://kubernetes.io/ko/docs/concepts/workloads/controllers/job/#%EC%9E%A1-%ED%8C%A8%ED%84%B4)
* .spec.activeDeadlineSeconds : 설정한 시간안에 안끝나면 파드 종료
  * .spec.activeDeadlineSeconds 는 .spec.backoffLimit 보다 우선


```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: job-example
spec:
#  completions: 5
#  parallelism: 2
  template:
    spec:
      containers:
      - name: centos-container
        image: centos:7
        command: ["bash"]
        args:
        - "-c"
        - "echo 'Hello World'; sleep 5; echo 'Bye'"
      restartPolicy: Never
#      restartPolicy: OnFailure
#  backoffLimit: 2
```


# CronJob

* Job 컨트롤 기능 포함
* 원하는 시간에 Job을 실행하도록 예약 가능
* Linux의 cornjob과 비슷
* 반복해서 사용할때 사용

![cronjob](https://lh3.googleusercontent.com/pw/AM-JKLUFGX8LDAO4MfK9kX1xZ3vKPbH6LA1CAMxuh0a_5kMJnDm6Cwk3Du2fae6uBNCTEOjr_CSHAf5cHjh8A94Rct-NNdtFXYyvGBPxbZIzBJ8DZ09qrgCztjU-UdU_mUevBmkxXpWQM6y1Z6ywYGEelaAaGQ=w309-h172-no?authuser=0)

```
 ┌───────────── 분 (0 - 59)
 │ ┌───────────── 시 (0 - 23)
 │ │ ┌───────────── 일 (1 - 31)
 │ │ │ ┌───────────── 월 (1 - 12)
 │ │ │ │ ┌───────────── 요일 (0 - 6) (일요일부터 토요일까지;
 │ │ │ │ │                                   특정 시스템에서는 7도 일요일)
 │ │ │ │ │
 │ │ │ │ │
 * * * * *
```

![cronjob2](https://lh3.googleusercontent.com/pw/AM-JKLVvbxH_xP8Znplfe6wIYmCdtTR5QYLPxxZodI8OnVDnfsFiUKhzhgVFYfaFIUzUGpg1IDxTvO105ZUtZo91L6JmQoqrIuxh7QFK97h1oWhHd6ZuMWjgMuCbmShEWuYMvFHBeaPQfT6A6yq8Hm13cFKSUA=w800-h521-no?authuser=0)
  
* 모든 시간은  kube-controller-manager 기준

* .spec.concurrencyPolicy
  * Allow(기본값) : 동시에 실행되는 잡을 허용
  * Forbid : 동시 실행 허용 금지, 새로운 잡 실행 건너 뜀
  * Replace : 새로운 잡으로 실행 대체

* .spec.startingDeadlineSeconds : 해당 시간이 지나면 실패한 작업으로 간주
* .spec.successfulJobsHistoryLimit 와 .spec.failedJobsHistoryLimit :  기록을 보관할 완료 및 실패한 잡의 개수를 지정

```yaml
apiVersion: batch/v1
kind: CronJob
metadata:
  name: hello
spec:
  schedule: "* * * * *"
  startingDeadlineSeconds: 300
  concurrencyPolicy: Allow
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: hello
            image: busybox
            command:
            - /bin/sh
            - -c
            - echo Hello; sleep 10; echo Bye
          restartPolicy: Never
```

