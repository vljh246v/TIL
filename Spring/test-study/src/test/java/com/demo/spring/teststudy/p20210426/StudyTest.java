package com.demo.spring.teststudy.p20210426;

import static org.junit.jupiter.api.Assertions.*;

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
    Study study = new Study(-10);


    assertAll(
        () -> assertNotNull(study),
        () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
        () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0 보다 커야 한다.")
    );
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