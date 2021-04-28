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
    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> new Study(-10));
    String message = illegalArgumentException.getMessage();

    assertEquals("limit은 0보다 커야한다.", message);
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