package com.example.gof.structural.decorator.after;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Client {

    private final CommentService commentService;

    public void writeComment(String comment) {
        commentService.addComment(comment);
    }
}
