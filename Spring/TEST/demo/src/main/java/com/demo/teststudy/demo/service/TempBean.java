package com.demo.teststudy.demo.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TempBean  {
  public void beanInitMethod(){
    log.info("@Bean(initMethod) !!!!!!!!");
  }
}
