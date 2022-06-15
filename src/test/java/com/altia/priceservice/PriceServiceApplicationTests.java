package com.altia.priceservice;

import com.altia.priceservice.model.ProductPrice;
import com.google.gson.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.altia.priceservice.model.Constants.DATE_TIME_FORMAT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

		MvcResult result = mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-14-10.00.00")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		ProductPrice response = gson.fromJson(content, ProductPrice.class);

		Assertions.assertEquals(0, response.getId());
	}

	@Test
	public void testCase2() throws Exception {

		MvcResult result = mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-14-16.00.00")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		ProductPrice response = gson.fromJson(content, ProductPrice.class);

		Assertions.assertEquals(1, response.getId());
	}

	@Test
	public void testCase3() throws Exception {

		MvcResult result = mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-14-21.00.00")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		ProductPrice response = gson.fromJson(content, ProductPrice.class);

		Assertions.assertEquals(0, response.getId());
	}

	@Test
	public void testCase4() throws Exception {

		MvcResult result = mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-15-10.00.00")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		ProductPrice response = gson.fromJson(content, ProductPrice.class);

		Assertions.assertEquals(2, response.getId());
	}

	@Test
	public void testCase5() throws Exception {

		MvcResult result = mvc.perform(get("/product-prices")
						.queryParam("dateTime", "2020-06-16-21.00.00")
						.queryParam("productId", "35455")
						.queryParam("brandId", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		ProductPrice response = gson.fromJson(content, ProductPrice.class);

		Assertions.assertEquals(3, response.getId());
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
