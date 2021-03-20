# 1. Controller Test(Web Layer)

## 1.1 컨텍스트 로드 테스트

- 컨텍스트가 정상적으로 올라오는지 확인하기 위해 추가

```java
@SpringBootTest
class TestingWebApplicationTest {

 @Autowired
 private HomeController controller;

 @Test
 public void contextLoads() {
   assertThat(controller).isNotNull();
 }
}
```

## 1.2. Controller Test

### 1.2.1. HttpRequest Test

- 프로덕션과 마찬가지로 http 요청을 통해 응답을 확인할 수 있음
- 실제 서버를 구동시키는 것과 유사하게 테스트 동작

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void greetingShouldReturnDefaultMessage() throws Exception {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
        String.class)).contains("Hello, World");
  }
}
```

### 1.2.2. Testing WebApplication Test

- 컨텍스트만올려서 시작하는 방법
- MockMvc를 활용

```java
@SpringBootTest
@AutoConfigureMockMvc
class TestingWebApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnDefaultMessage() throws Exception {
    this.mockMvc.perform(get("/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Hello, World")));
  }
}
```

### 1.2.3. WebLayer Test

- 위 예제에서는 @SpringBootTest 어노테이션을 사용해서 컨텍스트에 스프링에서 사용하는 bean들을 모두 올리고 시작함
- @WebMvcTest 어노테이션을 사용해서 WebLayer에 필요한 bean 들만 올려서 사용가능

```java
@WebMvcTest
public class WebLayerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnDefaultMessage() throws Exception {
    this.mockMvc.perform(get("/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Hello, World")));
  }
}

- 컨트롤러가 여러개인 경우 하나만 인스턴스화 해서 사용하도록 요청할 수도 있음 @WebMvcTest(HomeController.class)

```

## 1.2. 의존성이 있는 Controller Test

```java
@Controller
public class GreetingController {

  private final GreetingService service;

  public GreetingController(GreetingService service){
    this.service = service;
  }

  @RequestMapping("/greeting")
  public @ResponseBody String greeting() {
    return service.greet();
  }
}

...

@Service
public class GreetingService {
  public String greet() {
    return "Hello, World";
  }
}

```

- 의존성을 컨트롤러에 주입해준다.

```java
@WebMvcTest(GreetingController.class)
public class WebMockTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GreetingService service;

  @Test
  public void greetingShouldReturnMessageFromService() throws Exception {
    when(service.greet()).thenReturn("Hello, Mock");

    this.mockMvc.perform(get("/greeting"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Hello, Mock")));
  }
}

```
