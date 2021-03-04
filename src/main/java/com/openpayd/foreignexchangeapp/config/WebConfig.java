package com.openpayd.foreignexchangeapp.config;

import com.openpayd.foreignexchangeapp.util.SortDirectionEnumConverter;
import com.openpayd.foreignexchangeapp.util.TransactionSortParamEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new TransactionSortParamEnumConverter());
		registry.addConverter(new SortDirectionEnumConverter());
	}
}
