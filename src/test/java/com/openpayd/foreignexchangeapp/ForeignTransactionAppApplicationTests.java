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
				.param("pageNo", "0")
				.param("sortParam", "id")
				.param("dateStart", "2021-03-02")
				.param("dateEnd", "2021-03-02");

		mockMvc.perform(builder)
				.andExpect(status().isOk())
				.andDo(document("all-transactions"));
	}

	@Test
	void getAllTransactions_invalidDate() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/exchanges")
				.param("sortDirection", "DESC")
				.param("pageNo", "0")
				.param("sortParam", "id")
				.param("dateStart", "2021-03-02")
				.param("dateEnd", "2021-03-023");

		mockMvc.perform(builder)
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exceptionType", is(ExceptionType.INVALID_DATE.name())))
				.andDo(document("all-transactions-invalid-date"));
	}

	@Test
	void getAllTransactions_invalidPageNo() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/exchanges")
				.param("sortDirection", "DESC")
				.param("pageNo", "-1")
				.param("sortParam", "id")
				.param("dateStart", "2021-03-02")
				.param("dateEnd", "2021-03-02");

		mockMvc.perform(builder)
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exceptionType", is(ExceptionType.INVALID_PAGE_NO.name())))
				.andDo(document("all-transactions-invalid-pageNo"));
	}

	@Test
	void getAllTransactions_invalidSortDirection() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/exchanges")
				.param("sortDirection", "DESCA")
				.param("pageNo", "0")
				.param("sortParam", "id")
				.param("dateStart", "2021-03-02")
				.param("dateEnd", "2021-03-02");

		mockMvc.perform(builder)
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exceptionType", is(ExceptionType.INVALID_SORT_DIRECTION.name())))
				.andDo(document("all-transactions-invalid-sort-direction"));
	}

	@Test
	void getAllTransactions_invalidSortParam() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/exchanges")
				.param("sortDirection", "DESC")
				.param("pageNo", "0")
				.param("sortParam", "ida")
				.param("dateStart", "2021-03-02")
				.param("dateEnd", "2021-03-02");

		mockMvc.perform(builder)
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exceptionType", is(ExceptionType.INVALID_SORT_PARAM.name())))
				.andDo(document("all-transactions-invalid-sort-param"));
	}

	@Test
	void exchange_valid() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/exchanges")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"sourceCurrencyCode\": \"TRY\", \"targetCurrencyCode\": \"EUR\", \"amount\": \"100\" }") ;

		mockMvc.perform(builder)
				.andExpect(status().isOk())
				.andDo(document("exchange_valid"));
	}

	@Test
	void exchange_invalidCurrency() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/exchanges")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"sourceCurrencyCode\": \"TRYO\", \"targetCurrencyCode\": \"EUR\", \"amount\": \"100\" }") ;

		mockMvc.perform(builder)
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exceptionType", is(ExceptionType.INVALID_CURRENCY.name())))
				.andDo(document("exchange_invalid_currency"));
	}

	@Test
	void exchange_invalidAmount() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/exchanges")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"sourceCurrencyCode\": \"TRY\", \"targetCurrencyCode\": \"EUR\", \"amount\": \"-5\" }") ;

		mockMvc.perform(builder)
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exceptionType", is(ExceptionType.INVALID_EXCHANGE_AMOUNT.name())))
				.andDo(document("exchange_invalid_amount"));
	}
}
