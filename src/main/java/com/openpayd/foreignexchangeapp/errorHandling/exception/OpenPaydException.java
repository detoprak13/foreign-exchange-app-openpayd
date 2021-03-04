package com.openpayd.foreignexchangeapp.errorHandling.exception;

import com.openpayd.foreignexchangeapp.errorHandling.enums.ExceptionType;

public class OpenPaydException extends RuntimeException {
	private ExceptionType type;

	public OpenPaydException() {
		super();
		this.type = ExceptionType.BAD_REQUEST;
	}

	public OpenPaydException(String message) {
		super(message);
		this.type = ExceptionType.BAD_REQUEST;
	}

	public OpenPaydException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}

	public OpenPaydException(Throwable cause, ExceptionType type) {
		super(cause == null ? new Throwable() : cause);
		this.type = type;
	}

	public ExceptionType getType() {
		return type;
	}

	public void setType(ExceptionType type) {
		this.type = type;
	}
}
