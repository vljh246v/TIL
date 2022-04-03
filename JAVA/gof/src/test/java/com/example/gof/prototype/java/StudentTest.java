package com.example.gof.prototype.java;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void Collection_clone_test() {
        Student a = new Student("a");
        Student b = new Student("b");

        List<Student> students = List.of(a, b);

        List<Student> clone = new ArrayList<>(students);

        assertThat(students).isEqualTo(clone);
    }

}