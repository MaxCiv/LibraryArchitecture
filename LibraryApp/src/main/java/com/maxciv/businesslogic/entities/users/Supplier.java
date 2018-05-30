package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Condition;
import com.maxciv.businesslogic.Role;
import com.maxciv.businesslogic.Status;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.exceptions.LibraryAppException;
import com.maxciv.storage.Repository;

import java.util.Date;

public class Supplier extends AbstractUser {

    public Supplier(int id, String login, String password, String name) {
        super(id, login, password, name);
    }

    public BookOrder finishOrder(Repository repository, Object bookOrder) throws LibraryAppException {
        if (bookOrder == null || bookOrder.getClass() != BookOrder.class)
            throw new LibraryAppException("Failed finishing: you should choose order in the table.");
        BookOrder bookOrder1 = (BookOrder) bookOrder;
        if (bookOrder1.getEndDate() != null)
            throw new LibraryAppException("Failed finishing: this order already finishing.");

        bookOrder1.setEndDate(new Date());
        bookOrder1.getBook().setStatus(Status.LIBRARY);
        bookOrder1.getBook().setCondition(Condition.IN_LIBRARY);
        repository.update(bookOrder1);
        return bookOrder1;
    }

    public BookOrder denyOrder(Repository repository, Object bookOrder) throws LibraryAppException {
        if (bookOrder == null || bookOrder.getClass() != BookOrder.class)
            throw new LibraryAppException("Failed denial: you should choose order in the table.");
        BookOrder bookOrder1 = (BookOrder) bookOrder;
        if (bookOrder1.getEndDate() != null)
            throw new LibraryAppException("Failed denial: this order already finishing.");

        bookOrder1.setEndDate(new Date());
        bookOrder1.getBook().setCondition(Condition.NOT_AVAILABLE);
        repository.update(bookOrder1);
        return bookOrder1;
    }

    @Override
    public Role getRole() {
        return Role.SUPPLIER;
    }
}
