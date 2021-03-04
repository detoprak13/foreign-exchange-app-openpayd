package com.openpayd.foreignexchangeapp.service;

import com.openpayd.foreignexchangeapp.dto.response.ExchangeTransactionResponse;
import com.openpayd.foreignexchangeapp.entity.Transaction;
import com.openpayd.foreignexchangeapp.enums.TransactionSortParam;
import com.openpayd.foreignexchangeapp.exhangeRateApi.ExchangeApi;
import com.openpayd.foreignexchangeapp.repository.TransactionRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.List;

@Service
public class ExchangeService {
	private final ExchangeApi exchangeRateSource;
	private final TransactionRepository transactionRepository;

	public ExchangeService(ExchangeApi exchangeApi, TransactionRepository transactionRepository) {
		this.exchangeRateSource = exchangeApi;
		this.transactionRepository = transactionRepository;
	}

	public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) {
		return exchangeRateSource.getExchangeRate(sourceCurrency,targetCurrency);
	}

	public Transaction exchange(Currency sourceCurrency, Currency targetCurrency, BigDecimal amount) {
		BigDecimal rate = getExchangeRate(sourceCurrency, targetCurrency);
		Transaction transaction = new Transaction();
		transaction.setDate(ZonedDateTime.now());
		transaction.setAmountInSourceCurrency(amount);
		transaction.setAmountInTargetCurrency(amount.multiply(rate));
		transaction.setSourceCurrency(sourceCurrency);
		transaction.setTargetCurrency(targetCurrency);
		transaction.setRate(rate);
		return transactionRepository.save(transaction);
	}

	public Slice<Transaction> getAllTransactions(int pageNo, Sort.Direction direction, TransactionSortParam sortParam,
	                                             List<Long> idList, ZonedDateTime dateStart, ZonedDateTime dateEnd) {
		Pageable pageable = PageRequest.of(pageNo, 5, Sort.by(direction, sortParam.name().toLowerCase()));
		return transactionRepository.findAllByFilters(idList, dateStart, dateEnd, pageable);
	}
}
