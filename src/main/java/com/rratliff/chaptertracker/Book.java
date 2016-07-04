package com.rratliff.chaptertracker;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;
import com.rratliff.chaptertracker.View.BookDetail;
import com.rratliff.chaptertracker.View.BookSummary;

@Entity
@Table(name = "t_book", //
		uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "chapterCount" }) })
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	@JsonView(BookSummary.class)
	private long id;

	@NotEmpty
	@JsonView(BookSummary.class)
	private String name;

	private int sequence;

	@Min(1)
	@JsonView(BookSummary.class)
	private int chapterCount;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonView(BookDetail.class)
	private List<ReadingRecord> readingRecords = new ArrayList<ReadingRecord>();

	protected Book() {
	}

	public Book(String name, int sequence, int chapterCount) {
		this.name = name;
		this.sequence = sequence;
		this.chapterCount = chapterCount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getChapterCount() {
		return chapterCount;
	}

	public void setChapterCount(int chapterCount) {
		this.chapterCount = chapterCount;
	}

	public List<ReadingRecord> getReadingRecords() {
		return readingRecords;
	}

	public void setReadingRecords(List<ReadingRecord> readingRecords) {
		this.readingRecords = readingRecords;
	}
}
