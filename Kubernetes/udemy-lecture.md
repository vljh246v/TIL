## pod 편집 참고 사항

-   pod 정의 파일이 없는 경우 아래와 같은 명령어 입력

    ```shell
    kubectl get pod <pod-name> -o yaml > pod-definition.yaml
    ```

-   오브젝트 속성을 변경하기 위해서는

    ```shell
    kubectl edit <type> <name>
    ## ex )
    kubectl edit replicaset new-replica-set

    kubectl edit pod pod-name
    ```

-   변경된 속성 파일을 업데이트 하기 위해서는
    ```shell
    kubectl replace -f <file-name>.yaml
    ```
-   ReplicaSet 스케일 변경을 위해서는

    ```shell
    kubectl scale --replicas=6 -f <file-name>.yaml
    ## 또는
    kubectl scale replicaset --replicas=5 my-replicaset
    ```

    또는

    ```shell
    kubectl scale --replicas=6 <type> <name>
    ## ex )
    kubectl scale --replicas=6 replicaset myapp-replicaset
    ```

-   생성된 리소스 확인

    ```shell
    kubectl get <type>
    ## ex)
    kubectl get replicaset
    ```

-   리소스 삭제
    ```shell
    kubectl delete <type> <name>
    ## ex)
    kubectl delete replicaset myapp-replicaset
    ```
-   리소스에 대한 설명을 보기 위해서
    ```sehll
    kubectl describe <type> <name>
    # ex)
    kubectl describe replicaset replicaset-name
    ```

---

## Object yaml 설명

-   ReplicaSet 파일 설명

    ```yaml
    apiVersion: apps/v1 # apps 는 api endpoint의 group을 의미한다.
    kind: ReplicaSet
    metadata:
        name: hello-node # ReplicaSet의 고유 이름
        labels: # 외부에서 ReplicaSet을 찾을 때 사용할 label. 복수의 label 입력가능
            service-name: hello-node
    spec: # 기대되는
        replicas: 2 # 2개의 Pod 복제(replica)를 생성한다.
        selector: # 위에서 설명했던 Label Selector - 이 Label에 매칭하는 Pod을 관리
            matchLabels: # 여기서는 label이 동일한 Pod을 select한다.
                service-name: hello-node # service-name label이 hello-node인 Pod을 2개 유지
        template: # Pod 생성 시 사용할 template를 정의한다. 기존의 Pod 정의와 동일하다.
            metadata:
                name: hello-node
                labels:
                    service-name: hello-node
            spec:
                containers:
                - name: hello-node
                    image: asbubam/hello-node
                    readinessProbe:
                    httpGet:
                        path: /
                        port: 8080
                    livenessProbe:
                    httpGet:
                        path: /
                        port: 8080

    ```
