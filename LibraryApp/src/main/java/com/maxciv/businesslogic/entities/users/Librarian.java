package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Condition;
import com.maxciv.businesslogic.Role;
import com.maxciv.businesslogic.Status;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.exceptions.LibraryAppException;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.storage.Repository;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

public class Librarian extends AbstractUser {

    public Librarian(int id, String login, String password, String name) {
        super(id, login, password, name);
    }

    public Book addNewBook(Repository repository, String title, String author, String publisher, int publishYear, int statusInt, int ownerId) {
        Status status = Status.valueOf(statusInt);
        Condition condition = null;
        switch (status) {
            case LIBRARY:
                condition = Condition.IN_LIBRARY;
                break;
            case EXCHANGE:
                condition = Condition.RETURNED_TO_OWNER;
                break;
            case ORDER:
                condition = Condition.NOT_AVAILABLE;
                break;
        }
        Book newBook = new Book(-1, title, author, publisher, publishYear, status, condition);
        repository.addNewBook(newBook);
        if (status == Status.EXCHANGE) {
            repository.openNewExchange(new BookExchange(-1, newBook, (Reader) repository.findUserById(ownerId)));
        }
        return newBook;
    }

    public User addNewUser(Repository repository, String login, String password, String name, String role) {
        User newUser = null;
        switch (role) {
            case "Librarian":
                newUser = new Librarian(-1, login, DigestUtils.sha1Hex(password), name);
                break;
            case "Reader":
                newUser = new Reader(-1, login, DigestUtils.sha1Hex(password), name);
                break;
            case "Supplier":
                newUser = new Supplier(-1, login, DigestUtils.sha1Hex(password), name);
                break;
        }
        repository.addNewUser(newUser);
        return newUser;
    }

    public BookOrder openNewOrder(Repository repository, int bookId, int supplierId) throws LibraryAppException {
        if (repository.findUserById(supplierId) == null || repository.findUserById(supplierId).getRole() != Role.SUPPLIER)
            throw new LibraryAppException("User with this ID isn't a supplier.");

        if (repository.findBookById(bookId).getStatus() != Status.ORDER || repository.findBookById(bookId).getCondition() != Condition.NOT_AVAILABLE) {
            throw new LibraryAppException("This book isn't for order.");
        }

        Book book = repository.findBookById(bookId);
        book.setCondition(Condition.ORDER_IN_PROGRESS);
        repository.update(book);
        BookOrder newBookOrder = new BookOrder(-1, book, (Supplier) repository.findUserById(supplierId), new Date());
        repository.openNewOrder(newBookOrder);
        return newBookOrder;
    }

    @Override
    public Role getRole() {
        return Role.LIBRARIAN;
    }
}
