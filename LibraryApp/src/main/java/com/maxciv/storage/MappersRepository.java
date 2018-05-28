package com.maxciv.storage;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.entities.users.User;
import com.maxciv.storage.mappers.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;

public class MappersRepository implements Repository {

    private static BookMapper bookMapper;
    private static UserMapper userMapper;
    private static BookBorrowMapper bookBorrowMapper;
    private static BookExchangeMapper bookExchangeMapper;
    private static BookOrderMapper bookOrderMapper;

    public MappersRepository() {
        try {
            if (bookMapper == null) bookMapper = new BookMapper();
            if (userMapper == null) userMapper = new UserMapper();
            if (bookBorrowMapper == null) bookBorrowMapper = new BookBorrowMapper();
            if (bookExchangeMapper == null) bookExchangeMapper = new BookExchangeMapper();
            if (bookOrderMapper == null) bookOrderMapper = new BookOrderMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User logInUser(String login, String realPassword) {
        User loggedInUser = null;
        try {
            loggedInUser = userMapper.findByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (loggedInUser != null && loggedInUser.logIn(realPassword)) {
            return loggedInUser;
        }
        return null;
    }

    @Override
    public User findUserById(int id) {
        try {
            return userMapper.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Object obj) {
        String name = obj.getClass().getSimpleName();
        if (name.equals("Librarian") || name.equals("Reader") || name.equals("Supplier")) {
            name = "User";
        }
        try {
            switch (name) {
                case "User":
                    User user = (User) obj;
                    userMapper.update(user);
                    break;
                case "Book":
                    Book book = (Book) obj;
                    bookMapper.update(book);
                    break;
                case "Film":
                    BookBorrow bookBorrow = (BookBorrow) obj;
                    bookBorrowMapper.update(bookBorrow);
                    break;
                case "Seans":
                    BookExchange bookExchange = (BookExchange) obj;
                    bookExchangeMapper.update(bookExchange);
                    break;
                case "Bron":
                    BookOrder bookOrder = (BookOrder) obj;
                    bookOrderMapper.update(bookOrder);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAll() {
        try {
            bookMapper.update();
            userMapper.update();
            bookBorrowMapper.update();
            bookExchangeMapper.update();
            bookOrderMapper.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAll() {
        bookMapper.clear();
        userMapper.clear();
        bookBorrowMapper.clear();
        bookExchangeMapper.clear();
        bookOrderMapper.clear();
    }

    @Override
    public void dropAll() {
        try {
            DataGateway.getInstance().dropAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearAll();
    }
}
