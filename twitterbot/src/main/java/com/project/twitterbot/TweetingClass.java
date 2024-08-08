package com.project.twitterbot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.v1.UploadedMedia;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.UUID;

public class TweetingClass {
    private HttpClient client;
    private String postURL;
    private String bodyImage;
    private Twitter twitter;
    private Date currentTime;
    private String previousTitle;

    private String title;

    public TweetingClass(){
        this.client = HttpClient.newHttpClient();
        this.postURL = "https://api.twitter.com/2/tweets";
        this.bodyImage = "";
        this.previousTitle = "";
        this.twitter = Twitter.newBuilder()
                .prettyDebugEnabled(true)
                .oAuthConsumer("fmUDdUQjEiDb93bk9cMQAwNYV","ZfzTDkeuE5KuizupYiPy6Qv4X2vUw6W1SXnRS5UCgaGW6VJy1n")
                .oAuthAccessToken("1672949931013709825-fBmwJL5m9ovIOoQn73EfmXUoPcyfjD","O46APlAceZA6homKQZONUiknX8f9nzhqnwNhKIFXOlY5E")
                .build();
        this.currentTime = new Date();
    }

    public HttpRequest makeRequest() throws IOException, InterruptedException, TwitterException {

        DeezerSongClass song = new DeezerSongClass();
        HttpResponse<String> response = song.getClient().send(song.makeRequest(),HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body());
        JsonNode next = jsonNode.get("data");
        JsonNode last = next.get(0);
        title = last.get("title").asText();
        JsonNode artist = last.get("artist");
        String artistName = artist.get("name").asText();
        JsonNode album = last.get("album");
        String cover = album.get("cover_big").asText();
        URL url = new URL(cover);

        Long mediaID = uploadFile(url);
        var requestNonce = UUID.randomUUID().toString().replace("-", "");
        var time = System.currentTimeMillis() / 1000L;
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "OAuth oauth_consumer_key=\"fmUDdUQjEiDb93bk9cMQAwNYV\",oauth_token=\"1672949931013709825-fBmwJL5m9ovIOoQn73EfmXUoPcyfjD\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1699972867\",oauth_nonce=\"Jdt52Syvu6O\",oauth_version=\"1.0\",oauth_signature=\"Rc83Q5pvbVis2Oqxn34FTAB2f4s%3D\"")
                .uri(URI.create(this.postURL))
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"text\":\"Currently playing: " +  title + " by " + artistName + "\", " +
                        "\"media\": {\"media_ids\":[\"" + mediaID + "\"]}}"))
                .build();

        if (previousTitle != null && previousTitle.equals(title)) {
            return null;
        }

        previousTitle = title;
        return request;
    }
    public Long uploadFile(URL url) throws IOException, TwitterException {
        long mediaID;
            File finalimage = new File("C:\\Users\\Jean-Charles Mve\\Downloads\\images\\image.png");
            BufferedImage sourceImage = ImageIO.read(url);
            ImageIO.write(sourceImage, "jpg", finalimage);
            UploadedMedia media = twitter.v1().tweets().uploadMedia(finalimage);
            mediaID = media.getMediaId();
            return mediaID;

    }
    public HttpClient getClient(){
        return this.client;
    }
    
}
