package com.currencyconversion.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.currencyconversion.entity.CurrencyConversion;


// http://localhost:8000/currency-exchange/from/USD/to/INR

//@FeignClient(name = "currency-exchange-service", url = "localhost:8000", path = "/currency-exchange")
@FeignClient(name = "currency-exchange-service", path = "/currency-exchange")
public interface CurrencyExchangeProxy {
	
	@GetMapping("/from/{from}/to/{to}")
	public CurrencyConversion retriveExchangeValue(@PathVariable String from, @PathVariable String to);

}
