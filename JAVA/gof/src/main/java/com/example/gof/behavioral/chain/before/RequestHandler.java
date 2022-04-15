package com.example.gof.behavioral.chain.before;

public class RequestHandler {
    public void handler(Request request) {
        System.out.println(request.getBody());
    }
}
