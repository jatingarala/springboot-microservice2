package com.currencyexchange.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreackerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreackerController.class);
	
	@GetMapping("/sample-api")
//	@Retry(name = "sample-api", fallbackMethod = "hardcodedRespose")
//	@CircuitBreaker(name = "default", fallbackMethod = "hardcodedRespose")
	@RateLimiter(name = "default")
	// 10s => 10000 calls to the sample api
	public String sampleApi() {
		logger.info("sample api call received");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8000/dummy", String.class);
		return forEntity.getBody();
	}
	
	public String hardcodedRespose(Exception ex) {
		
		return "fallback-resposnse";
	}
}
