package com.maxciv.storage.mappers;

import com.maxciv.Util;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.users.Reader;
import com.maxciv.storage.DataGateway;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class BookBorrowMapper implements Mapper<BookBorrow> {

    // COLUMNS = " id, book_id, reader_id, start_date, end_date ";
    private static Map<Integer, BookBorrow> loadedBookBorrowMap = new HashMap<>();
    private Connection connection;

    private BookMapper bookMapper;
    private UserMapper userMapper;

    public BookBorrowMapper() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
        bookMapper = new BookMapper();
        userMapper = new UserMapper();
    }

    public void addBookBorrow(BookBorrow bookBorrow) throws SQLException {
        if (loadedBookBorrowMap.values().contains(bookBorrow)) {
            update(bookBorrow);
        } else {
            String insertSQL = "INSERT INTO LibraryBooks(book_id, reader_id, start_date, end_date) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bookBorrow.getBook().getId());
            preparedStatement.setInt(2, bookBorrow.getReader().getId());
            preparedStatement.setString(3, Util.getStringFromFormattedDate(bookBorrow.getStartDate()));
            preparedStatement.setString(4, Util.getStringFromFormattedDate(bookBorrow.getEndDate()));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                bookBorrow.setId(resultSet.getInt(1));
            }

            loadedBookBorrowMap.put(bookBorrow.getId(), bookBorrow);

            bookMapper.update(bookBorrow.getBook());
            userMapper.update(bookBorrow.getReader());
        }
    }

    @Override
    public BookBorrow findById(int id) throws SQLException {
        for (int loadedBookBorrowId : loadedBookBorrowMap.keySet()) {
            if (loadedBookBorrowId == id)
                return loadedBookBorrowMap.get(loadedBookBorrowId);
        }

        // BookBorrow not found, extract from database
        String selectSQL = "SELECT * FROM LibraryBooks WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        int bookId = resultSet.getInt("book_id");
        int readerId = resultSet.getInt("reader_id");
        Date startDate = Util.getDateFromFormattedString(resultSet.getString("start_date"));
        Date endDate = Util.getDateFromFormattedString(resultSet.getString("end_date"));

        Book book = bookMapper.findById(bookId);
        Reader reader = (Reader) userMapper.findById(readerId);

        BookBorrow newBookBorrow = new BookBorrow(id, book, reader, startDate, endDate);

        loadedBookBorrowMap.put(id, newBookBorrow);

        return newBookBorrow;
    }

    @Override
    public Map<Integer, BookBorrow> findAll() throws SQLException {
        Map<Integer, BookBorrow> allBookBorrows = new HashMap<>();

        String selectSQL = "SELECT id FROM LibraryBooks;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            allBookBorrows.put(resultSet.getInt("id"), findById(resultSet.getInt("id")));
        }

        return allBookBorrows;
    }

    @Override
    public void update(BookBorrow item) throws SQLException {
        if (loadedBookBorrowMap.values().contains(item)) {
            String updateSQL = "UPDATE LibraryBooks SET  book_id = ?, reader_id = ?, start_date = ?, end_date = ?  WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, item.getBook().getId());
            preparedStatement.setInt(2, item.getReader().getId());
            preparedStatement.setString(3, Util.getStringFromFormattedDate(item.getStartDate()));
            preparedStatement.setString(4, Util.getStringFromFormattedDate(item.getEndDate()));
            preparedStatement.setInt(5, item.getId());
            preparedStatement.execute();

            bookMapper.update(item.getBook());
            userMapper.update(item.getReader());
        } else {
            addBookBorrow(item);
        }
    }

    @Override
    public void update() throws SQLException {
        bookMapper.update();
        userMapper.update();
        for (BookBorrow bookBorrow : loadedBookBorrowMap.values())
            update(bookBorrow);
    }

    @Override
    public void closeConnection() throws SQLException {
        bookMapper.closeConnection();
        userMapper.closeConnection();
        connection.close();
    }

    @Override
    public void clear() {
        bookMapper.clear();
        userMapper.clear();
        loadedBookBorrowMap.clear();
    }
}
