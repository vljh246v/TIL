package com.example.gof.behavioral.iterator.before;

import java.util.List;

public class Client {

    public static void main(String[] args) {
        Board board = new Board();
        board.addPost("add post1");
        board.addPost("add post1");
        board.addPost("add post1");

        List<Post> posts = board.getPosts();
        for (Post post : posts) {
            System.out.println(post.getTitle());
        }

        posts.sort((p1, p2) -> p2.getCreatedDateTime().compareTo(p1.getCreatedDateTime()));
        for (Post post : posts) {
            System.out.println(post.getTitle());
        }
    }

}
