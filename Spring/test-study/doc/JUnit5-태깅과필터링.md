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

## JUnit 5: 커스텀 태그
- 메타 어노테이션으로 만들어서 사용 가능
    ```java
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Tag("fast")
    @Test
    public @interface FastTest { }
    ```
- @Tag 어노테이션 vlaue는  타입세이프한것이 아니기 때문에 메타 어노테이션을 만들어 두고 사용하는것이 좋음