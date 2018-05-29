package com.maxciv.storage;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.entities.libraryrecords.BookRecord;
import com.maxciv.businesslogic.entities.users.User;

import java.util.List;

public interface Repository {

    User logInUser(String login, String realPassword);
    User findUserById(int id);
    Book findBookById(int id);
    List<Book> getAllBooks();
    List<BookBorrow> getAllBorrows();
    List<BookExchange> getAllExchanges();
    List<BookOrder> getAllOrderings();
    List<BookRecord> getAllRequireConfirmation();
    List<User> getAllUsers();

    void addNewUser(User newUser);
    void addNewBook(Book book);

    void openNewExchange(BookExchange bookExchange);
    void openNewOrder(BookOrder bookOrder);

    void updateExchange(BookExchange bookExchange);

    void update(Object obj);
    void updateAll();
    void clearAll();
    void dropAll();
}
