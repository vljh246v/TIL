package com.demo.spring.teststudy.p20210426;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ParameterContext;
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

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
  @CsvSource({"10, '자바 스터디'", "20, 스프링"})
  public void parameterizedTest(@AggregateWith(StudyAggregator.class) Study study){
    System.out.println(study);
  }


  static class StudyAggregator implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor argumentsAccessor,
        ParameterContext parameterContext) throws ArgumentsAggregationException {
      return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
    }
  }

  static class StudyConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
      assertThat(Study.class).isEqualTo(aClass);
      return new Study(Integer.parseInt(o.toString()));
    }
  }
}