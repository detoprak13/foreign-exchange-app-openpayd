package com.openpayd.foreignexchangeapp.errorHandling.exception;

import com.openpayd.foreignexchangeapp.errorHandling.enums.ExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCurrencyException extends OpenPaydException {
	public InvalidCurrencyException(Throwable cause) {
		super(cause, ExceptionType.INVALID_CURRENCY);
	}
	public InvalidCurrencyException(String message) {
		super(message, ExceptionType.INVALID_CURRENCY);
	}
}
