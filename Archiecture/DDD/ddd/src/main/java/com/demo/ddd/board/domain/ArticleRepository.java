package com.demo.ddd.board.domain;

public interface ArticleRepository {

    void save(Article article);

    Article findById(Long id);
}