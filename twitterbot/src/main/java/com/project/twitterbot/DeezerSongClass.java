package com.project.twitterbot;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class DeezerSongClass {
    private HttpClient client;
    private String postURL;

    public DeezerSongClass(){
        this.client = HttpClient.newHttpClient();
        this.postURL = "https://api.deezer.com/user/3091923584/history?index=0&limit=1&access_token=frwXZmYwrd6AjxdtJ7nJH1uOSLQYGIm7H3s0xHsFqisKlEeUn7";
    }

    public HttpRequest makeRequest(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.postURL))
                .GET()
                .build();
        return request;
    }
    public HttpClient getClient(){
        return this.client;
    }
}