package com.maxciv.businesslogic.entities.libraryrecords;

import com.maxciv.businesslogic.entities.Book;

import java.util.Date;

public class AbstractBookRecord implements BookRecord {

    private int id;
    private Book book;
    private Date startDate;
    private Date endDate;

    public AbstractBookRecord(int id, Book book) {
        this.id = id;
        this.book = book;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
