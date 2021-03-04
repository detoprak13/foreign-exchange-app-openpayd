package com.openpayd.foreignexchangeapp.dto.response;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class GetTransactionResponse {
	private Long id;
	private String sourceCurrencyCode;
	private String targetCurrency;
	private BigDecimal rate;
	private BigDecimal amountInSourceCurrency;
	private BigDecimal amountInTargetCurrency;
	private ZonedDateTime date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceCurrencyCode() {
		return sourceCurrencyCode;
	}

	public void setSourceCurrencyCode(String sourceCurrencyCode) {
		this.sourceCurrencyCode = sourceCurrencyCode;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getAmountInSourceCurrency() {
		return amountInSourceCurrency;
	}

	public void setAmountInSourceCurrency(BigDecimal amountInSourceCurrency) {
		this.amountInSourceCurrency = amountInSourceCurrency;
	}

	public BigDecimal getAmountInTargetCurrency() {
		return amountInTargetCurrency;
	}

	public void setAmountInTargetCurrency(BigDecimal amountInTargetCurrency) {
		this.amountInTargetCurrency = amountInTargetCurrency;
	}

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}
}
