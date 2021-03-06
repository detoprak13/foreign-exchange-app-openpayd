package com.openpayd.foreignexchangeapp.util;

import com.openpayd.foreignexchangeapp.errorHandling.enums.ExceptionType;
import com.openpayd.foreignexchangeapp.errorHandling.exception.OpenPaydException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;

public class SortDirectionEnumConverter implements Converter<String, Sort.Direction> {
	@Override
	public Sort.Direction convert(String source) {
		try {
			return Sort.Direction.valueOf(source.toUpperCase());
		} catch (Exception e) {
			throw new OpenPaydException(source + " is not a valid type of Sort.Direction", ExceptionType.INVALID_SORT_DIRECTION);
		}
	}
}
