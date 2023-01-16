package me.whiteship.refactoring._19_insider_trading;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CheckInTest {

    @Test
    void isFastPass() {
        CheckIn checkIn = new CheckIn();
        assertTrue(checkIn.isFastPass(new Ticket(LocalDate.of(2021, 12, 31), true)));
        assertFalse(checkIn.isFastPass(new Ticket(LocalDate.of(2021, 12, 31), false)));
        assertFalse(checkIn.isFastPass(new Ticket(LocalDate.of(2022, 1, 2), true)));
    }

}