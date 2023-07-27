package com.currencyexchange.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.currencyexchange.entity.CurrencyExchange;
import com.currencyexchange.repo.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeControlller {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository currencyRepo;
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		LOG.info("Inside currency-echange-service:retriveExchangeValue()");
		CurrencyExchange currencyExchange = currencyRepo.findByFromAndTo(from, to);
		if(currencyExchange == null)
		{
			throw new RuntimeException("Unable to find data");
		}
		String port = environment.getProperty("server.port");
		currencyExchange.setEnvironment(port);
		
		return currencyExchange;
	}
}
