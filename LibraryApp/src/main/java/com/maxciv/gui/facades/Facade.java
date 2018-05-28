package com.maxciv.gui.facades;

import com.maxciv.businesslogic.exceptions.LogInErrorException;
import com.maxciv.businesslogic.exceptions.NotFoundException;

public interface Facade {

    int logInUser(String login, String realPassword) throws LogInErrorException;
    String getUserRole(int userId) throws NotFoundException;
}
