package com.openpayd.foreignexchangeapp.util;

import com.openpayd.foreignexchangeapp.enums.TransactionSortParam;
import com.openpayd.foreignexchangeapp.errorHandling.enums.ExceptionType;
import com.openpayd.foreignexchangeapp.errorHandling.exception.OpenPaydException;
import org.springframework.core.convert.converter.Converter;

public class TransactionSortParamEnumConverter implements Converter<String, TransactionSortParam> {
	@Override
	public TransactionSortParam convert(String source) {
		try {
			return TransactionSortParam.valueOf(source.toUpperCase());
		} catch (Exception e) {
			throw new OpenPaydException(source + " is not a valid type of TransactionSortParam", ExceptionType.INVALID_SORT_PARAM);
		}
	}
}
