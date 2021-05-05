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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

  @DisplayName("스터디 만들기")
  @ParameterizedTest(name = "{index} {displayName} message={0}")
  @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
  public void parameterizedTest(String message){
    System.out.println(message);
  }
}