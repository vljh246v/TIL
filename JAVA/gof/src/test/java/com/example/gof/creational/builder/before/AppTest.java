package com.example.gof.creational.builder.before;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void createTour() {
        TourPlan shortTrip = new TourPlan();
        shortTrip.setTitle("제주도 다이빙");
        shortTrip.setStartDate(LocalDate.of(2022, 6, 1));

        assertThat(shortTrip.getTitle()).isEqualTo("제주도 다이빙");

        TourPlan tourPlan = new TourPlan();
        tourPlan.setTitle("세부 다이빙");
        tourPlan.setNights(4);
        tourPlan.setDays(5);
        tourPlan.setStartDate(LocalDate.of(2022, 7, 6));
        tourPlan.setWhereToStay("다이빙 샵");
        tourPlan.addPlan(0, "도착");
        tourPlan.addPlan(1, "다이빙 1");
        tourPlan.addPlan(1, "다이빙 2");
        tourPlan.addPlan(1, "다이빙 3");
        tourPlan.addPlan(2, "다이빙 1");
        tourPlan.addPlan(2, "다이빙 2");
        tourPlan.addPlan(2, "다이빙 3");
        tourPlan.addPlan(3, "다이빙 1");
        tourPlan.addPlan(3, "다이빙 2");
        tourPlan.addPlan(3, "다이빙 3");
        tourPlan.addPlan(4, "go home");

        assertThat(tourPlan.getTitle()).isEqualTo("세부 다이빙");

    }
}