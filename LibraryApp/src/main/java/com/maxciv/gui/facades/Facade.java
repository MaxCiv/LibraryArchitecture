package com.maxciv.gui.facades;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.users.User;
import com.maxciv.businesslogic.exceptions.LogInErrorException;
import com.maxciv.businesslogic.exceptions.NotFoundException;

import java.util.List;

public interface Facade {

    int logInUser(String login, String realPassword) throws LogInErrorException;
    String getUserRole(int userId) throws NotFoundException;
    String getUserLogin(int userId) throws NotFoundException;
    String getUserName(int userId) throws NotFoundException;

    List<Book> getAllBooks() throws NotFoundException;
    List<User> getAllUsers() throws NotFoundException;

    void addNewUser(String login, String password, String name, String role);

    void update();
}
