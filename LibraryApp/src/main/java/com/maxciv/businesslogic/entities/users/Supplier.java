package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Role;

public class Supplier extends AbstractUser {

    public Supplier(int id, String login, String password, String name) {
        super(id, login, password, name);
    }

    @Override
    public Role getRole() {
        return Role.SUPPLIER;
    }
}
