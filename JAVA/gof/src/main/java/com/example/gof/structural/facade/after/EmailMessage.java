package com.example.gof.structural.facade.after;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailMessage {

    private final String to;
    private final String from;
    private final String subject;
    private final String message;

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getText() {
        return this.message;
    }
}
