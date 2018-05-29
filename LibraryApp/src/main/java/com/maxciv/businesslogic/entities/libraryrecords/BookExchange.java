package com.maxciv.businesslogic.entities.libraryrecords;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.users.Reader;

import java.util.Date;

public class BookExchange extends AbstractBookRecord {

    private Reader owner;
    private Date openExchangeDate = null;
    private Reader reader = null;

    public BookExchange(int id, Book book, Reader owner) {
        super(id, book);
        this.owner = owner;
    }

    public BookExchange(int id, Book book, Reader owner, Date openExchangeDate) {
        this(id, book, owner);
        this.openExchangeDate = openExchangeDate;
    }

    public BookExchange(int id, Book book, Reader owner, Date openExchangeDate, Reader reader, Date startDate, Date endDate) {
        this(id, book, owner, openExchangeDate);
        this.reader = reader;
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    public Reader getOwner() {
        return owner;
    }

    public void setOwner(Reader owner) {
        this.owner = owner;
    }

    public Date getOpenExchangeDate() {
        return openExchangeDate;
    }

    public void setOpenExchangeDate(Date openExchangeDate) {
        this.openExchangeDate = openExchangeDate;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
