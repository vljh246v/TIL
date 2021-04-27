package com.demo.spring.teststudy.p20210426;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

class StudyTest {

  @Test
  @DisplayName("테스트~")
  void create() {
    System.out.println("create");
    Study study = new Study();
    assertNotNull(study);
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