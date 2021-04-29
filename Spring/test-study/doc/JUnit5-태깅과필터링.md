# JUnit 5 태깅과 필터링

- 테스트가 여러개면 테스트들을 그룹화 할 수 있음
- @Tag 라는 어노테이션으로 태그 관리
- 다양한 특징(속도, 테스트 종류 등등)에 따라 나눌 수 있음
- IDE(인텔리J) 에서 해당 태그들을 필터링 해서 실행할 수 있음
- maven 설정에서도 프로파일별 테스트 태그를 설정할 수 있다.
    ```xml
    
    <profile>
        <id>ci</id>
        <build>
            <plugin>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <groups>fast | slow</groups>
                </configuration>
            </plugin>
        </build>
    </profile>
    <!-- ./mvnw test -P ci -->
    ```