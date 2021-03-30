package com.demo.teststudy.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
class TestRepositoryTest {


  @Autowired
  TestRepository testRepository;

  @Test
  public void testRepositoryLoads() {
    assertThat(testRepository).isNotNull();
  }

}