package com.rratliff.chaptertracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@ActiveProfiles({ "test" })
@WebIntegrationTest({ "server.port=8888" })
public class ReadingRecordControllerIT {

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ReadingRecordRepository readingRecordRepository;

	private RestTemplate restTemplate = new TestRestTemplate();

	private Book book;
	private Map<String, String> params;

	@Before
	public void before() {
		book = new Book("Leviticus", 27, 3);
		bookRepository.save(book);
		params = new HashMap<>();
		params.put("id", Long.toString(book.getId()));
	}

	@Test
	public void testGetReadingRecords_returnsList() throws Exception {
		Date date = new Date();
		ReadingRecord record = new ReadingRecord(book, date, 1);
		readingRecordRepository.save(record);

		@SuppressWarnings("unchecked")
		HashMap<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8888/book/{id}",
				HashMap.class, params);

		assertNotNull(apiResponse);
		@SuppressWarnings("unchecked")
		List<Object> readingRecords = (List<Object>) apiResponse.get("readingRecords");
		assertNotNull(readingRecords);
		assertEquals(1, readingRecords.size());
		@SuppressWarnings("unchecked")
		Map<String, Object> readingRecord = (Map<String, Object>) readingRecords.get(0);
		assertNotNull(readingRecord);
		assertNotNull(readingRecord.get("date"));
	}

	@Test
	public void testCreateReadingRecord_returnsReadingRecord() throws Exception {

		HttpEntity<String> httpEntity = jsonHttpPostEntity(readingRecordRequestBodyHelper(1));

		@SuppressWarnings("unchecked")
		HashMap<String, Object> apiResponse = restTemplate
				.postForObject("http://localhost:8888/book/{id}/readingRecord", httpEntity, HashMap.class, params);

		assertNotNull(apiResponse);
	}

	@Test
	public void testCreateReadingRecord_chapterNumberExceedsBook_returnsBadRequest() throws Exception {

		HttpEntity<String> httpEntity = jsonHttpPostEntity(readingRecordRequestBodyHelper(28));

		ResponseEntity<String> apiResponse = restTemplate.exchange("http://localhost:8888/book/{id}/readingRecord",
				HttpMethod.POST, httpEntity, String.class, params);

		assertNotNull(apiResponse);
		assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
	}

	/**
	 * Creating http entity object with request body. Assume it is a JSON body.
	 */
	private HttpEntity<String> jsonHttpPostEntity(Map<String, Object> requestBody) throws JsonProcessingException {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody),
				requestHeaders);
		return httpEntity;
	}

	/**
	 * Build request body data into a map.
	 */
	private Map<String, Object> readingRecordRequestBodyHelper(int chapterNumber) {
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("date", new Date());
		requestBody.put("chapterNumber", chapterNumber);
		return requestBody;
	}

}
