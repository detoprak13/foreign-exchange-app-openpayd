package com.openpayd.foreignexchangeapp.errorHandling.exception;

import com.openpayd.foreignexchangeapp.errorHandling.enums.ExceptionType;

public class InvalidPageException extends OpenPaydException {
	public InvalidPageException(Throwable cause) {
		super(cause, ExceptionType.INVALID_PAGE_NO);
	}
}
