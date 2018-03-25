package com.maxciv.businesslogic.entities;

import java.util.List;

public class Client extends Profile {

    private String phoneNumber;         // мобильный номер
    private List<Book> completedBooks;  // прочитанные и возвращенные книги
    private List<Book> currentBooks;    // книги, полученные клиентом в текущее время
    private List<Contract> currentContracts;    // договоры по обмену книгами, в которых сейчас участвует клиент

    public Client(long id, String name, String phoneNumber, List<Book> completedBooks, List<Book> currentBooks, List<Contract> currentContracts) {
        super(id, name);
        this.phoneNumber = phoneNumber;
        this.completedBooks = completedBooks;
        this.currentBooks = currentBooks;
        this.currentContracts = currentContracts;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Book> getCompletedBooks() {
        return completedBooks;
    }

    public void setCompletedBooks(List<Book> completedBooks) {
        this.completedBooks = completedBooks;
    }

    public List<Book> getCurrentBooks() {
        return currentBooks;
    }

    public void setCurrentBooks(List<Book> currentBooks) {
        this.currentBooks = currentBooks;
    }

    public List<Contract> getCurrentContracts() {
        return currentContracts;
    }

    public void setCurrentContracts(List<Contract> currentContracts) {
        this.currentContracts = currentContracts;
    }
}