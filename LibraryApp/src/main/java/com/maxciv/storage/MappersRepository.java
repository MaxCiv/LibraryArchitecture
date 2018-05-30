package com.maxciv.storage;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.entities.libraryrecords.BookRecord;
import com.maxciv.businesslogic.entities.users.Librarian;
import com.maxciv.businesslogic.entities.users.Reader;
import com.maxciv.businesslogic.entities.users.Supplier;
import com.maxciv.businesslogic.entities.users.User;
import com.maxciv.storage.mappers.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MappersRepository implements Repository {

    private static BookMapper bookMapper;
    private static UserMapper userMapper;
    private static BookBorrowMapper bookBorrowMapper;
    private static BookExchangeMapper bookExchangeMapper;
    private static BookOrderMapper bookOrderMapper;

    public MappersRepository() {
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

    @Override
    public User logInUser(String login, String realPassword) {
        User loggedInUser = null;
        try {
            loggedInUser = userMapper.findByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (loggedInUser != null && loggedInUser.logIn(realPassword)) {
            return loggedInUser;
        }
        return null;
    }

    @Override
    public User findUserById(int id) {
        try {
            return userMapper.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book findBookById(int id) {
        try {
            return bookMapper.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        try {
            return new ArrayList<>(bookMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookBorrow> getAllBorrows() {
        try {
            return new ArrayList<>(bookBorrowMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookBorrow> getAllUserBorrows(Reader reader) {
        List<BookBorrow> allBookBorrows = null;
        try {
            allBookBorrows = new ArrayList<>(bookBorrowMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<BookBorrow> userBookBorrows = new ArrayList<>();
        for (BookBorrow bookBorrow : allBookBorrows) {
            if (bookBorrow.getReader().equals(reader)) userBookBorrows.add(bookBorrow);
        }
        return userBookBorrows;
    }

    @Override
    public List<BookExchange> getAllExchanges() {
        try {
            return new ArrayList<>(bookExchangeMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookExchange> getAllUserExchanges(Reader reader) {
        List<BookExchange> allBookExchanges;
        try {
            allBookExchanges = new ArrayList<>(bookExchangeMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<BookExchange> userBookExchanges = new ArrayList<>();
        for (BookExchange bookExchange : allBookExchanges) {
            if (bookExchange.getOwner().equals(reader) || (bookExchange.getReader() != null && bookExchange.getReader().equals(reader)))
                userBookExchanges.add(bookExchange);
        }
        return userBookExchanges;
    }

    @Override
    public List<BookOrder> getAllOrderings() {
        try {
            return new ArrayList<>(bookOrderMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookOrder> getAllUserOrderings(Supplier supplier) {
        List<BookOrder> allBookOrders;
        try {
            allBookOrders = new ArrayList<>(bookOrderMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<BookOrder> userBookOrders = new ArrayList<>();
        for (BookOrder bookOrder : allBookOrders) {
            if (bookOrder.getSupplier().equals(supplier)) userBookOrders.add(bookOrder);
        }
        return userBookOrders;
    }

    @Override
    public List<BookRecord> getAllRequireConfirmation() {
        try {
            List<BookBorrow> bookBorrows = new ArrayList<>(bookBorrowMapper.findAll().values());
            List<BookExchange> bookExchanges = new ArrayList<>(bookExchangeMapper.findAll().values());

            List<BookRecord> bookRecords = new ArrayList<>();
            for (BookBorrow bookBorrow : bookBorrows) {
                if (bookBorrow.getStartDate() == null) bookRecords.add(bookBorrow);
            }
            for (BookExchange bookExchange : bookExchanges) {
                if (bookExchange.getReader() != null && bookExchange.getStartDate() == null) bookRecords.add(bookExchange);
            }
            return bookRecords;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return new ArrayList<>(userMapper.findAll().values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addNewUser(User newUser) {
        try {
            userMapper.addUser(newUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewBook(Book book) {
        try {
            bookMapper.addBook(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewBookBorrow(BookBorrow bookBorrow) {
        try {
            bookBorrowMapper.addBookBorrow(bookBorrow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openNewExchange(BookExchange bookExchange) {
        try {
            bookExchangeMapper.addBookExchange(bookExchange);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openNewOrder(BookOrder bookOrder) {
        try {
            bookOrderMapper.addBookOrder(bookOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateExchange(BookExchange bookExchange) {
        try {
            bookExchangeMapper.update(bookExchange);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object obj) {
        String name = obj.getClass().getSimpleName();
        if (name.equals("Librarian") || name.equals("Reader") || name.equals("Supplier")) {
            name = "User";
        }
        try {
            switch (name) {
                case "User":
                    User user = (User) obj;
                    userMapper.update(user);
                    break;
                case "Book":
                    Book book = (Book) obj;
                    bookMapper.update(book);
                    break;
                case "BookBorrow":
                    BookBorrow bookBorrow = (BookBorrow) obj;
                    bookBorrowMapper.update(bookBorrow);
                    break;
                case "BookExchange":
                    BookExchange bookExchange = (BookExchange) obj;
                    bookExchangeMapper.update(bookExchange);
                    break;
                case "BookOrder":
                    BookOrder bookOrder = (BookOrder) obj;
                    bookOrderMapper.update(bookOrder);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAll() {
        try {
            bookMapper.update();
            userMapper.update();
            bookBorrowMapper.update();
            bookExchangeMapper.update();
            bookOrderMapper.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAll() {
        bookMapper.clear();
        userMapper.clear();
        bookBorrowMapper.clear();
        bookExchangeMapper.clear();
        bookOrderMapper.clear();
    }

    @Override
    public void dropAll() {
        try {
            DataGateway.getInstance().dropAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearAll();
    }
}
