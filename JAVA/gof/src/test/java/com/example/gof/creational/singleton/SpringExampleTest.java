package com.example.gof.creational.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class SpringExampleTest {

    @Test
    void applicationContextTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        String hello1 = context.getBean("hello", String.class);
        String hello2 = context.getBean("hello", String.class);

        assertThat(hello1).isEqualTo(hello2);
    }
}