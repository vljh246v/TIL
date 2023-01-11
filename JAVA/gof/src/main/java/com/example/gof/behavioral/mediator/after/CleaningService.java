package com.example.gof.behavioral.mediator.after;

public class CleaningService {

    private FrontDesk frontDesk;

    public void getTowers(Integer guestId, int numberOfTowers) {
        String roomNumber = this.frontDesk.getRoomNumberFor(guestId);
        System.out.println("provide " + numberOfTowers + " to " + roomNumber);
    }
}
