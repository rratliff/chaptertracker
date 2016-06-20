package com.rratliff.chaptertracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@ActiveProfiles({ "test" })
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

		bookRepository.delete(bookId.longValue());
	}

	@Test
	public void testCreateBook_withMissingName_returnsBadRequest() throws Exception {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>("", requestHeaders);

		ResponseEntity<String> apiResponse = restTemplate.exchange("http://localhost:8888/book", HttpMethod.POST,
				httpEntity, String.class);

		assertNotNull(apiResponse);
		assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
	}

	@Test
	public void testCreateBook_withNegativeChapterCount_returnsBadRequest() throws Exception {
		// Building the Request body data
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("name", "Book 1");
		requestBody.put("chapterCount", -1);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		// Creating http entity object with request body and headers
		HttpEntity<String> httpEntity = new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody),
				requestHeaders);

		// Invoking the API
		ResponseEntity<String> apiResponse = restTemplate.exchange("http://localhost:8888/book", HttpMethod.POST,
				httpEntity, String.class);

		assertNotNull(apiResponse);
		assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
	}

	@Test
	public void testGetBooks_returnsList() throws Exception {
		Book book = new Book("Leviticus", 27, 3);
		bookRepository.save(book);

		@SuppressWarnings("unchecked")
		List<Book> apiResponse = restTemplate.getForObject("http://localhost:8888/book", List.class);

		assertNotNull(apiResponse);
		assertEquals(1, apiResponse.size());

		// Clean up
		bookRepository.delete(book);
	}

	@Test
	public void testGetBooks_excludesReadingRecords() throws Exception {
		Book book = new Book("Leviticus", 27, 3);
		bookRepository.save(book);

		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> apiResponse = restTemplate.getForObject("http://localhost:8888/book", List.class);

		assertNotNull(apiResponse);
		assertEquals(1, apiResponse.size());
		HashMap<String, Object> responseBook = apiResponse.get(0);
		assertEquals(book.getName(), responseBook.get("name"));
		assertNull(responseBook.get("readingRecords"));

		// Clean up
		bookRepository.delete(book);
	}

	@Test
	public void testGetBookDetail_includesReadingRecords() throws Exception {
		Book book = new Book("Leviticus", 27, 3);
		bookRepository.save(book);

		Map<String, String> params = new HashMap<>();
		params.put("id", Long.toString(book.getId()));

		Book apiResponse = restTemplate.getForObject("http://localhost:8888/book/{id}", Book.class, params);

		assertNotNull(apiResponse);
		assertNotNull(apiResponse.getReadingRecords());

		// Clean up
		bookRepository.delete(book);
	}

}
