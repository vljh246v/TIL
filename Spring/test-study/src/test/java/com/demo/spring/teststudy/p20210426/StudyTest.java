package com.demo.spring.teststudy.p20210426;

import static org.assertj.core.api.Assertions.assertThat;

import com.demo.spring.teststudy.FindSlowTestExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

  @RegisterExtension
  static FindSlowTestExtension findSlowTestExtension
      = new FindSlowTestExtension(1000);

  int value = 1;

  @Order(2)
  @Test
  @DisplayName("스터디 만들기1 (/ω＼)")
  @Tag("fast")
  void create_new_study() {
    System.out.println(this);
    System.out.println(value++);
    Study study = new Study(1);
    assertThat(study.getLimit()).isGreaterThan(0);
  }


  @Order(1)
  @Test
  @DisplayName("스터디 만들기2 (/ω＼)")
  @Tag("slow")
  void create_new_study_again() throws InterruptedException {
    Thread.sleep(1005l);
    System.out.println(this);
    System.out.println("create1 " + value++);
  }

}