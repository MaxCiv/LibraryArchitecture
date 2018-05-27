package com.maxciv.businesslogic.entities.users;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class AbstractUser implements User {

    private int id;
    private String login;
    private String password;    // здесь хранится хэш пароля
    private String name;

    public AbstractUser(int id, String login, String password, String name) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        User otherUser = (User) obj;
        return login.equals(otherUser.getLogin());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void encryptAndSetPassword(String password) {
        this.password = DigestUtils.sha1Hex(password);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
