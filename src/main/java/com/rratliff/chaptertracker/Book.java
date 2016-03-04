package com.rratliff.chaptertracker;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(View.Summary.class)
	private long id;

	@NotEmpty
	@JsonView(View.Summary.class)
	private String name;

	@JsonView(View.Summary.class)
	private int sequence;

	@Min(1)
	@JsonView(View.Summary.class)
	private int chapterCount;

	@OneToMany
	private List<ReadingRecord> readingRecords;

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

	@Override
	public String toString() {
		return String.format("Book [id=%s, name=%s, sequence=%s, chapterCount=%s]", id, name, sequence, chapterCount);
	}

}
