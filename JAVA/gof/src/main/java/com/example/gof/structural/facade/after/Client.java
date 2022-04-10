package com.example.gof.structural.facade.after;

public class Client {

    public static void main(String[] args) {

        EmailSettings emailSettings = new EmailSettings("127.0.0.1");
        String to = "keesun@whiteship.me";
        String from = "whiteship@whiteship.me";
        String subject = "Test Mail from Java Program";
        String text = "message";
        EmailMessage emailMessage = new EmailMessage(to, from, subject, text);

        EmailSender emailSender = new EmailSender(emailSettings);
        emailSender.sendEmail(emailMessage);
    }
}
