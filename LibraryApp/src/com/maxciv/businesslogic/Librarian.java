package com.maxciv.businesslogic;

import java.util.List;

public class Librarian extends Profile {

    private List<Book> requiredBooks;           // список требуемых в библиотеку книг
    private List<Ordering> currentOrdering;     // действующие заказы на книги у поставщика
    private List<Contract> currentContracts;    // действующие контракты по обмену книг

    public Librarian(long id, String name, List<Book> requiredBooks, List<Ordering> currentOrdering, List<Contract> currentContracts) {
        super(id, name);
        this.requiredBooks = requiredBooks;
        this.currentOrdering = currentOrdering;
        this.currentContracts = currentContracts;
    }

    public List<Book> getRequiredBooks() {
        return requiredBooks;
    }

    public void setRequiredBooks(List<Book> requiredBooks) {
        this.requiredBooks = requiredBooks;
    }

    public List<Ordering> getCurrentOrdering() {
        return currentOrdering;
    }

    public void setCurrentOrdering(List<Ordering> currentOrdering) {
        this.currentOrdering = currentOrdering;
    }

    public List<Contract> getCurrentContracts() {
        return currentContracts;
    }

    public void setCurrentContracts(List<Contract> currentContracts) {
        this.currentContracts = currentContracts;
    }
}