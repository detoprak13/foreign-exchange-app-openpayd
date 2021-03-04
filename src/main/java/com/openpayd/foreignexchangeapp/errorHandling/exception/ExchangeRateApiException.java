package com.openpayd.foreignexchangeapp.errorHandling.exception;

import com.openpayd.foreignexchangeapp.errorHandling.enums.ExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExchangeRateApiException extends OpenPaydException {
	public ExchangeRateApiException(Throwable cause) {
		super(cause, ExceptionType.EXCHANGE_RATE_API);
	}
}
