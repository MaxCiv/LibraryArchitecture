package com.maxciv.businesslogic.entities.libraryrecords;

import com.maxciv.businesslogic.entities.Book;

public class BookBorrow extends AbstractBookRecord {

    private int readerId;

    public BookBorrow(int id, Book book, int readerId) {
        super(id, book);
        this.readerId = readerId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }
}
