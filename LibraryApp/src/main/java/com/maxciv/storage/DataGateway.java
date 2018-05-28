package com.maxciv.storage;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DataGateway {

    private static final String URL = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1111";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static DataGateway dataGateway;
    private static MysqlDataSource mysqlDataSource;

    private DataGateway() {
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        mysqlDataSource.setUser(USER);
        mysqlDataSource.setPassword(PASSWORD);

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DataGateway getInstance() {
        if (dataGateway == null)
            dataGateway = new DataGateway();
        return dataGateway;
    }

    public MysqlDataSource getDataSource() {
        return mysqlDataSource;
    }

    public void dropAll() throws SQLException {
        executeSqlScript(getDataSource().getConnection(), new File(DataGateway.class.getResource("/createDB.sql").getFile()));
    }

    private void executeSqlScript(Connection connection, File inputFile) {
        // Delimiter
        String delimiter = ";";

        // Create scanner
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile).useDelimiter(delimiter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Loop through the SQL file statements
        Statement currentStatement = null;
        while (scanner.hasNext()) {
            // Get statement
            String rawStatement = scanner.next() + delimiter;
            try {
                // Execute statement
                currentStatement = connection.createStatement();
                currentStatement.execute(rawStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        currentStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                currentStatement = null;
            }
        }
        scanner.close();
    }
}
