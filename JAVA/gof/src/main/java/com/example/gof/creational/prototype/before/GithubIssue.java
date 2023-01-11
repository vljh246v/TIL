package com.example.gof.creational.prototype.before;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class GithubIssue {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String title;

    @Getter
    private final GithubRepository repository;

    public String getUrl() {
        return String.format("https://github.com/%s/%s/issues/%d",
                             repository.getUser(),
                             repository.getName(),
                             this.getId());
    }
}
