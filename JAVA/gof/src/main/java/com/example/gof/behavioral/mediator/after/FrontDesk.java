package com.example.gof.behavioral.mediator.after;

import java.time.LocalDateTime;

public class FrontDesk {

    private final CleaningService cleaningService = new CleaningService();
    private final Restaurant restaurant = new Restaurant();

    public void getTowers(Guest guest, int numberOfTowers) {
        cleaningService.getTowers(guest.getId(), numberOfTowers);
    }

    public String getRoomNumberFor(Integer guestId) {
        return "1111";
    }

    public void dinner(Guest guest, LocalDateTime dateTime) {
        restaurant.dinner(guest.getId(), dateTime);
    }
}
