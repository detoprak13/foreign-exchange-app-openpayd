package com.openpayd.foreignexchangeapp.dto.response;

import java.math.BigDecimal;

public class GetExchangeRateResponse {
	private BigDecimal rate;

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
