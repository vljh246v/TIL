package com.demo.teststudy.demo.service;

import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TempBean  {

  @PreDestroy
  public void preDestroy(){
    log.info("@PreDestroy !!!!!!!!");
  }
}
