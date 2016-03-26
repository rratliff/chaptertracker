package com.rratliff.chaptertracker;

public class View {

	interface BookSummary {
	}

	interface CommonDetails extends BookSummary {
	}

	interface BookDetail extends CommonDetails {
	}

	interface ReadingRecordDetail extends CommonDetails {
	}

}
