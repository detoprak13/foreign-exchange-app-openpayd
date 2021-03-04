package com.openpayd.foreignexchangeapp.errorHandling.dto;

import com.openpayd.foreignexchangeapp.errorHandling.enums.ExceptionType;

import java.io.Serializable;

public class BadRequestResponse implements Serializable {
	private ExceptionType exceptionType;
	private String message;

	public ExceptionType getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(ExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
