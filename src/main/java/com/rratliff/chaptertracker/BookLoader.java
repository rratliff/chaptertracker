package com.rratliff.chaptertracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BookLoader implements ApplicationListener<ContextRefreshedEvent> {

	private BookRepository bookRepository;

	@Autowired
	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Book[] books = { //
				new Book("Genesis", 1, 50), //
				new Book("Exodus", 2, 40) //
		};
		for (Book book : books) {
			bookRepository.save(book);
		}
	}

}
