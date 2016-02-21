package com.rratliff.chaptertracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
	private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
		SpringApplication.run(App.class);
    }
    
	@Bean
	public CommandLineRunner demo(BookRepository repository) {
		return (args) -> {
			// save a couple of books
			repository.save(new Book("Genesis", 1, 50));
			repository.save(new Book("Exodus", 2, 40));

			log.info("Books found with findAll");
			for (Book book : repository.findAll()) {
				log.info(book.toString());
			}
		};
	}
}
