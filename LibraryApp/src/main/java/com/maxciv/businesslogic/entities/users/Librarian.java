package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Condition;
import com.maxciv.businesslogic.Role;
import com.maxciv.businesslogic.Status;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.entities.libraryrecords.BookRecord;
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

    public BookBorrow confirmBorrowing(Repository repository, Object bookBorrow) throws LibraryAppException {
        if (bookBorrow == null || bookBorrow.getClass() != BookBorrow.class)
            throw new LibraryAppException("Failed borrowing confirmation: you should choose borrowing in the table.");
        BookBorrow bookBorrowToConfirm = (BookBorrow) bookBorrow;
        if (bookBorrowToConfirm.getStartDate() != null)
            throw new LibraryAppException("Failed borrowing confirmation: this borrowing already confirmed.");
        if (bookBorrowToConfirm.getBook().getCondition() != Condition.IN_LIBRARY || bookBorrowToConfirm.getBook().getStatus() != Status.LIBRARY)
            throw new LibraryAppException("Failed borrowing confirmation: book is unavailable now.");
        bookBorrowToConfirm.setStartDate(new Date());
        bookBorrowToConfirm.getBook().setCondition(Condition.IN_READING);
        repository.update(bookBorrowToConfirm);
        return bookBorrowToConfirm;
    }

    public BookBorrow closeBorrowing(Repository repository, Object bookBorrow) throws LibraryAppException {
        if (bookBorrow == null || bookBorrow.getClass() != BookBorrow.class)
            throw new LibraryAppException("Failed borrowing closing: you should choose borrowing in the table.");
        BookBorrow bookBorrowToClose = (BookBorrow) bookBorrow;
        if (bookBorrowToClose.getStartDate() == null || bookBorrowToClose.getEndDate() != null)
            throw new LibraryAppException("Failed borrowing closing: this borrowing is not for closing.");
        bookBorrowToClose.setEndDate(new Date());
        bookBorrowToClose.getBook().setCondition(Condition.IN_LIBRARY);
        repository.update(bookBorrowToClose);
        return bookBorrowToClose;
    }

    public BookExchange confirmExchange(Repository repository, Object bookExchange) throws LibraryAppException {
        if (bookExchange == null || bookExchange.getClass() != BookExchange.class)
            throw new LibraryAppException("Failed exchange confirmation: you should choose exchange in the table.");
        BookExchange bookExchangeToConfirm = (BookExchange) bookExchange;
        if (bookExchangeToConfirm.getReader() == null || bookExchangeToConfirm.getStartDate() != null)
            throw new LibraryAppException("Failed exchange confirmation: this exchange no confirmation needed.");
        if (bookExchangeToConfirm.getBook().getCondition() != Condition.ON_EXCHANGE || bookExchangeToConfirm.getBook().getStatus() != Status.EXCHANGE)
            throw new LibraryAppException("Failed exchange confirmation: book is unavailable now.");
        bookExchangeToConfirm.setStartDate(new Date());
        bookExchangeToConfirm.getBook().setCondition(Condition.IN_READING);
        repository.update(bookExchangeToConfirm);
        return bookExchangeToConfirm;
    }

    public BookExchange closeExchange(Repository repository, Object bookExchange) throws LibraryAppException {
        if (bookExchange == null || bookExchange.getClass() != BookExchange.class)
            throw new LibraryAppException("Failed exchange closing: you should choose exchange in the table.");
        BookExchange bookExchangeToClose = (BookExchange) bookExchange;
        if (bookExchangeToClose.getStartDate() == null || bookExchangeToClose.getEndDate() != null)
            throw new LibraryAppException("Failed exchange closing: this exchange no closing needed.");

        bookExchangeToClose.setEndDate(new Date());
        bookExchangeToClose.getBook().setCondition(Condition.ON_EXCHANGE);
        repository.update(bookExchangeToClose);

        repository.openNewExchange(new BookExchange(-1, bookExchangeToClose.getBook(), bookExchangeToClose.getOwner(), new Date()));

        return bookExchangeToClose;
    }

    @Override
    public Role getRole() {
        return Role.LIBRARIAN;
    }
}
