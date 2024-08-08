package com.project.twitterbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class TwitterbotApplication{

	private static ConfigurableApplicationContext session;
	private static String body;
	public static TweetingClass tweetingClass;

	public static void main(String[] args){
		session = SpringApplication.run(TwitterbotApplication.class, args);
		tweetingClass = new TweetingClass();
		Timer timer = new Timer();
		var task = new TwitterTask();
		timer.schedule(task, 0, 100000);

	}

	public static class TwitterTask extends TimerTask {

		/**
		 * The action to be performed by this timer task.
		 */
		@Override
		public void run() {
			try {
				HttpRequest nullCheck = tweetingClass.makeRequest();
				if (nullCheck == null){
					System.out.println("HEY THERE!!!!!!!!!!!!!!!!!!!!!! SAME SONG :3");
					return;
				}
				HttpResponse<String> response = tweetingClass.getClient().send(nullCheck,HttpResponse.BodyHandlers.ofString());
				body = response.body();
				if (response.statusCode() == 201){
					System.out.println("Yay");
				} else if (response.statusCode() == 401) {
					System.out.println("Nay");
					session.close();
				} else {
					System.out.println("IDK");
					session.close();
				}
				System.out.println(response.body());
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

}
