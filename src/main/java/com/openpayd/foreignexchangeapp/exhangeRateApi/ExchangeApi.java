package com.openpayd.foreignexchangeapp.exhangeRateApi;

import com.openpayd.foreignexchangeapp.exhangeRateApi.dto.ExchangeRateApiResponse;
import com.openpayd.foreignexchangeapp.errorHandling.exception.ExchangeRateApiException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Currency;

public class ExchangeApi {
	private RestTemplate restTemplate;
	private String baseUrl;

	public ExchangeApi(RestTemplate restTemplate, String baseUrl) {
		this.restTemplate = restTemplate;
		this.baseUrl = baseUrl;
	}

	public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) {
		try {
			UriComponents uriComponents =  UriComponentsBuilder.newInstance()
					.scheme("https").host(baseUrl).path("/api/latest")
					.queryParam("base", sourceCurrency.getCurrencyCode())
					.queryParam("symbols", targetCurrency.getCurrencyCode()).build();

			ExchangeRateApiResponse response = restTemplate.getForObject(uriComponents.toUriString(), ExchangeRateApiResponse.class);
			return response.getRates().get(targetCurrency);
		} catch (Exception e) {
			throw new ExchangeRateApiException(e.getCause());
		}
	}
}
