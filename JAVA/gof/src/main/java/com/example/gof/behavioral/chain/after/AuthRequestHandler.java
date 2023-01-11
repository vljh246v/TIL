package com.example.gof.behavioral.chain.after;

public class AuthRequestHandler extends RequestHandler {

    public AuthRequestHandler(RequestHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handle(Request request) {
        System.out.println("인증 완료?");
        super.handle(request);
    }
}
