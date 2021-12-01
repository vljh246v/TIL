package com.demoversion.object.domain.movie;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void createReservation() {
        assertThat(new Reservation(null, null, Money.ZERO, 0)).isNotNull();
    }
}