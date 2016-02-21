package com.rratliff.chaptertracker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private int sequence;
	private int chapterCount;
	
	protected Book() {}
	
	public Book(String name, int sequence, int chapterCount) {
		this.name = name;
		this.sequence = sequence;
		this.chapterCount = chapterCount;
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

	@Override
	public String toString() {
		return String.format("Book [id=%s, name=%s, sequence=%s, chapterCount=%s]", id, name, sequence, chapterCount);
	}
	

}
