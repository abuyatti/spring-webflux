package com.example.tweet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@RestController
public class TweetController {

	private static final Logger log = LoggerFactory.getLogger(TweetController.class);

	private static final int SERVER_PORT = 8080;

	@GetMapping("/tweets-blocking")
	public List<Tweet> getTweetsBlocking() {
		log.info("Starting BLOCKING Controller!");

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Tweet>> response = restTemplate.exchange(getSlowServiceUri(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Tweet>>() {
				});

		List<Tweet> result = response.getBody();

		result.forEach(tweet -> log.info(tweet.toString()));

		log.info("Exiting BLOCKING Controller!");

		return result;
	}

	@GetMapping(value = "/tweets-non-blocking")
	public Flux<Tweet> getTweetsNonBlocking() {
		log.info("Starting NON-BLOCKING Controller!");

		Flux<Tweet> tweetFlux = WebClient.create().get().uri(getSlowServiceUri()).retrieve().bodyToFlux(Tweet.class);

		tweetFlux.subscribe(tweet -> log.info(tweet.toString()));

		log.info("Exiting NON-BLOCKING Controller!");

		return tweetFlux;
	}

	private String getSlowServiceUri() {
		return "http://localhost:" + SERVER_PORT + "/slow-service-tweets";
	}

}
