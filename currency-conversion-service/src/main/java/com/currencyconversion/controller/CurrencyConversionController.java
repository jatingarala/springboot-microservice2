package com.currencyconversion.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.currencyconversion.entity.CurrencyConversion;
import com.currencyconversion.feignclient.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

		@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
		public CurrencyConversion calculateCurrencyConversion(
				@PathVariable String from,
				@PathVariable String to,
				@PathVariable BigDecimal quantity
				) {
			
			LOG.info("Inside currency-conversion-service:calculateCurrencyConversion()");
			
			HashMap<String, String> uriVariables= new HashMap<String, String>();
			uriVariables.put("from", from);
			uriVariables.put("to", to);
			ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate()
					.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
					CurrencyConversion.class, uriVariables);
			
			CurrencyConversion currencyConversion = responseEntity.getBody();
			return new CurrencyConversion(currencyConversion.getId(), from, to, quantity, 
					currencyConversion.getConversionMultiple(), 
					quantity.multiply(currencyConversion.getConversionMultiple()), 
					currencyConversion.getEnvironment()+" RestTemplate");
			
			
		}
		
		
		@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
		public CurrencyConversion calculateCurrencyConversionFeign(
				@PathVariable String from,
				@PathVariable String to,
				@PathVariable BigDecimal quantity
				) {
			
			LOG.info("Inside currency-conversion-service:calculateCurrencyConversionFeign()");
			CurrencyConversion currencyConversion = proxy.retriveExchangeValue(from, to);
			
			return new CurrencyConversion(currencyConversion.getId(), from, to, quantity, 
					currencyConversion.getConversionMultiple(), 
					quantity.multiply(currencyConversion.getConversionMultiple()), 
					currencyConversion.getEnvironment()+" feign client");
			
			
		}
	
}
