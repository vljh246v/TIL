package com.demo.teststudy.demo.controller;

import com.demo.teststudy.demo.service.RestClientTestService;
import com.demo.teststudy.demo.vo.Rest;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestClientTestController {

  private final RestClientTestService restClientTestService;

  public RestClientTestController(
      RestClientTestService restClientTestService) {
    this.restClientTestService = restClientTestService;
  }

  @GetMapping("/rest")
  public ResponseEntity<Rest> rest(){
    Rest api = restClientTestService.getApi();
    return new ResponseEntity<>(api, HttpStatus.OK);
  }
}
