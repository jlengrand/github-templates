package com.amazon.ask.githubtemplates.data;

import org.kohsuke.github.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GithubDataGrabber {

    private Map<String, String> languagesAndTemplates =
            Map.of(
            "java", "https://github.com/Spring-Boot-Framework/Spring-Boot-Application-Template",
            "typescript", "https://github.com/carsonfarmer/ts-template"
            );

    private GitHub githubApi;

    public GithubDataGrabber(GitHub gitHub){
        this.githubApi = gitHub;
    }

//    public static void main(String[] args) throws IOException {
//        System.out.println("Grabbing data from your GitHub profile!");
//
//        Path githubConfigPath = Paths.get(Paths.get(System.getProperty("user.dir")).toString(), ".github");
//        if(!new File(githubConfigPath.toString()).exists()){
//            System.out.println("No GitHub config file found, exiting.");
//            System.exit(0);
//        }
//
//        GitHub github = GitHubBuilder.fromPropertyFile(githubConfigPath.toString()).build();
//
//        GithubDataGrabber githubHello = new GithubDataGrabber(github);
//
//        githubHello.getLanguages("jlengrand").forEach(System.out::println);
//        System.out.println("------");
//        githubHello.getRepositories("jlengrand").forEach(System.out::println);
//    }

    public Set<String> getLanguages(String username) throws IOException {
        PagedIterable<GHRepository> repos =  this.githubApi.getUser(username).listRepositories();

        return StreamSupport.stream(repos.spliterator(), false)
                .map(repo -> repo.getLanguage())
                .filter(s -> s != null)
                .collect(Collectors.toSet());
    }

    public List<String> getRepositories(String username) throws IOException {
        PagedIterable<GHRepository> repos =  this.githubApi.getUser(username).listRepositories();

        return StreamSupport.stream(repos.spliterator(), false)
                .map(repo -> sanitize(repo.getName()))
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    public String sanitize(String value) {
        return value
                .replace("-", " ")
                .replace("_", " ");
    }

}