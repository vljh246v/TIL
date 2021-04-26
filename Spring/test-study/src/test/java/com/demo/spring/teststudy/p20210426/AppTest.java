package com.demo.spring.teststudy.p20210426;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppTest {

  @Test
  public void create(){
    App app = new App();
    assertNotNull(app);
  }
}