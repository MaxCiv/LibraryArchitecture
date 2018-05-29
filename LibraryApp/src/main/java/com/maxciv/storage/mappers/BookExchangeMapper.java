package com.maxciv.storage.mappers;

import com.maxciv.Util;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.users.Reader;
import com.maxciv.storage.DataGateway;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookExchangeMapper implements Mapper<BookExchange> {

    // COLUMNS = " id, book_id, owner_id, open_exchange_date, reader_id, start_date, end_date ";
    private static Map<Integer, BookExchange> loadedBookExchangeMap = new HashMap<>();
    private Connection connection;

    private BookMapper bookMapper;
    private UserMapper userMapper;

    public BookExchangeMapper() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
        bookMapper = new BookMapper();
        userMapper = new UserMapper();
    }

    public void addBookExchange(BookExchange bookExchange) throws SQLException {
        if (loadedBookExchangeMap.values().contains(bookExchange)) {
            update(bookExchange);
        } else {
            String insertSQL = "INSERT INTO exchangebooks(book_id, owner_id, open_exchange_date, reader_id, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bookExchange.getBook().getId());
            preparedStatement.setInt(2, bookExchange.getOwner().getId());
            preparedStatement.setString(3, Util.getStringFromFormattedDate(bookExchange.getOpenExchangeDate()));
            if (bookExchange.getReader() == null) {
                preparedStatement.setNull(4, Types.INTEGER);
            } else {
                preparedStatement.setInt(4, bookExchange.getReader().getId());
            }
            preparedStatement.setString(5, Util.getStringFromFormattedDate(bookExchange.getStartDate()));
            preparedStatement.setString(6, Util.getStringFromFormattedDate(bookExchange.getEndDate()));

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                bookExchange.setId(resultSet.getInt(1));
            }

            loadedBookExchangeMap.put(bookExchange.getId(), bookExchange);

//            bookMapper.update(bookExchange.getBook());
//            userMapper.update(bookExchange.getOwner());
//            if (bookExchange.getReader() != null) userMapper.update(bookExchange.getReader());
        }
    }

    @Override
    public BookExchange findById(int id) throws SQLException {
        for (int loadedBookExchangeId : loadedBookExchangeMap.keySet()) {
            if (loadedBookExchangeId == id)
                return loadedBookExchangeMap.get(loadedBookExchangeId);
        }

        // BookExchange not found, extract from database
        String selectSQL = "SELECT * FROM exchangebooks WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        int bookId = resultSet.getInt("book_id");
        int ownerId = resultSet.getInt("owner_id");
        Date openExchangeDate = Util.getDateFromFormattedString(resultSet.getString("open_exchange_date"));
        Date startDate = Util.getDateFromFormattedString(resultSet.getString("start_date"));
        Date endDate = Util.getDateFromFormattedString(resultSet.getString("end_date"));

        Book book = bookMapper.findById(bookId);
        Reader owner = (Reader) userMapper.findById(ownerId);

        Reader reader = null;
        int readerId = resultSet.getInt("reader_id");
        if (!resultSet.wasNull()) {
            reader = (Reader) userMapper.findById(readerId);
        }

        BookExchange newBookExchange = new BookExchange(id, book, owner, openExchangeDate, reader, startDate, endDate);

        loadedBookExchangeMap.put(id, newBookExchange);

        return newBookExchange;
    }

    @Override
    public Map<Integer, BookExchange> findAll() throws SQLException {
        Map<Integer, BookExchange> allBookExchanges = new HashMap<>();

        String selectSQL = "SELECT id FROM exchangebooks;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            allBookExchanges.put(resultSet.getInt("id"), findById(resultSet.getInt("id")));
        }

        return allBookExchanges;
    }

    @Override
    public void update(BookExchange item) throws SQLException {
        if (loadedBookExchangeMap.values().contains(item)) {
            String updateSQL = "UPDATE exchangebooks SET  book_id = ?, owner_id = ?, open_exchange_date = ?, reader_id = ?, start_date = ?, end_date = ?  WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, item.getBook().getId());
            preparedStatement.setInt(2, item.getOwner().getId());
            preparedStatement.setString(3, Util.getStringFromFormattedDate(item.getOpenExchangeDate()));
            preparedStatement.setInt(4, item.getReader().getId());
            if (item.getReader() == null) {
                preparedStatement.setNull(4, Types.INTEGER);
            } else {
                preparedStatement.setInt(4, item.getReader().getId());
            }
            preparedStatement.setString(5, Util.getStringFromFormattedDate(item.getStartDate()));
            preparedStatement.setString(6, Util.getStringFromFormattedDate(item.getEndDate()));
            preparedStatement.setInt(7, item.getId());
            preparedStatement.execute();

            loadedBookExchangeMap.replace(item.getId(), item);

//            bookMapper.update(item.getBook());
//            userMapper.update(item.getOwner());
//            if (item.getReader() != null) userMapper.update(item.getReader());
        } else {
            addBookExchange(item);
        }
    }

    @Override
    public void update() throws SQLException {
        bookMapper.update();
        userMapper.update();
        for (BookExchange bookExchange : loadedBookExchangeMap.values())
            update(bookExchange);
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
        loadedBookExchangeMap.clear();
    }
}
