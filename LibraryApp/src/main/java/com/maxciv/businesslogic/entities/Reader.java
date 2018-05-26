package com.maxciv.businesslogic.entities;

import com.maxciv.businesslogic.Role;

public class Reader extends AbstractUser {

    @Override
    public Role getRole() {
        return Role.READER;
    }
}
