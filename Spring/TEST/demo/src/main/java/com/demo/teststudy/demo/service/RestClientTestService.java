package com.demo.teststudy.demo.service;

import com.demo.teststudy.demo.vo.Rest;
import java.util.Map;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestClientTestService {

  private final RestTemplate restTemplate;

  private final String openApiUrl = "https://httpbin.org/get";

  public RestClientTestService(RestTemplateBuilder restTemplateBuilder){
    this.restTemplate = restTemplateBuilder.build();
  }


  public Rest getApi() {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(openApiUrl)
        .queryParam("a", "a");

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, Rest.class).getBody();
  }
}
