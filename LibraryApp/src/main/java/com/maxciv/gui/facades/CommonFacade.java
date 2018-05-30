package com.maxciv.gui.facades;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.entities.libraryrecords.BookRecord;
import com.maxciv.businesslogic.entities.users.Librarian;
import com.maxciv.businesslogic.entities.users.Reader;
import com.maxciv.businesslogic.entities.users.Supplier;
import com.maxciv.businesslogic.entities.users.User;
import com.maxciv.businesslogic.exceptions.LibraryAppException;
import com.maxciv.businesslogic.exceptions.LogInErrorException;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.storage.MappersRepository;

import java.util.List;

public class CommonFacade implements Facade {

    private MappersRepository repository;
    private int loggedInUserId;

    public CommonFacade() {
        repository = new MappersRepository();
    }



    /**
     * Tries to log in user with login and password.
     *
     * @param login
     * @param realPassword
     * @return id of logged in user
     * @throws LogInErrorException if login or password incorrect
     */
    @Override
    public int logInUser(String login, String realPassword) throws LogInErrorException {
        User user = repository.logInUser(login, realPassword);
        if (user == null) throw new LogInErrorException("Incorrect login or password.");
        loggedInUserId = user.getId();
        return user.getId();
    }

    @Override
    public String getUserRole(int userId) throws NotFoundException {
        User user = repository.findUserById(userId);
        if (user == null) throw new NotFoundException("User with ID " + userId + " not found.");
        return user.getRole().getRoleName();
    }

    @Override
    public String getUserLogin(int userId) throws NotFoundException {
        User user = repository.findUserById(userId);
        if (user == null) throw new NotFoundException("User with ID " + userId + " not found.");
        return user.getLogin();
    }

    @Override
    public String getUserName(int userId) throws NotFoundException {
        User user = repository.findUserById(userId);
        if (user == null) throw new NotFoundException("User with ID " + userId + " not found.");
        return user.getName();
    }

    @Override
    public User getUser(int userId) throws NotFoundException {
        User user = repository.findUserById(userId);
        if (user == null) throw new NotFoundException("User with ID " + userId + " not found.");
        return user;
    }

    @Override
    public Book getBook(int bookId) throws NotFoundException {
        Book book = repository.findBookById(bookId);
        if (book == null) throw new NotFoundException("Book with ID " + bookId + " not found.");
        return book;
    }

    @Override
    public List<Book> getAllBooks() throws NotFoundException {
        List<Book> books = repository.getAllBooks();
        if (books == null) throw new NotFoundException("All books not found.");
        return books;
    }

    @Override
    public List<BookBorrow> getAllBorrows() throws NotFoundException {
        List<BookBorrow> bookBorrows = repository.getAllBorrows();
        if (bookBorrows == null) throw new NotFoundException("All book borrows not found.");
        return bookBorrows;
    }

    @Override
    public List<BookBorrow> getAllUserBorrows(int userId) throws NotFoundException {
        Reader reader = (Reader)repository.findUserById(userId);
        List<BookBorrow> bookBorrows = repository.getAllUserBorrows(reader);
        if (bookBorrows == null) throw new NotFoundException("All user's (ID: " + userId + ") book borrows not found.");
        return bookBorrows;
    }

    @Override
    public List<BookExchange> getAllExchanges() throws NotFoundException {
        List<BookExchange> bookExchanges = repository.getAllExchanges();
        if (bookExchanges == null) throw new NotFoundException("All book exchanges not found.");
        return bookExchanges;
    }

    @Override
    public List<BookExchange> getAllUserExchanges(int userId) throws NotFoundException {
        Reader reader = (Reader)repository.findUserById(userId);
        List<BookExchange> bookExchanges = repository.getAllUserExchanges(reader);
        if (bookExchanges == null) throw new NotFoundException("All user's (ID: " + userId + ") book exchanges not found.");
        return bookExchanges;
    }

    @Override
    public List<BookOrder> getAllOrderings() throws NotFoundException {
        List<BookOrder> bookOrders = repository.getAllOrderings();
        if (bookOrders == null) throw new NotFoundException("All book orderings not found.");
        return bookOrders;
    }

    @Override
    public List<BookOrder> getAllUserOrderings(int userId) throws NotFoundException {
        Supplier supplier = (Supplier)repository.findUserById(userId);
        List<BookOrder> bookOrders = repository.getAllUserOrderings(supplier);
        if (bookOrders == null) throw new NotFoundException("All user's (ID: " + userId + ") book orderings not found.");
        return bookOrders;
    }

    @Override
    public List<BookRecord> getAllRequireConfirmation() throws NotFoundException {
        List<BookRecord> bookRecords = repository.getAllRequireConfirmation();
        if (bookRecords == null) throw new NotFoundException("All require confirmation book records not found.");
        return bookRecords;
    }

    @Override
    public List<User> getAllUsers() throws NotFoundException {
        List<User> users = repository.getAllUsers();
        if (users == null) throw new NotFoundException("All users not found.");
        return users;
    }

    @Override
    public void addNewUser(String login, String password, String name, String role) {
        try {
            ((Librarian) getUser(loggedInUserId)).addNewUser(repository, login, password, name, role);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewBook(String title, String author, String publisher, int publishYear, int statusInt, int ownerId) {
        try {
            ((Librarian) getUser(loggedInUserId)).addNewBook(repository, title, author, publisher, publishYear, statusInt, ownerId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openNewOrder(int bookId, int supplierId) throws LibraryAppException {
        try {
            ((Librarian) getUser(loggedInUserId)).openNewOrder(repository, bookId, supplierId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmBorrowing(Object bookBorrow) throws LibraryAppException {
        try {
            ((Librarian) getUser(loggedInUserId)).confirmBorrowing(repository, bookBorrow);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeBorrowing(Object bookBorrow) throws LibraryAppException {
        try {
            ((Librarian) getUser(loggedInUserId)).closeBorrowing(repository, bookBorrow);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmExchange(Object bookExchange) throws LibraryAppException {
        try {
            ((Librarian) getUser(loggedInUserId)).confirmExchange(repository, bookExchange);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeExchange(Object bookExchange) throws LibraryAppException {
        try {
            ((Librarian) getUser(loggedInUserId)).closeExchange(repository, bookExchange);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void borrowBookToReader(Object book) throws LibraryAppException {
        try {
            ((Reader) getUser(loggedInUserId)).borrowBook(repository, book);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void takeBookByExchangeToReader(Object bookExchange) throws LibraryAppException {
        try {
            ((Reader) getUser(loggedInUserId)).getBookByExchange(repository, bookExchange);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startExchange(Object bookExchange) throws LibraryAppException {
        try {
            ((Reader) getUser(loggedInUserId)).openExchange(repository, bookExchange);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeExchangeAndTakeBookToOwner(Object bookExchange) throws LibraryAppException {
        try {
            ((Reader) getUser(loggedInUserId)).closeExchangeAndTakeBook(repository, bookExchange);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finishOrder(Object bookOrder) throws LibraryAppException {
        try {
            ((Supplier) getUser(loggedInUserId)).finishOrder(repository, bookOrder);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void denyOrder(Object bookOrder) throws LibraryAppException {
        try {
            ((Supplier) getUser(loggedInUserId)).denyOrder(repository, bookOrder);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        repository.updateAll();
    }
}
