package com.example.gof.behavioral.chain.after;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Client {

    private final RequestHandler requestHandler;

    public void doWork() {
        Request request = new Request("테스트~");
        requestHandler.handle(request);
    }
}
