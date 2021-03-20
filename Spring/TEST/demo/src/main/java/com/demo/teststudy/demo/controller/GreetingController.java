package com.demo.teststudy.demo.controller;

import com.demo.teststudy.demo.service.GreetingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
