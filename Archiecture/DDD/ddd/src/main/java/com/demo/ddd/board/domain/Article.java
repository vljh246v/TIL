package com.demo.ddd.board.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "article")
@SecondaryTable(
    name = "article_content",
    pkJoinColumns = @PrimaryKeyJoinColumn(name = "id")
)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @AttributeOverrides({
        @AttributeOverride(name = "content",
            column = @Column(table = "article_content")),
        @AttributeOverride(name = "contentType",
            column = @Column(table = "article_content"))
    })
    @Embedded
    private ArticleContent content;

    protected Article() {
    }

    public Article(final String title, final ArticleContent content) {
        this.title = title;
        this.content = content;
    }
}
