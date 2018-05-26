package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Role;

public class Librarian extends AbstractUser {

    public Librarian(int id, String login, String password, String name) {
        super(id, login, password, name);
    }

    @Override
    public Role getRole() {
        return Role.LIBRARIAN;
    }
}
