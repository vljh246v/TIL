package com.demoversion.object.ch04;

import com.demoversion.object.domain.movie.Money;

public class ReservationAgency {

    public Reservation reserve(final Screening screening, final Customer customer,
        final int audienceCount) {
        final Movie movie = screening.getMovie();

        boolean discountable = false;

        for (final DiscountCondition condition : movie.getDiscountConditions()) {
            if (condition.getType() == DiscountConditionType.PERIOD) {
                discountable =
                    screening.getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek())
                        && condition.getStartTime()
                        .compareTo(screening.getWhenScreened().toLocalTime()) <= 0
                        && condition.getEndTime()
                        .compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
            } else {
                discountable = condition.getSequence() == screening.getSequence();
            }

            if (discountable) {
                break;
            }
        }

        final Money fee;
        if (discountable) {
            final Money discountAmount;
            switch (movie.getMovieType()) {
                case AMOUNT_DISCOUNT:
                    discountAmount = movie.getDiscountAmount();
                    break;
                case PERCENT_DISCOUNT:
                    discountAmount = movie.getFee().times(movie.getDiscountPercent());
                    break;
                case NONE_DISCOUNT:
                    discountAmount = Money.ZERO;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + movie.getMovieType());
            }
            fee = movie.getFee().minus(discountAmount).times(audienceCount);
        } else {
            fee = movie.getFee();
        }

        return new Reservation(customer, screening, fee, audienceCount);
    }
}
