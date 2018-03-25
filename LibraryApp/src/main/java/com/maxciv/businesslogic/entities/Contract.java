package com.maxciv.businesslogic.entities;

public class Contract {

    public final static int STAT_OPENED = 0;// клиент 1 выставил книгу 1 на обмен, обмен открыт
    public final static int STAT_REQUEST = 10;// клиент 2 предложил книгу 2 на обмен
    public final static int STAT_EXCHANGED = 20;// клиент 1 и клиент 2 обменялись книгами
    public final static int STAT_CLOSED = 30;// клиенты вернули друг другу книги, обмен завершен

    private long id;
    private Client firstClient; // первый клиент, предлагающий обмен
    private Book firstBook;     // книга первого клиента
    private Client secondClient;// второй клиент, учавствует в обмене
    private Book secondBook;    // книга второго клиента
    private int status;         // статус договора

    public Contract(long id, Client firstClient, Book firstBook, Client secondClient, Book secondBook, int status) {
        this.id = id;
        this.firstClient = firstClient;
        this.firstBook = firstBook;
        this.secondClient = secondClient;
        this.secondBook = secondBook;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getFirstClient() {
        return firstClient;
    }

    public void setFirstClient(Client firstClient) {
        this.firstClient = firstClient;
    }

    public Book getFirstBook() {
        return firstBook;
    }

    public void setFirstBook(Book firstBook) {
        this.firstBook = firstBook;
    }

    public Client getSecondClient() {
        return secondClient;
    }

    public void setSecondClient(Client secondClient) {
        this.secondClient = secondClient;
    }

    public Book getSecondBook() {
        return secondBook;
    }

    public void setSecondBook(Book secondBook) {
        this.secondBook = secondBook;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}