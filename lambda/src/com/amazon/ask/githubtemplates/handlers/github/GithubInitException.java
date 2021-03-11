package com.amazon.ask.githubtemplates.handlers.github;

public class GithubInitException extends Exception{
    public GithubInitException(String errorMessage) {
        super(errorMessage);
    }
}
