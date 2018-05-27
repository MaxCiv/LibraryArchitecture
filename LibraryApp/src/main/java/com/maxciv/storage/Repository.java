package com.maxciv.storage;

import com.maxciv.storage.mappers.*;

import java.sql.SQLException;

public class Repository {

    private static BookMapper bookMapper;
    private static UserMapper userMapper;
    private static BookBorrowMapper bookBorrowMapper;
    private static BookExchangeMapper bookExchangeMapper;
    private static BookOrderMapper bookOrderMapper;

    public Repository() {
        try {
            if (bookMapper == null) bookMapper = new BookMapper();
            if (userMapper == null) userMapper = new UserMapper();
            if (bookBorrowMapper == null) bookBorrowMapper = new BookBorrowMapper();
            if (bookExchangeMapper == null) bookExchangeMapper = new BookExchangeMapper();
            if (bookOrderMapper == null) bookOrderMapper = new BookOrderMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
