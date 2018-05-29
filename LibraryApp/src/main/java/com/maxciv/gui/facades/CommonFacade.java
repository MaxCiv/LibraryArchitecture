package com.maxciv.gui.facades;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.entities.libraryrecords.BookRecord;
import com.maxciv.businesslogic.entities.users.Librarian;
import com.maxciv.businesslogic.entities.users.User;
import com.maxciv.businesslogic.exceptions.LibraryAppException;
import com.maxciv.businesslogic.exceptions.LogInErrorException;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.storage.MappersRepository;

import java.util.List;

public class CommonFacade implements Facade {

    private MappersRepository repository;
    private User loggedInUser;

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
        loggedInUser = user;
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
    public List<BookExchange> getAllExchanges() throws NotFoundException {
        List<BookExchange> bookExchanges = repository.getAllExchanges();
        if (bookExchanges == null) throw new NotFoundException("All book borrows not found.");
        return bookExchanges;
    }

    @Override
    public List<BookOrder> getAllOrderings() throws NotFoundException {
        List<BookOrder> bookOrders = repository.getAllOrderings();
        if (bookOrders == null) throw new NotFoundException("All book borrows not found.");
        return bookOrders;
    }

    @Override
    public List<BookRecord> getAllRequireConfirmation() throws NotFoundException {
        List<BookRecord> bookRecords = repository.getAllRequireConfirmation();
        if (bookRecords == null) throw new NotFoundException("All book borrows not found.");
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
        ((Librarian)loggedInUser).addNewUser(repository, login, password, name, role);
    }

    @Override
    public void addNewBook(String title, String author, String publisher, int publishYear, int statusInt, int ownerId) {
        ((Librarian)loggedInUser).addNewBook(repository, title, author, publisher, publishYear, statusInt, ownerId);
    }

    @Override
    public void openNewOrder(int bookId, int supplierId) throws LibraryAppException {
        ((Librarian)loggedInUser).openNewOrder(repository, bookId, supplierId);
    }

    @Override
    public void update() {
        repository.updateAll();
    }
}
