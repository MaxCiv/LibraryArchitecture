package com.maxciv.storage.mappers;

import com.maxciv.businesslogic.Condition;
import com.maxciv.businesslogic.Status;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.storage.DataGateway;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BookMapper implements Mapper<Book> {

    // COLUMNS = " id, title, author, publisher, publish_year, status, condition ";
    private static Map<Integer, Book> loadedBookMap = new HashMap<>();
    private Connection connection;

    public BookMapper() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
    }

    public void addBook(Book book) throws SQLException {
        if (loadedBookMap.values().contains(book)) {
            update(book);
        } else {
            String insertSQL = "INSERT INTO Book(title, author, publisher, publish_year, status, condition) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setInt(4, book.getPublishYear());
            preparedStatement.setInt(5, book.getStatus().getStatusId());
            preparedStatement.setInt(6, book.getCondition().getConditionId());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                book.setId(resultSet.getInt(1));
            }

            loadedBookMap.put(book.getId(), book);
        }
    }

    @Override
    public Book findById(int id) throws SQLException {
        for (int loadedBookId : loadedBookMap.keySet()) {
            if (loadedBookId == id)
                return loadedBookMap.get(loadedBookId);
        }

        // Book not found, extract from database
        String selectSQL = "SELECT * FROM Book WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) return null;

        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        String publisher = resultSet.getString("publisher");
        int publish_year = resultSet.getInt("publish_year");
        int status = resultSet.getInt("status");
        int condition = resultSet.getInt("condition");

        Book newBook = new Book(id, title, author, publisher, publish_year, Status.valueOf(status), Condition.valueOf(condition));

        loadedBookMap.put(id, newBook);

        return newBook;
    }

    @Override
    public Map<Integer, Book> findAll() throws SQLException {
        Map<Integer, Book> allBooks = new HashMap<>();

        String selectSQL = "SELECT id FROM Book;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            allBooks.put(resultSet.getInt("id"), findById(resultSet.getInt("id")));
        }

        return allBooks;
    }

    @Override
    public void update(Book item) throws SQLException {
        if (loadedBookMap.values().contains(item)) {
            String updateSQL = "UPDATE Book SET  title = ?, author = ?, publisher = ?, publish_year = ?, status = ?, condition = ?  WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setString(2, item.getAuthor());
            preparedStatement.setString(3, item.getPublisher());
            preparedStatement.setInt(4, item.getPublishYear());
            preparedStatement.setInt(5, item.getStatus().getStatusId());
            preparedStatement.setInt(6, item.getCondition().getConditionId());
            preparedStatement.setInt(7, item.getId());
            preparedStatement.execute();
        } else {
            addBook(item);
        }
    }

    @Override
    public void update() throws SQLException {
        for (Book book : loadedBookMap.values())
            update(book);
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public void clear() {
        loadedBookMap.clear();
    }
}
