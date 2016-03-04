package com.rratliff.chaptertracker;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/book/{bookId}/readingRecord")
public class ReadingRecordController {

	@Autowired
	ReadingRecordRepository readingRecordRepository;

	@Autowired
	BookRepository bookRepository;

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createReadingRecord(@PathVariable("bookId") Long bookId,
			@Valid @RequestBody ReadingRecord readingRecord) {
		Book book = bookRepository.findOne(bookId);
		readingRecord.setBook(book);

		readingRecordRepository.save(readingRecord);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Reading record created successfully");
		response.put("readingRecord", readingRecord);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<ReadingRecord> getReadingRecordsByBook(@PathVariable("bookId") Long bookId) {
		return readingRecordRepository.findAllByBook(bookId);
	}

}
