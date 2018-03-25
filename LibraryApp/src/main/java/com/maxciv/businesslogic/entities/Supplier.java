package com.maxciv.businesslogic.entities;

import java.util.List;

public class Supplier extends Profile {

    private String companyName;     // название компании поставщика

    public Supplier(long id, String name, String companyName) {
        super(id, name);
        this.companyName = companyName;
    }

    public List<Book> findOrderedBooks(Ordering ordering) {
        // процесс поиска книг
        ordering.setFoundBooks(ordering.getOrderedBooks()); // найдены все заказанные книги
        return ordering.getFoundBooks();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}