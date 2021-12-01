package com.demoversion.object.domain.movie;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ScreeningTest {

    @Test
    void getStartTime() {
        final LocalDateTime now = LocalDateTime.now();
        final Screening screening = new Screening(null, 0, now);

        assertThat(screening.getStartTime()).isEqualTo(now);
    }

    @Test
    void isSequence() {
        final int sequence1 = 1;
        final int sequence2 = 2;
        final Screening screening = new Screening(null, sequence1, null);

        assertThat(screening.isSequence(sequence1)).isTrue();
        assertThat(screening.isSequence(sequence2)).isFalse();
    }

    @Test
    void getMovieFee() {
        final Money fee = Money.wons(1000L);
        final Movie movie = new Movie(null, null, fee, null);
        final Screening screening = new Screening(movie, 0, null);

        assertThat(screening.getMovieFee()).isEqualTo(fee);

    }
}