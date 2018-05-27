package com.maxciv.businesslogic.entities.libraryrecords;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.users.Reader;

import java.util.Date;

public class BookBorrow extends AbstractBookRecord {

    private Reader reader;

    public BookBorrow(int id, Book book, Reader reader, Date startDate) {
        super(id, book);
        this.reader = reader;
        this.setStartDate(startDate);
    }

    public BookBorrow(int id, Book book, Reader reader, Date startDate, Date endDate) {
        this(id, book, reader, startDate);
        this.setEndDate(endDate);
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
