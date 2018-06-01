package com.maxciv.storage;

import com.maxciv.businesslogic.entities.users.Reader;
import com.maxciv.businesslogic.entities.users.Supplier;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.Assert.*;

public class MappersRepositoryTest {

    private MappersRepository repository = new MappersRepository();
    private static Connection connection;
    private Savepoint savepoint;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
        connection.setAutoCommit(false);
    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() throws Exception {
        savepoint = connection.setSavepoint();
    }

    @After
    public void tearDown() throws Exception {
        connection.rollback(savepoint);
    }

    @Test
    public void logInUser() {
        assertNotNull(repository.logInUser("root", "1111"));
    }

    @Test
    public void findUserById() {
        assertNotNull(repository.findUserById(1));
        assertNotNull(repository.findUserById(2));
    }

    @Test
    public void findBookById() {
        assertNotNull(repository.findBookById(1));
    }

    @Test
    public void getAllBooks() {
        assertTrue(repository.getAllBooks().size() > 0);
    }

    @Test
    public void getAllBorrows() {
        assertTrue(repository.getAllBorrows().size() > 0);
    }

    @Test
    public void getAllUserBorrows() {
        assertTrue(repository.getAllUserBorrows((Reader)repository.findUserById(4)).size() > 0);
    }

    @Test
    public void getAllExchanges() {
        assertTrue(repository.getAllExchanges().size() > 0);
    }

    @Test
    public void getAllUserExchanges() {
        assertTrue(repository.getAllUserExchanges((Reader)repository.findUserById(4)).size() > 0);
    }

    @Test
    public void getAllOrderings() {
        assertTrue(repository.getAllOrderings().size() > 0);
    }

    @Test
    public void getAllUserOrderings() {
        assertTrue(repository.getAllUserOrderings((Supplier)repository.findUserById(3)).size() > 0);
    }

    @Test
    public void getAllRequireConfirmation() {
        assertTrue(repository.getAllRequireConfirmation().size() > 0);
    }

    @Test
    public void getAllUsers() {
        assertTrue(repository.getAllUsers().size() > 0);
    }
}