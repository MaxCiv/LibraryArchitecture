package com.maxciv.gui.facades;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.users.User;
import com.maxciv.businesslogic.exceptions.LogInErrorException;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.storage.MappersRepository;

import java.util.List;

public class CommonFacade implements Facade {

    private MappersRepository repository;

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
    public List<Book> getAllBooks() throws NotFoundException {
        List<Book> books = repository.getAllBooks();
        if (books == null) throw new NotFoundException("All books not found.");
        return books;
    }

    @Override
    public List<User> getAllUsers() throws NotFoundException {
        List<User> users = repository.getAllUsers();
        if (users == null) throw new NotFoundException("All users not found.");
        return users;
    }

    @Override
    public void addNewUser(String login, String password, String name, String role) {
        repository.addNewUser(login, password, name, role);
    }

    @Override
    public void update() {
        repository.updateAll();
    }
}
