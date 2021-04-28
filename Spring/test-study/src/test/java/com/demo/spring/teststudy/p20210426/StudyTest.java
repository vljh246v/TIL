package com.demo.spring.teststudy.p20210426;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyTest {

  @Test
  @DisplayName("스터디 만들기 (/ω＼)")
  void create_new_study(){

    String test_env = System.getenv("TEST_ENV");

    assumeTrue("LOCAL".equalsIgnoreCase(test_env));

    assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
      System.out.println(test_env);
      Study actual = new Study(10);
      assertThat(actual.getLimit()).isGreaterThan(0);
    });

    assumingThat("demo".equalsIgnoreCase(test_env), () -> {
      System.out.println(test_env);
      Study actual = new Study(20);
      assertThat(actual.getLimit()).isGreaterThan(10);
    });


  }

  @Test
  void create1(){
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