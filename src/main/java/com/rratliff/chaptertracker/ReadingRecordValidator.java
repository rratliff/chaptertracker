package com.rratliff.chaptertracker;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ReadingRecordValidator implements Validator {

	@Override
	public boolean supports(Class<?> klass) {
		return ReadingRecord.class.equals(klass);
	}

	@Override
	public void validate(Object target, Errors result) {
		ValidationUtils.rejectIfEmpty(result, "book", "field.required");
		ReadingRecord readingRecord = (ReadingRecord) target;
		if (readingRecord.getBook().getChapterCount() < readingRecord.getChapterNumber()) {
			result.rejectValue("chapterNumber", "chapternumber.invalid");
		}
	}

}
