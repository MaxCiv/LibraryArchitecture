package com.maxciv.storage;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.users.User;

import java.util.List;

public interface Repository {

    User logInUser(String login, String realPassword);
    User findUserById(int id);
    List<Book> getAllBooks();
    List<User> getAllUsers();

    void addNewUser(String login, String password, String name, String role);

    void update(Object obj);
    void updateAll();
    void clearAll();
    void dropAll();
}
