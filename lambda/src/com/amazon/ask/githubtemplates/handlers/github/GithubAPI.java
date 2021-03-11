package com.amazon.ask.githubtemplates.handlers.github;

import org.javatuples.Pair;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.util.Map;

public class GithubAPI {

    public static final Map<String, Pair<String, String>> TEMPLATES =
            Map.of(
                    "java", new Pair<>("Spring-Boot-Framework", "Spring-Boot-Application-Template"),
                    "typescript", new Pair<>("carsonfarmer", "ts-template")
            );

    private GitHub github;

    public GithubAPI() throws GithubInitException {
        try {
            github = GitHubBuilder.fromEnvironment().build();
        } catch (IOException e) {
            throw new GithubInitException("Impossible to login the Github API");
        }
    }

    public GitHub getGithub() {
        return github;
    }
}
