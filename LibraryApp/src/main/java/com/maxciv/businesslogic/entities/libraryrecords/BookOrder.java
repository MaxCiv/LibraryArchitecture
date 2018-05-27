package com.maxciv.businesslogic.entities.libraryrecords;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.users.Supplier;

import java.util.Date;

public class BookOrder extends AbstractBookRecord {

    private Supplier supplier;

    public BookOrder(int id, Book book, Supplier supplier, Date startDate) {
        super(id, book);
        this.supplier = supplier;
        this.setStartDate(startDate);
    }

    public BookOrder(int id, Book book, Supplier supplier, Date startDate, Date endDate) {
        this(id, book, supplier, startDate);
        this.setEndDate(endDate);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
