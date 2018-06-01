package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.storage.DataGateway;
import com.maxciv.storage.MappersRepository;
import com.maxciv.storage.MockRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class LibrarianTest {

    private static MockRepository repository = new MockRepository();
    private Librarian librarian = new Librarian(-1, "test librarian", "1", "test name librarian");
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
    public void addNewBook() {
        String title = "test book", author = "test author", publisher = "test publisher";
        int publishYear = 1996;
        Book newBook = librarian.addNewBook(repository, title, author, publisher, publishYear, 0, 0);
        assertEquals(newBook.getTitle(), title);
        assertEquals(newBook.getAuthor(), author);
        assertEquals(newBook.getPublisher(), publisher);
        assertEquals(newBook.getPublishYear(), publishYear);

    }

    @Test
    public void addNewUser() {
        String login = "test book", password = "test author", name = "test publisher", role = "Reader";
        User user = librarian.addNewUser(repository, login, password, name, role);
        assertEquals(user.getLogin(), login);
        assertEquals(user.getPassword(), DigestUtils.sha1Hex(password));
        assertEquals(user.getName(), name);
        assertEquals(user.getRole().getRoleName(), role);
    }

    @Test
    public void openNewOrder() throws Exception {
        assertNotNull(librarian.openNewOrder(repository, 4, 3));
    }
}