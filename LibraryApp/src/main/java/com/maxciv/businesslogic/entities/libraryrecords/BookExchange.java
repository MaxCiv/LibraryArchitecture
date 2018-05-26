package com.maxciv.businesslogic.entities.libraryrecords;

import com.maxciv.businesslogic.entities.Book;

public class BookExchange extends AbstractBookRecord {

    private int ownerId;
    private int readerId;

    public BookExchange(int id, Book book, int ownerId) {
        super(id, book);
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }
}
