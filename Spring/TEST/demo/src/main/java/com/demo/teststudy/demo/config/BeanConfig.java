package com.demo.teststudy.demo.config;

import com.demo.teststudy.demo.service.TempBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean(destroyMethod = "beanDestroyMethod", initMethod = "beanInitMethod")
  public TempBean tempBean(){
    return new TempBean();
  }
}
