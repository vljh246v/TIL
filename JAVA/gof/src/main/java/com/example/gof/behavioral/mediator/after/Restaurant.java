package com.example.gof.behavioral.mediator.after;

import java.time.LocalDateTime;

public class Restaurant {
    private FrontDesk frontDesk;

    public void dinner(Integer guestId, LocalDateTime dateTime) {
        String roomNumber = this.frontDesk.getRoomNumberFor(guestId);
        System.out.println("add room charge to " + roomNumber);
    }
}
