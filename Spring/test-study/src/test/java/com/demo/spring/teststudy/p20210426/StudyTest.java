package com.demo.spring.teststudy.p20210426;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

class StudyTest {

  @Test
  @DisplayName("스터디 만들기1 (/ω＼)")
  @Tag("fast")
  void create_new_study() {
    Study study = new Study(100);
    assertThat(study.getLimit()).isGreaterThan(0);
  }


  @Test
  @DisplayName("스터디 만들기2 (/ω＼)")
  @Tag("slow")
  void create_new_study_again(){
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