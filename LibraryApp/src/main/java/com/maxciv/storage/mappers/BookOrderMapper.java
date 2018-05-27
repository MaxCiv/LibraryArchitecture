package com.maxciv.storage.mappers;

import com.maxciv.Util;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.entities.users.Supplier;
import com.maxciv.storage.DataGateway;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookOrderMapper implements Mapper<BookOrder> {

    // COLUMNS = " id, book_id, supplier_id, start_date, end_date ";
    private static Map<Integer, BookOrder> loadedBookOrderMap = new HashMap<>();
    private Connection connection;

    private BookMapper bookMapper;
    private UserMapper userMapper;

    public BookOrderMapper() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
        bookMapper = new BookMapper();
        userMapper = new UserMapper();
    }

    public void addBookOrder(BookOrder bookOrder) throws SQLException {
        if (loadedBookOrderMap.values().contains(bookOrder)) {
            update(bookOrder);
        } else {
            String insertSQL = "INSERT INTO OrderBooks(book_id, supplier_id, start_date, end_date) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bookOrder.getBook().getId());
            preparedStatement.setInt(2, bookOrder.getSupplier().getId());
            preparedStatement.setString(3, Util.getStringFromFormattedDate(bookOrder.getStartDate()));
            preparedStatement.setString(4, Util.getStringFromFormattedDate(bookOrder.getEndDate()));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                bookOrder.setId(resultSet.getInt(1));
            }

            loadedBookOrderMap.put(bookOrder.getId(), bookOrder);

            bookMapper.update(bookOrder.getBook());
            userMapper.update(bookOrder.getSupplier());
        }
    }

    @Override
    public BookOrder findById(int id) throws SQLException {
        for (int loadedBookOrderId : loadedBookOrderMap.keySet()) {
            if (loadedBookOrderId == id)
                return loadedBookOrderMap.get(loadedBookOrderId);
        }

        // BookOrder not found, extract from database
        String selectSQL = "SELECT * FROM OrderBooks WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        int bookId = resultSet.getInt("book_id");
        int supplierId = resultSet.getInt("supplier_id");
        Date startDate = Util.getDateFromFormattedString(resultSet.getString("start_date"));
        Date endDate = Util.getDateFromFormattedString(resultSet.getString("end_date"));

        Book book = bookMapper.findById(bookId);
        Supplier supplier = (Supplier) userMapper.findById(supplierId);

        BookOrder newBookOrder = new BookOrder(id, book, supplier, startDate, endDate);

        loadedBookOrderMap.put(id, newBookOrder);

        return newBookOrder;
    }

    @Override
    public Map<Integer, BookOrder> findAll() throws SQLException {
        Map<Integer, BookOrder> allBookOrders = new HashMap<>();

        String selectSQL = "SELECT id FROM OrderBooks;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            allBookOrders.put(resultSet.getInt("id"), findById(resultSet.getInt("id")));
        }

        return allBookOrders;
    }

    @Override
    public void update(BookOrder item) throws SQLException {
        if (loadedBookOrderMap.values().contains(item)) {
            String updateSQL = "UPDATE OrderBooks SET  book_id = ?, supplier_id = ?, start_date = ?, end_date = ?  WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, item.getBook().getId());
            preparedStatement.setInt(2, item.getSupplier().getId());
            preparedStatement.setString(3, Util.getStringFromFormattedDate(item.getStartDate()));
            preparedStatement.setString(4, Util.getStringFromFormattedDate(item.getEndDate()));
            preparedStatement.setInt(5, item.getId());
            preparedStatement.execute();

            bookMapper.update(item.getBook());
            userMapper.update(item.getSupplier());
        } else {
            addBookOrder(item);
        }
    }

    @Override
    public void update() throws SQLException {
        bookMapper.update();
        userMapper.update();
        for (BookOrder bookOrder : loadedBookOrderMap.values())
            update(bookOrder);
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
        loadedBookOrderMap.clear();
    }
}
