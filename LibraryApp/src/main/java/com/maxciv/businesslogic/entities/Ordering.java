package com.maxciv.businesslogic.entities;

import java.util.List;

public class Ordering {

    public final static int STAT_OPENED = 0;            // заказ сделан и передан поставщику
    public final static int STAT_SEARCHING_BOOKS = 10; // поставщик ищет книги
    public final static int STAT_SELLING_BOOKS = 20;   // поставщик предлагает купить найденные книги
    public final static int STAT_CLOSED = 30;          // книги куплены, заказ оплачен и закрыт

    private long id;
    private Supplier supplier;          // поставщик, выполняющий заказ
    private List<Book> orderedBooks;    // заказанные книги
    private List<Book> foundBooks;      // найденные книги
    private List<Book> boughtBooks;     // купленные книги
    private int status;                 // статус заказа

    public Ordering(long id, Supplier supplier, List<Book> orderedBooks, List<Book> foundBooks, List<Book> boughtBooks, int status) {
        this.id = id;
        this.supplier = supplier;
        this.orderedBooks = orderedBooks;
        this.foundBooks = foundBooks;
        this.boughtBooks = boughtBooks;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Book> getOrderedBooks() {
        return orderedBooks;
    }

    public void setOrderedBooks(List<Book> orderedBooks) {
        this.orderedBooks = orderedBooks;
    }

    public List<Book> getFoundBooks() {
        return foundBooks;
    }

    public void setFoundBooks(List<Book> foundBooks) {
        this.foundBooks = foundBooks;
    }

    public List<Book> getBoughtBooks() {
        return boughtBooks;
    }

    public void setBoughtBooks(List<Book> boughtBooks) {
        this.boughtBooks = boughtBooks;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}