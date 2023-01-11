package com.example.gof.behavioral.chain.after;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Disabled(value = "dev test")
    @Test
    public void test1() {
        RequestHandler chain = new AuthRequestHandler(new LoggingRequestHandler(new PrintRequestHandler(null)));
        Client client = new Client(chain);
        client.doWork();
    }
}