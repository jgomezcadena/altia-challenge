package com.altia.priceservice;

import com.google.gson.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.altia.priceservice.model.Constants.DATE_TIME_FORMAT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PriceServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	private static Gson gson;

	@BeforeAll
	static void init() {
		gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
			}
		}).create();
	}

	@Test
	public void testCase1() throws Exception {

		// Returns productPrice entry with id = 0

		mvc.perform(get("/product-prices")
				.queryParam("dateTime", "2020-06-14-10.00.00")
				.queryParam("productId", "35455")
				.queryParam("brandId", "1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.brandId").value("1"))
			.andExpect(jsonPath("$.productId").value("35455"))
			.andExpect(jsonPath("$.startDate").value("2020-06-14-00.00.00"))
			.andExpect(jsonPath("$.endDate").value("2020-12-31-23.59.59"))
			.andExpect(jsonPath("$.tariffId").value("1"))
			.andExpect(jsonPath("$.price").value("35.5"))
			.andExpect(jsonPath("$.currency").value("EUR"));
	}

	@Test
	public void testCase2() throws Exception {

		// Returns productPrice entry with id = 1

		mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-14-16.00.00")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.brandId").value("1"))
				.andExpect(jsonPath("$.productId").value("35455"))
				.andExpect(jsonPath("$.startDate").value("2020-06-14-15.00.00"))
				.andExpect(jsonPath("$.endDate").value("2020-06-14-18.30.00"))
				.andExpect(jsonPath("$.tariffId").value("2"))
				.andExpect(jsonPath("$.price").value("25.45"))
				.andExpect(jsonPath("$.currency").value("EUR"));
	}

	@Test
	public void testCase3() throws Exception {

		// Returns productPrice entry with id = 0

		mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-14-21.00.00")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.brandId").value("1"))
				.andExpect(jsonPath("$.productId").value("35455"))
				.andExpect(jsonPath("$.startDate").value("2020-06-14-00.00.00"))
				.andExpect(jsonPath("$.endDate").value("2020-12-31-23.59.59"))
				.andExpect(jsonPath("$.tariffId").value("1"))
				.andExpect(jsonPath("$.price").value("35.5"))
				.andExpect(jsonPath("$.currency").value("EUR"));
	}

	@Test
	public void testCase4() throws Exception {

		// Returns productPrice entry with id = 2

		mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-15-10.00.00")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.brandId").value("1"))
				.andExpect(jsonPath("$.productId").value("35455"))
				.andExpect(jsonPath("$.startDate").value("2020-06-15-00.00.00"))
				.andExpect(jsonPath("$.endDate").value("2020-06-15-11.00.00"))
				.andExpect(jsonPath("$.tariffId").value("3"))
				.andExpect(jsonPath("$.price").value("30.5"))
				.andExpect(jsonPath("$.currency").value("EUR"));
	}

	@Test
	public void testCase5() throws Exception {

		// Returns productPrice entry with id = 3

		mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-16-21.00.00")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.brandId").value("1"))
				.andExpect(jsonPath("$.productId").value("35455"))
				.andExpect(jsonPath("$.startDate").value("2020-06-15-16.00.00"))
				.andExpect(jsonPath("$.endDate").value("2020-12-31-23.59.59"))
				.andExpect(jsonPath("$.tariffId").value("4"))
				.andExpect(jsonPath("$.price").value("38.95"))
				.andExpect(jsonPath("$.currency").value("EUR"));
	}

	@Test
	public void testCaseInvalidParams() throws Exception {

		mvc.perform(get("/product-prices")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		mvc.perform(get("/product-prices")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-16-21.00.00")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		mvc.perform(get("/product-prices")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCaseNoContent() throws Exception {

		mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-15-10.00.00")
						.queryParam("productId", "123456")
						.queryParam("brandId", "6")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
}
