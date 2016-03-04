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

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createBook(@Valid @RequestBody Book book) {

		bookRepository.save(book);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Book created successfully");
		response.put("book", book);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
	public Book getBookDetails(@PathVariable("bookId") Long bookId) {
		return bookRepository.findOne(bookId);
	}

	@JsonView(View.Summary.class)
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Book> getBooks() {
		return bookRepository.findAll();
	}
}
