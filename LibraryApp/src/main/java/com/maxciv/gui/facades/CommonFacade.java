package com.maxciv.gui.facades;

import com.maxciv.businesslogic.entities.users.User;
import com.maxciv.businesslogic.exceptions.LogInErrorException;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.storage.MappersRepository;

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
}
