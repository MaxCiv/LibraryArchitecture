package com.maxciv.businesslogic;

import java.util.List;

public class Supplier extends Profile {

    private String companyName;     // название компании поставщика

    public Supplier(long id, String name, String companyName) {
        super(id, name);
        this.companyName = companyName;
    }

    public List<Book> findOrderedBooks(Ordering ordering) {
        return ordering.getOrderedBooks();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}