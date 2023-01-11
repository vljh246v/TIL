package com.example.gof.creational.builder.after;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.example.gof.creational.builder.before.TourPlan;

class AppTest {

    @Test
    void createTour() {
        TourPlanBuilder builder = new DefaultTourBuilder();
        TourPlan plan = builder.title("세부 여행")
                               .nightsAndDays(3, 4)
                               .startDate(LocalDate.of(2022, 8, 1))
                               .whereToStay("다이빙 샵")
                               .addPlan(0, "체크인")
                               .addPlan(1, "저녁먹기")
                               .addPlan(2, "저녁먹기")
                               .addPlan(3, "저녁먹기")
                               .getPlan();
        assertThat(plan.getTitle()).isEqualTo("세부 여행");
    }

}