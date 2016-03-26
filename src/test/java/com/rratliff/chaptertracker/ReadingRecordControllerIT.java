package com.rratliff.chaptertracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

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

	@Test
	public void testGetReadingRecords_returnsList() throws Exception {
		Book book = new Book("Leviticus", 27, 3);
		bookRepository.save(book);
		Date date = new Date();
		ReadingRecord record = new ReadingRecord(book, date, 1);
		readingRecordRepository.save(record);

		Map<String, String> params = new HashMap<>();
		params.put("id", Long.toString(book.getId()));

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

}
