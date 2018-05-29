package com.maxciv.gui.facades;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.entities.libraryrecords.BookRecord;
import com.maxciv.businesslogic.entities.users.User;
import com.maxciv.businesslogic.exceptions.LibraryAppException;
import com.maxciv.businesslogic.exceptions.LogInErrorException;
import com.maxciv.businesslogic.exceptions.NotFoundException;

import java.util.List;

public interface Facade {

    int logInUser(String login, String realPassword) throws LogInErrorException;

    String getUserRole(int userId) throws NotFoundException;
    String getUserLogin(int userId) throws NotFoundException;
    String getUserName(int userId) throws NotFoundException;

    Book getBook(int bookId) throws NotFoundException;

    List<Book> getAllBooks() throws NotFoundException;
    List<BookBorrow> getAllBorrows() throws NotFoundException;
    List<BookExchange> getAllExchanges() throws NotFoundException;
    List<BookOrder> getAllOrderings() throws NotFoundException;
    List<BookRecord> getAllRequireConfirmation() throws NotFoundException;
    List<User> getAllUsers() throws NotFoundException;

    void addNewUser(String login, String password, String name, String role);
    void addNewBook(String title, String author, String publisher, int publishYear, int statusInt, int ownerId);

    void openNewOrder(int bookId, int supplierId) throws LibraryAppException;

    void update();
}
