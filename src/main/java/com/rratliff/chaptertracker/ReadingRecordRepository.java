package com.rratliff.chaptertracker;

import org.springframework.data.repository.CrudRepository;

public interface ReadingRecordRepository extends CrudRepository<ReadingRecord, Long> {

	Iterable<ReadingRecord> findAllByBook(Long bookId);

}
