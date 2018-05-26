package com.maxciv.businesslogic.entities;

import com.maxciv.businesslogic.Role;

public class Supplier extends AbstractUser {

    @Override
    public Role getRole() {
        return Role.SUPPLIER;
    }
}
