package com.openpayd.foreignexchangeapp;

import com.openpayd.foreignexchangeapp.errorHandling.enums.ExceptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
@ExtendWith({RestDocumentationExtension.class,SpringExtension.class})
class ForeignTransactionAppApplicationTests {
	private MockMvc mockMvc;

	@BeforeEach
	public void setUp(WebApplicationContext context,
	                  RestDocumentationContextProvider restDocumentation) {

		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation)
						.snippets()
						.withEncoding("UTF-8"))
				.build();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void getExchangeRate_valid() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/exchanges/rate")
				.param("sourceCurrencyCode", "EUR")
				.param("targetCurrencyCode", "TRY");

		mockMvc.perform(builder)
				.andExpect(status().isOk())
				.andDo(document("exchangeRate"));
	}

	@Test
	void getExchangeRate_invalidCurrency() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/exchanges/rate")
				.param("sourceCurrencyCode", "EURo")
				.param("targetCurrencyCode", "TRY");

		mockMvc.perform(builder)
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exceptionType", is(ExceptionType.INVALID_CURRENCY.name())))
				.andDo(document("exchangeRate-invalid-currency"));
	}

	@Test
	void getAllTransactions_valid() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/exchanges")
				.param("sortDirection", "DESC")
				.param("sortDirection", "0")
				.param("sortParam", "id")
				.param("dateStart", "2021-03-02")
				.param("dateEnd", "2021-03-02");

		mockMvc.perform(builder)
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

}
