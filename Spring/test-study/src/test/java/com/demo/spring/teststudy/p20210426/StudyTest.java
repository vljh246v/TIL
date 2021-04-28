package com.demo.spring.teststudy.p20210426;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

class StudyTest {

  @Test
  @DisplayName("스터디 만들기1 (/ω＼)")
  @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
  void create_new_study() {
    String test_env = System.getenv("TEST_ENV");
    System.out.println(test_env);
    Study actual = new Study(100);

    assertThat(actual.getLimit()).isGreaterThan(0);
  }


  @Test
  @DisplayName("스터디 만들기2 (/ω＼)")
  @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "DEMO")
  void create_new_study_again(){
    String test_env = System.getenv("TEST_ENV");
    System.out.println("create1");
  }

  @BeforeAll
  static void beforeAll(){
    System.out.println("beforeAll");
  }

  @AfterAll
  static void afterAll(){
    System.out.println("afterAll");
  }

  @BeforeEach
  void beforeEach(){
    System.out.println("beforeEach");
  }

  @AfterEach
  void afterEach(){
    System.out.println("afterEach");
  }
}