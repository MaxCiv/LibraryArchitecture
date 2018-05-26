package com.maxciv.businesslogic.entities;

import com.maxciv.businesslogic.Role;

public class Librarian extends AbstractUser {

    @Override
    public Role getRole() {
        return Role.LIBRARIAN;
    }
}
