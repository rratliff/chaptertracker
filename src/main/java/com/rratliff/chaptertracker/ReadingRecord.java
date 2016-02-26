package com.rratliff.chaptertracker;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ReadingRecord {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private Book book;
	
	private Date date;
	private int chapterNumber;
	
	protected ReadingRecord() {}
	
	public ReadingRecord(Book book, Date date, int chapterNumber) {
		this.book = book;
		this.date = date;
		this.chapterNumber = chapterNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getChapterNumber() {
		return chapterNumber;
	}

	public void setChapterNumber(int chapterNumber) {
		this.chapterNumber = chapterNumber;
	}

	@Override
	public String toString() {
		return String.format("ReadingRecord [id=%s, book=%s, date=%s, chapterNumber=%s]", id, book, date,
				chapterNumber);
	}
}
