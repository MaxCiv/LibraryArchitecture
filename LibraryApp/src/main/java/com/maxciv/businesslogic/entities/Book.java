package com.maxciv.businesslogic.entities;

public class Book {

    private long id;
    private long stockNumber;   // инвентарный номер
    private String title;       // название книги
    private String author;      // автор книги
    private String publisher;   // издатель
    private int publishYear;    // год издательства

    public Book(long id, long stockNumber, String title, String author, String publisher, int publishYear) {
        this.id = id;
        this.stockNumber = stockNumber;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(long stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }
}