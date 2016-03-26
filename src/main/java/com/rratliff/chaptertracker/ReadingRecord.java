package com.rratliff.chaptertracker;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.rratliff.chaptertracker.View.CommonDetails;
import com.rratliff.chaptertracker.View.ReadingRecordDetail;

@Entity
@Table(name = "t_reading_record")
public class ReadingRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reading_record_id")
	@JsonView(CommonDetails.class)
	private long id;

	@ManyToOne
	@JsonView(ReadingRecordDetail.class)
	private Book book;

	@NotNull
	@JsonView(CommonDetails.class)
	private Date date;

	@Min(1)
	@JsonView(CommonDetails.class)
	private int chapterNumber;

	protected ReadingRecord() {
	}

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
}
