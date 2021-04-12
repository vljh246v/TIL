package com.demo.teststudy.demo.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class TempBean implements InitializingBean, DisposableBean {

  public void beanDestroyMethod(){
    log.info("@Bean(destroyMethod) !!!!!!!!");
  }

  public void beanInitMethod(){
    log.info("@Bean(initMethod) !!!!!!!!");
  }

  @Override
  public void destroy() throws Exception {
    log.info("DisposableBean destroy !!!!!!!!");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("InitializingBean afterPropertiesSet !!!!!!!!");
  }

  @PostConstruct
  public void postConstruct(){
    log.info("@PostConstruct !!!!!!!!");
  }

  @PreDestroy
  public void preDestroy(){
    log.info("@PreDestroy !!!!!!!!");
  }
}
