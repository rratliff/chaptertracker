package com.rratliff.chaptertracker;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.rratliff.chaptertracker.View.ReadingRecordDetail;

@RestController
@RequestMapping(value = "/book/{bookId}/readingRecord")
public class ReadingRecordController {

	@Autowired
	ReadingRecordRepository readingRecordRepository;

	@Autowired
	BookRepository bookRepository;

	@RequestMapping(method = RequestMethod.POST)
	@JsonView(ReadingRecordDetail.class)
	public Map<String, Object> createReadingRecord(@PathVariable("bookId") Long bookId,
			@Valid @RequestBody ReadingRecord readingRecord, BindingResult result) {
		Book book = bookRepository.findOne(bookId);
		readingRecord.setBook(book);

		ReadingRecordValidator rrValidator = new ReadingRecordValidator();
		rrValidator.validate(readingRecord, result);

		if (result.hasErrors()) {
			throw new BadRequestException();
		}

		readingRecordRepository.save(readingRecord);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Reading record created successfully");
		response.put("readingRecord", readingRecord);
		return response;
	}
}
