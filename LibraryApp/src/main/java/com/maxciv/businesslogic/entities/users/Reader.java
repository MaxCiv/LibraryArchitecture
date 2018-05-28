package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Role;

public class Reader extends AbstractUser {

    private int countBookLeftForExchange = 0;   // количество книг, которые читатель оставил на обмен (книги на обмене,
                                            // владельцем которых является данный читатель и у которых нет даты окончания)
    private int countBookTakenByExchange = 0;   // количество книг, которые читатель взял по обмену

    public Reader(int id, String login, String password, String name) {
        super(id, login, password, name);
    }

    public Reader(int id, String login, String password, String name, int countBookLeftForExchange, int countBookTakenByExchange) {
        super(id, login, password, name);
        this.countBookLeftForExchange = countBookLeftForExchange;
        this.countBookTakenByExchange = countBookTakenByExchange;
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

    public int getCountBookTakenByExchange() {
        return countBookTakenByExchange;
    }

    public void setCountBookTakenByExchange(int countBookTakenByExchange) {
        this.countBookTakenByExchange = countBookTakenByExchange;
    }
}
