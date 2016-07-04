package com.rratliff.chaptertracker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@ActiveProfiles({ "test" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, //
		TransactionalTestExecutionListener.class })
public class BookRepositoryTest {

	@Autowired
	private BookRepository repository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void bookModelHasUniqueConstraint() {
		Book book = new Book("Leviticus", 27, 3);
		repository.save(book);
		Book book2 = new Book("Leviticus", 27, 3);
		thrown.expect(DataIntegrityViolationException.class);
		repository.save(book2);
	}
}
