package com.maxciv.businesslogic.entities.libraryrecords;

import com.maxciv.businesslogic.entities.Book;

public class BookOrder extends AbstractBookRecord {

    private int supplierId;

    public BookOrder(int id, Book book, int supplierId) {
        super(id, book);
        this.supplierId = supplierId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}
