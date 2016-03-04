package com.rratliff.chaptertracker;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
			@RequestBody Map<String, Object> readingRecordMap) {
		// book
		Book book = bookRepository.findOne(bookId);
		// date
		Date recordDate = new Date();
		ReadingRecord readingRecord = new ReadingRecord(book, recordDate, //
				Integer.parseInt(readingRecordMap.get("chapterNumber").toString()));

		readingRecordRepository.save(readingRecord);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Reading record created successfully");
		response.put("readingRecord", readingRecord);
		return response;
	}

}
