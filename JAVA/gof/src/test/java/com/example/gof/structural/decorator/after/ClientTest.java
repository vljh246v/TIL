package com.example.gof.structural.decorator.after;

import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void appTest() {
        boolean enabledSpamFilter = true;

        boolean enabledTrimming = true;

        CommentService commentService = new DefaultCommentService();

        if (enabledSpamFilter) {
            commentService = new SpamFilteringCommentDecorator(commentService);
        }

        if (enabledTrimming) {
            commentService = new TrimmingCommentDecorator(commentService);
        }

        Client client = new Client(commentService);
        client.writeComment("오징어게임");
        client.writeComment("보는게 하는거 보다 재밌을 수가 없지...");
        client.writeComment("http://whiteship.me");
    }
}