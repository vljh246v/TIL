package com.example.gof.creational.prototype.java;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.example.gof.creational.prototype.before.GithubIssue;
import com.example.gof.creational.prototype.before.GithubRepository;

class GithubIssueDataTest {

    @Test
    void model_and_mapper_test() {
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

        ModelMapper modelMapper = new ModelMapper();
        GithubIssueData githubIssueData = modelMapper.map(githubIssue, GithubIssueData.class);

        assertThat(githubIssueData.getRepositoryName()).isEqualTo(name);
        assertThat(githubIssueData.getRepositoryUser()).isEqualTo(user);
        assertThat(githubIssueData.getId()).isEqualTo(id);
        assertThat(githubIssueData.getTitle()).isEqualTo(title);
    }

}