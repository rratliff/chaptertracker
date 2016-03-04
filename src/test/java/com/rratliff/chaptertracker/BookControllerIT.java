package com.rratliff.chaptertracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@WebIntegrationTest({ "server.port=8888" })
public class BookControllerIT {

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Autowired
	private BookRepository bookRepository;

	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testCreateBook_withValidData_returnsSuccess() throws Exception {

		// Building the Request body data
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("name", "Book 1");
		requestBody.put("chapterCount", 1);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		// Creating http entity object with request body and headers
		HttpEntity<String> httpEntity = new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody),
				requestHeaders);

		// Invoking the API
		@SuppressWarnings("unchecked")
		Map<String, Object> apiResponse = restTemplate.postForObject("http://localhost:8888/book", httpEntity,
				Map.class, Collections.emptyMap());

		assertNotNull(apiResponse);

		// Asserting the response of the API.
		String message = apiResponse.get("message").toString();
		assertEquals("Book created successfully", message);
		@SuppressWarnings("unchecked")
		Integer bookId = (Integer) ((Map<String, Object>) apiResponse.get("book")).get("id");

		assertNotNull(bookId);

		// Fetching the Book details directly from the DB to verify the API
		// succeeded
		Book bookFromDb = bookRepository.findOne(bookId.longValue());
		assertEquals("Book 1", bookFromDb.getName());
	}

}
