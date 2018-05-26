package com.maxciv.businesslogic.entities.libraryrecords;

import com.maxciv.businesslogic.entities.Book;

import java.util.Date;

public interface BookRecord {

    int getId();
    void setId(int id);

    Book getBook();
    void setBook(Book book);

    Date getStartDate();
    void setStartDate(Date startDate);

    Date getEndDate();
    void setEndDate(Date endDate);
}
