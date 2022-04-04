package com.example.gof.creational.prototype.after;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@RequiredArgsConstructor
public class GithubIssue implements Cloneable {

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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected Object clone_deep_copy() {

        GithubRepository repository = new GithubRepository();
        repository.setName(this.repository.getName());
        repository.setUser(this.repository.getUser());

        GithubIssue githubIssue = new GithubIssue(repository);
        githubIssue.setId(this.id);
        githubIssue.setTitle(this.title);

        return githubIssue;
    }

}
