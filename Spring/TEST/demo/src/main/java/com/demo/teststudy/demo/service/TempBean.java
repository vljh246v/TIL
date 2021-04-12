package com.demo.teststudy.demo.service;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TempBean implements InitializingBean {

  @PostConstruct
  public void postConstruct(){
    log.info("@PostConstruct !!!!!!!!");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("InitializingBean afterPropertiesSet !!!!!!!!");
  }
}
