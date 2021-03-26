package com.demo.teststudy.demo.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.assertj.core.api.Assertions.assertThat;

import com.demo.teststudy.demo.vo.Args;
import com.demo.teststudy.demo.vo.Rest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

@RunWith(SpringRunner.class)
@RestClientTest(value = RestClientTestService.class)
public class RestClientTestServiceTest {

  @Autowired
  RestClientTestService restClientTestService;

  @Autowired
  private MockRestServiceServer mockServer;

  private final String openApiUrl = "https://httpbin.org/get";


  @Test
  public void getApi(){
    // given
    Rest rest = new Rest();
    Args args = new Args();
    args.setA("a");
    rest.setArgs(args);

    String expect = "{\"args\":{\"a\":\"a\"},\"headers\":{\"Accept\":\"application/json\",\"Content-Type\":\"application/json\",\"Host\":\"httpbin.org\",\"User-Agent\":\"Java/11.0.7\",\"X-Amzn-Trace-Id\":\"Root=1-605defad-35b5c60e6d4e616220bfffb0\"},\"origin\":\"14.47.104.108\",\"url\":\"https://httpbin.org/get?a=a\"}";
    mockServer.expect(requestTo(openApiUrl + "?a=a"))
        .andRespond(withSuccess(expect, MediaType.APPLICATION_JSON));

    // when
    Rest api = restClientTestService.getApi();

    // then
    assertThat(api.getArgs().getA()).isEqualTo(rest.getArgs().getA());
  }
}