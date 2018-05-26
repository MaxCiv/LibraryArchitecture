package com.maxciv.businesslogic.entities;

import com.maxciv.businesslogic.Role;

public interface User {

    Role getRole();

    int getId();
    void setId(int id);

    String getLogin();
    void setLogin(String login);

    String getName();
    void setName(String name);
}
