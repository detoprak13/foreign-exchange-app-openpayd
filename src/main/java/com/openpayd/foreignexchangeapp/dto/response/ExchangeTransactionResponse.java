package com.openpayd.foreignexchangeapp.dto.response;

import java.math.BigDecimal;

public class ExchangeTransactionResponse {
	private long id;
	private BigDecimal amountInTargetCurrency;
	private String targetCurrencyCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAmountInTargetCurrency() {
		return amountInTargetCurrency;
	}

	public void setAmountInTargetCurrency(BigDecimal amountInTargetCurrency) {
		this.amountInTargetCurrency = amountInTargetCurrency;
	}

	public String getTargetCurrencyCode() {
		return targetCurrencyCode;
	}

	public void setTargetCurrencyCode(String targetCurrencyCode) {
		this.targetCurrencyCode = targetCurrencyCode;
	}
}
