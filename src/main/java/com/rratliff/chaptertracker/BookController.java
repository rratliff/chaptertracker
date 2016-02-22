package com.rratliff.chaptertracker;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createBook(@RequestBody Map<String, Object> bookMap) {
		Book book = new Book(bookMap.get("name").toString(), //
				Integer.parseInt(bookMap.get("sequence").toString()), //
				Integer.parseInt(bookMap.get("chapterCount").toString()));

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
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Book> getBooks() {
		return bookRepository.findAll();
	}
}
