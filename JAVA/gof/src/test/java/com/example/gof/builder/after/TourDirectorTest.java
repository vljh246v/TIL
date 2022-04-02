package com.example.gof.builder.after;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.gof.builder.before.TourPlan;

class TourDirectorTest {

    @Test
    void diveTrip() {

        // given
        TourDirector director = new TourDirector(new DefaultTourBuilder());

        // when
        TourPlan tourPlan = director.cebuDiveTrip();

        // then
        assertThat(tourPlan.getTitle()).isEqualTo("세부 여행");

    }
}