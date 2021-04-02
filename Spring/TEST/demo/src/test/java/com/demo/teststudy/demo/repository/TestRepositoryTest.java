package com.demo.teststudy.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.demo.teststudy.demo.entity.*;

@DataJpaTest
class TestRepositoryTest {


  @Autowired
  TestRepository testRepository;

  @Test
  public void testRepositoryLoads() {
    assertThat(testRepository).isNotNull();
  }

  @Test
  public void save(){
    TestEntity code1 = TestEntity.builder()
        .code("code1")
        .build();

    testRepository.save(code1);

    List<TestEntity> all = testRepository.findAll();

    assertThat(all.size()).isEqualTo(1);
    assertThat(all.get(0)).isEqualTo(code1);
  }

}