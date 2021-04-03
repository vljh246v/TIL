package com.demo.teststudy.demo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest
class TestMapperTest {

  @Autowired
  TestMapper testMapper;

  @Test
  public void setTestMapperLoads() {
    assertThat(testMapper).isNotNull();
  }
}