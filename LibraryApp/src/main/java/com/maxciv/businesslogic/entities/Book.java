package com.maxciv.businesslogic.entities;

import com.maxciv.businesslogic.Condition;
import com.maxciv.businesslogic.Status;

public class Book {

    private int id;
    private String title;       // название книги
    private String author;      // автор книги
    private String publisher;   // издатель
    private int publishYear;    // год издательства
    private Status status;
    private Condition condition;

    public Book(int id, String title, String author, String publisher, int publishYear, Status status, Condition condition) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.status = status;
        this.condition = condition;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}