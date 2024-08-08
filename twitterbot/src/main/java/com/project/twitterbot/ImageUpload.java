package com.project.twitterbot;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class ImageUpload {
    private HttpClient client;
    private String postURL;

    public ImageUpload(){
        this.client = HttpClient.newHttpClient();
        this.postURL = "https://upload.twitter.com/1.1/media/upload.json?media_category=tweet_image";
    }

    public HttpRequest makeRequest(String body){
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "OAuth oauth_consumer_key=\"fmUDdUQjEiDb93bk9cMQAwNYV\",oauth_token=\"1672949931013709825-fBmwJL5m9ovIOoQn73EfmXUoPcyfjD\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1692047841\",oauth_nonce=\"krLMZm6X5Jt\",oauth_version=\"1.0\",oauth_signature=\"HzvqqKYLgO8p1DVq81WJHIbF3io%3D\"")
                .uri(URI.create(this.postURL))
                .header("Content-type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return request;
    }
    public HttpClient getClient(){
        return this.client;
    }
}
