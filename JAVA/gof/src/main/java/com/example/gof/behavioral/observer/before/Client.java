package com.example.gof.behavioral.observer.before;

public class Client {

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();

        User user1 = new User(chatServer);
        user1.sendMessage("subject1", "message1");
        user1.sendMessage("subject2", "message2");

        User user2 = new User(chatServer);
        System.out.println(user2.getMessage("subject1"));

        user1.sendMessage("subject1", "message3");
        System.out.println(user2.getMessage("subject1"));
    }
}
