package com.example.gof.behavioral.mediator.after;

import java.time.LocalDateTime;

public class Guest {
    private final FrontDesk frontDesk = new FrontDesk();

    private Integer id;

    public void getTowers(int numberOfTowers) {
        this.frontDesk.getTowers(this, numberOfTowers);
    }

    private void dinner(LocalDateTime dateTime) {
        this.frontDesk.dinner(this, dateTime);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
