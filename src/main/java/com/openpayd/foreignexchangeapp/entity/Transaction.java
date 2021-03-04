package com.openpayd.foreignexchangeapp.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;

@Entity(name="transactions")
public class Transaction {
	@Id
	@GeneratedValue
	private Long id;
	private Currency sourceCurrency;
	private Currency targetCurrency;
	private BigDecimal rate;
	private BigDecimal amountInSourceCurrency;
	private BigDecimal amountInTargetCurrency;
	private ZonedDateTime date;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "source_currency")
	public Currency getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(Currency sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	@Column(name = "target_currency")
	public Currency getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(Currency targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	@Column(name = "rate")
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Column(name = "amount_in_source_currency")
	public BigDecimal getAmountInSourceCurrency() {
		return amountInSourceCurrency;
	}

	public void setAmountInSourceCurrency(BigDecimal amountInSourceCurrency) {
		this.amountInSourceCurrency = amountInSourceCurrency;
	}

	@Column(name = "amount_in_target_currency")
	public BigDecimal getAmountInTargetCurrency() {
		return amountInTargetCurrency;
	}

	public void setAmountInTargetCurrency(BigDecimal amountInTargetCurrency) {
		this.amountInTargetCurrency = amountInTargetCurrency;
	}

	@Column(name = "date")
	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}
}
