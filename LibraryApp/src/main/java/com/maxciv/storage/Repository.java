package com.maxciv.storage;

import com.maxciv.businesslogic.entities.users.User;

public interface Repository {

    User logInUser(String login, String realPassword);
    User findUserById(int id);

    void update(Object obj);
    void updateAll();
    void clearAll();
    void dropAll();
}
