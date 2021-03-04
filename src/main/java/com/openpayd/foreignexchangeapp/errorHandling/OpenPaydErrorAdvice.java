package com.openpayd.foreignexchangeapp.errorHandling;

import com.openpayd.foreignexchangeapp.errorHandling.dto.BadRequestResponse;
import com.openpayd.foreignexchangeapp.errorHandling.enums.ExceptionType;
import com.openpayd.foreignexchangeapp.errorHandling.exception.OpenPaydException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class OpenPaydErrorAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(OpenPaydException.class)
	public BadRequestResponse handleOpenPaydExceptions(OpenPaydException exception) {
		BadRequestResponse badRequestResponse = new BadRequestResponse();
		badRequestResponse.setExceptionType(exception.getType());
		badRequestResponse.setMessage(exception.getMessage());
		return badRequestResponse;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public BadRequestResponse handleInvalidFormatException(IllegalArgumentException exception) {
		BadRequestResponse badRequestResponse = new BadRequestResponse();
		badRequestResponse.setMessage(exception.getMessage());
		if (exception.getCause() != null) {
			if (exception.getCause().getClass().equals(DateTimeParseException.class)) {
				badRequestResponse.setExceptionType(ExceptionType.INVALID_DATE);
			}
		}
		if (exception instanceof NumberFormatException) {
			badRequestResponse.setExceptionType(ExceptionType.INVALID_NUMBER);
		}
		return badRequestResponse;
	}
}
