package com.demo.ddd.board.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ArticleContent {

    private String content;
    private String contentType;

    private ArticleContent() {
    }

    public ArticleContent(final String content, final String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
