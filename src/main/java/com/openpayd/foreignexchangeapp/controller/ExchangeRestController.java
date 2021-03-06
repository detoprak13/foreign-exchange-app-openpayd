package com.openpayd.foreignexchangeapp.controller;

import com.openpayd.foreignexchangeapp.dto.request.ExchangeTransactionRequest;
import com.openpayd.foreignexchangeapp.dto.response.ExchangeTransactionResponse;
import com.openpayd.foreignexchangeapp.dto.response.GetExchangeRateResponse;
import com.openpayd.foreignexchangeapp.dto.response.GetTransactionResponse;
import com.openpayd.foreignexchangeapp.dto.response.PagedResponse;
import com.openpayd.foreignexchangeapp.entity.Transaction;
import com.openpayd.foreignexchangeapp.enums.TransactionSortParam;
import com.openpayd.foreignexchangeapp.errorHandling.enums.ExceptionType;
import com.openpayd.foreignexchangeapp.errorHandling.exception.InvalidCurrencyException;
import com.openpayd.foreignexchangeapp.errorHandling.exception.InvalidPageException;
import com.openpayd.foreignexchangeapp.errorHandling.exception.OpenPaydException;
import com.openpayd.foreignexchangeapp.service.ExchangeService;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@RestController("")
@RequestMapping("exchanges")
public class ExchangeRestController {
	private ExchangeService exchangeService;

	public ExchangeRestController(ExchangeService exchangeService) {
		this.exchangeService = exchangeService;
	}

	@GetMapping("/rate")
	public GetExchangeRateResponse getExchangeRate(@RequestParam("sourceCurrencyCode") String sourceCurrencyCode, @RequestParam("targetCurrencyCode") String targetCurrencyCode) {
		BigDecimal rate = exchangeService.getExchangeRate(getCurrencyFromCurrencyCode(sourceCurrencyCode), getCurrencyFromCurrencyCode(targetCurrencyCode));
		GetExchangeRateResponse getExchangeRateResponse = new GetExchangeRateResponse();
		getExchangeRateResponse.setRate(rate);
		return getExchangeRateResponse;
	}

	@PostMapping
	public ExchangeTransactionResponse exchange(@RequestBody ExchangeTransactionRequest exchangeTransactionRequest) {
		if (exchangeTransactionRequest.getAmount().compareTo(new BigDecimal(1)) < 0) {
			throw new OpenPaydException("Amount cannot be less than 1.", ExceptionType.INVALID_EXCHANGE_AMOUNT);
		}
		Transaction transaction = exchangeService.exchange(getCurrencyFromCurrencyCode(exchangeTransactionRequest.getSourceCurrencyCode()),
				getCurrencyFromCurrencyCode(exchangeTransactionRequest.getTargetCurrencyCode()), exchangeTransactionRequest.getAmount());

		ExchangeTransactionResponse exchangeTransactionResponse = new ExchangeTransactionResponse();
		exchangeTransactionResponse.setId(transaction.getId());
		exchangeTransactionResponse.setAmountInTargetCurrency(transaction.getAmountInTargetCurrency());
		exchangeTransactionResponse.setTargetCurrencyCode(transaction.getTargetCurrency().getCurrencyCode());
		return exchangeTransactionResponse;
	}

	@GetMapping
	public PagedResponse<GetTransactionResponse> getAllTransactions(@RequestParam("sortDirection") Optional<Sort.Direction> sortDirection,
	                                                                @RequestParam("pageNo") Optional<Integer> pageNo,
	                                                                @RequestParam("sortParam") Optional<TransactionSortParam> sortParam,
	                                                                @RequestParam("idList") Optional<List<Long>> idList,
	                                                                @RequestParam("dateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> dateStart,
	                                                                @RequestParam("dateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> dateEnd) {
		TransactionSortParam currentSortParam = sortParam.orElse(TransactionSortParam.DATE);
		int currentPageNo = pageNo.orElse(0);

		if (currentPageNo < 0) {
			throw new InvalidPageException(null);
		}

		Sort.Direction currentSortDirection = sortDirection.orElse(Sort.Direction.DESC);
		List<Long> currentIdList = idList.orElse(null);

		ZonedDateTime currentDateStart = null;
		ZonedDateTime currentDateEnd = null;
		if (dateStart.isPresent() && dateEnd.isPresent()) {
			currentDateStart = dateStart.orElse(null).atStartOfDay(ZoneId.of("Europe/Istanbul"));
			currentDateEnd = dateEnd.orElse(null).atStartOfDay(ZoneId.of("Europe/Istanbul"));
		}

		Slice<Transaction> transactionsSlice = exchangeService.getAllTransactions(currentPageNo, currentSortDirection, currentSortParam, currentIdList,
				currentDateStart, currentDateEnd);

		PagedResponse<GetTransactionResponse> response = new PagedResponse<>();
		response.setContent(new ArrayList<>());
		for (Transaction transaction : transactionsSlice.getContent()) {
			GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
			getTransactionResponse.setId(transaction.getId());
			getTransactionResponse.setRate(transaction.getRate());
			getTransactionResponse.setDate(transaction.getDate());
			getTransactionResponse.setAmountInSourceCurrency(transaction.getAmountInSourceCurrency());
			getTransactionResponse.setTargetCurrency(transaction.getTargetCurrency().getCurrencyCode());
			getTransactionResponse.setSourceCurrencyCode(transaction.getSourceCurrency().getCurrencyCode());
			getTransactionResponse.setAmountInTargetCurrency(transaction.getAmountInTargetCurrency());
			response.getContent().add(getTransactionResponse);
		}
		response.setHasNext(transactionsSlice.hasNext());
		return response;
	}

	private Currency getCurrencyFromCurrencyCode(String currencyCode) {
		Currency currency;
		try {
			currency = Currency.getInstance(currencyCode);
		} catch (Exception e) {
			throw new InvalidCurrencyException("invalid currency code: " + currencyCode);
		}
		return currency;
	}
}
