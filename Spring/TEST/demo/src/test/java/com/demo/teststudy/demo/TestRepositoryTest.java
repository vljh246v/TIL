package com.demo.teststudy.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.demo.teststudy.demo.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TestRepositoryTest {

  @Autowired
  TestRepository testRepository;

  @Test
  public void testRepositoryLoads() {
    assertThat(testRepository).isNotNull();
  }
}
