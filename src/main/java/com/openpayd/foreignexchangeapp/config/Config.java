package com.openpayd.foreignexchangeapp.config;

import com.openpayd.foreignexchangeapp.exhangeRateApi.ExchangeApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	@Value("${rates.api.base.url}")
	String ratesApiBaseUrl;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ExchangeApi exchangeSource(RestTemplate restTemplate) {
		return new ExchangeApi(restTemplate, ratesApiBaseUrl);
	}
}
