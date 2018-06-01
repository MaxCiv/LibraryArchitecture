package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Condition;
import com.maxciv.businesslogic.Role;
import com.maxciv.businesslogic.Status;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.exceptions.LibraryAppException;
import com.maxciv.storage.Repository;

import java.util.Date;
import java.util.List;

public class Reader extends AbstractUser {

    private int countBookLeftForExchange = 0;   // количество книг, которые читатель оставил на обмен (книги на обмене,
                                                // владельцем которых является данный читатель и у которых нет даты окончания)
    private int countBookTakenByExchange = 0;   // количество книг, которые читатель взял по обмену

    public Reader(int id, String login, String password, String name) {
        super(id, login, password, name);
    }

    public Reader(int id, String login, String password, String name, int countBookLeftForExchange, int countBookTakenByExchange) {
        super(id, login, password, name);
        this.countBookLeftForExchange = countBookLeftForExchange;
        this.countBookTakenByExchange = countBookTakenByExchange;
    }

    public BookBorrow borrowBook(Repository repository, Object book) throws LibraryAppException {
        if (book == null || book.getClass() != Book.class)
            throw new LibraryAppException("Failed borrowing: you should choose book in the table.");
        Book bookToBorrow = (Book) book;
        if (bookToBorrow.getStatus() != Status.LIBRARY || bookToBorrow.getCondition() != Condition.IN_LIBRARY)
            throw new LibraryAppException("Failed borrowing: this book is not for borrowing.");
        List<BookBorrow> bookBorrowList = repository.getAllUserBorrows(this);
        if (bookBorrowList != null) {
            for (BookBorrow bookBorrow : bookBorrowList) {
                if (bookBorrow.getBook().getId() == ((Book) book).getId()
                        && bookBorrow.getReader().getId() == getId()
                        && bookBorrow.getStartDate() == null)
                    throw new LibraryAppException("Failed borrowing: this book is already borrowed.");
            }
        }
        BookBorrow bookBorrow = new BookBorrow(-1, bookToBorrow, this);
        repository.addNewBookBorrow(bookBorrow);
        return bookBorrow;
    }

    public BookExchange getBookByExchange(Repository repository, Object bookExchange) throws LibraryAppException {
        if (getCountAvailableExchangeBook() <= 0)
            throw new LibraryAppException("Failed getting book by exchange: add your books to exchange to be able get another books by exchange.");
        if (bookExchange == null || bookExchange.getClass() != BookExchange.class)
            throw new LibraryAppException("Failed getting book by exchange: you should choose exchange in the table.");
        BookExchange exchange = (BookExchange) bookExchange;
        if (exchange.getBook().getStatus() != Status.EXCHANGE
                || exchange.getBook().getCondition() != Condition.ON_EXCHANGE
                || exchange.getOpenExchangeDate() == null
                || exchange.getReader() != null)
            throw new LibraryAppException("Failed getting book by exchange: this book is not for exchange.");
        if (exchange.getOwner().getId() == getId())
            throw new LibraryAppException("Failed getting book by exchange: it is your book, you can Close Your Exchange to get this book back.");
        exchange.setReader(this);
        repository.update(exchange);
        return exchange;
    }

    public BookExchange openExchange(Repository repository, Object bookExchange) throws LibraryAppException {
        if (bookExchange == null || bookExchange.getClass() != BookExchange.class)
            throw new LibraryAppException("Failed open exchange: you should choose exchange in the table.");
        BookExchange exchange = (BookExchange) bookExchange;
        if (exchange.getBook().getStatus() != Status.EXCHANGE
                || exchange.getBook().getCondition() != Condition.RETURNED_TO_OWNER
                || exchange.getOpenExchangeDate() != null)
            throw new LibraryAppException("Failed open exchange: this book is not for exchange.");
        if (exchange.getOwner().getId() != getId())
            throw new LibraryAppException("Failed open exchange: it isn't your book.");

        exchange.setOpenExchangeDate(new Date());
        exchange.getBook().setCondition(Condition.ON_EXCHANGE);
        repository.update(exchange);
        return exchange;
    }

    public BookExchange closeExchangeAndTakeBook(Repository repository, Object bookExchange) throws LibraryAppException {
        if (bookExchange == null || bookExchange.getClass() != BookExchange.class)
            throw new LibraryAppException("Failed close exchange: you should choose exchange in the table.");
        BookExchange exchange = (BookExchange) bookExchange;
        if (exchange.getBook().getStatus() != Status.EXCHANGE
                || exchange.getBook().getCondition() != Condition.ON_EXCHANGE
                || exchange.getOpenExchangeDate() == null
                || exchange.getStartDate() != null)
            throw new LibraryAppException("Failed close exchange: you can't close this exchange.");
        if (exchange.getOwner().getId() != getId())
            throw new LibraryAppException("Failed close exchange: it isn't your book.");

        exchange.setReader(this);
        exchange.setStartDate(new Date());
        exchange.setEndDate(new Date());
        exchange.getBook().setCondition(Condition.RETURNED_TO_OWNER);
        repository.update(exchange);

        repository.openNewExchange(new BookExchange(-1, exchange.getBook(), exchange.getOwner()));
        return exchange;
    }

    @Override
    public Role getRole() {
        return Role.READER;
    }

    public int getCountAvailableExchangeBook() {
        return countBookLeftForExchange - countBookTakenByExchange;
    }

    public int getCountBookLeftForExchange() {
        return countBookLeftForExchange;
    }

    public void setCountBookLeftForExchange(int countBookLeftForExchange) {
        this.countBookLeftForExchange = countBookLeftForExchange;
    }

    public int getCountBookTakenByExchange() {
        return countBookTakenByExchange;
    }

    public void setCountBookTakenByExchange(int countBookTakenByExchange) {
        this.countBookTakenByExchange = countBookTakenByExchange;
    }
}
