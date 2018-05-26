package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Role;

public class Reader extends AbstractUser {

    private int countBookLeftForExchange;   // количество книг, которые читатель оставил на обмен (книги на обмене,
                                            // владельцем которых является данный читатель и у которых нет даты окончания)

    public Reader(int id, String login, String password, String name, int countBookLeftForExchange) {
        super(id, login, password, name);
        this.countBookLeftForExchange = countBookLeftForExchange;
    }

    @Override
    public Role getRole() {
        return Role.READER;
    }

    public int getCountBookLeftForExchange() {
        return countBookLeftForExchange;
    }

    public void setCountBookLeftForExchange(int countBookLeftForExchange) {
        this.countBookLeftForExchange = countBookLeftForExchange;
    }
}
