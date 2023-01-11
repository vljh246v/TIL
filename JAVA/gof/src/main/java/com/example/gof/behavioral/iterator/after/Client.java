package com.example.gof.behavioral.iterator.after;

import java.util.Iterator;

public class Client {
    public static void main(String[] args) {
        Board board = new Board();
        board.addPost("add post1");
        board.addPost("add post1");
        board.addPost("add post1");

        Iterator<Post> recentPostIterator = board.getRecentPostIterator();
        while (recentPostIterator.hasNext()) {
            System.out.println(recentPostIterator.next().getTitle());
        }
    }
}
