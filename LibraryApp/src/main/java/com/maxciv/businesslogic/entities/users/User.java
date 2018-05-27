package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Role;

public interface User {

    Role getRole();

    int getId();
    void setId(int id);

    String getLogin();
    void setLogin(String login);

    String getPassword();
    void setPassword(String password);
    void encryptAndSetPassword(String password);

    String getName();
    void setName(String name);
}
