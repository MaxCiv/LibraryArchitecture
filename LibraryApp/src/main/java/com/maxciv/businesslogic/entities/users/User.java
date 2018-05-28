package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Role;
import com.maxciv.businesslogic.entities.Book;

public interface User {

    Boolean logIn(String realPassword);
    Role getRole();

    int getId();
    void setId(int id);

    String getLogin();
    void setLogin(String login);

    String getPassword();
    void setPassword(String password);
    void encryptAndSetPassword(String realPassword);

    String getName();
    void setName(String name);
}
