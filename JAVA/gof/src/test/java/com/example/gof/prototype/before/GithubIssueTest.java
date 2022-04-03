package com.example.gof.prototype.before;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GithubIssueTest {

    @Test
    void getUrl() {

        // given
        String name = "study";
        String user = "demo.lim";
        int id = 1;
        String title = "디자인 패턴 다시 학습 시즌 1";

        GithubRepository repository = new GithubRepository();
        repository.setName(name);
        repository.setUser(user);

        GithubIssue githubIssue = new GithubIssue(repository);
        githubIssue.setId(id);
        githubIssue.setTitle(title);

        // when
        String url = githubIssue.getUrl();

        // then
        assertThat(url).isEqualTo("https://github.com/" + user + "/" + name + "/issues/" + id);
    }
}