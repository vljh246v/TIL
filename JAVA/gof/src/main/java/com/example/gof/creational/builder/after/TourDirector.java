package com.example.gof.creational.builder.after;

import java.time.LocalDate;

import com.example.gof.creational.builder.before.TourPlan;

public class TourDirector {

    private final TourPlanBuilder tourPlanBuilder;

    public TourDirector(TourPlanBuilder tourPlanBuilder) {
        this.tourPlanBuilder = tourPlanBuilder;
    }

    public TourPlan cebuDiveTrip() {
        return tourPlanBuilder.title("세부 여행")
                              .nightsAndDays(3, 4)
                              .startDate(LocalDate.of(2022, 8, 1))
                              .whereToStay("다이빙 샵")
                              .addPlan(0, "체크인")
                              .addPlan(1, "저녁먹기")
                              .addPlan(2, "저녁먹기")
                              .addPlan(3, "저녁먹기")
                              .getPlan();
    }
}
