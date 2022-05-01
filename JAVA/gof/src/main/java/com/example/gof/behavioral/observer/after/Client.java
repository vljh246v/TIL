package com.example.gof.behavioral.observer.after;

public class Client {

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        User user1 = new User("demo");
        User user2 = new User("jaehyun");

        chatServer.register("game1", user1);
        chatServer.register("game1", user2);

        chatServer.register("game2", user1);

        chatServer.sendMessage(user1, "game1", "game1~?");
        chatServer.sendMessage(user2, "game1", "game1~!!");

        chatServer.unregister("game2", user2);

        chatServer.sendMessage(user2, "game2", "game2 start");
    }
}
