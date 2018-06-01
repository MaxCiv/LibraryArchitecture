package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Condition;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.storage.MockRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ReaderTest {

    private static MockRepository repository = new MockRepository();
    private Reader reader = new Reader(-1, "test reader", "1", "test name reader");

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void borrowBook() throws Exception {
        BookBorrow bookBorrow = reader.borrowBook(repository, repository.findBookById(1));
        assertEquals(bookBorrow.getReader(), reader);
        assertNull(bookBorrow.getStartDate());
    }

    @Test
    public void getBookByExchange() throws Exception {
        reader.setId(-1);
        Book book = repository.findBookById(2);
        book.setCondition(Condition.ON_EXCHANGE);
        BookExchange bookExchange = new BookExchange(-1, book, (Reader)repository.findUserById(4), new Date());
        reader.setCountBookLeftForExchange(1);
        BookExchange newBookExchange = reader.getBookByExchange(repository, bookExchange);
        assertNull(newBookExchange.getStartDate());
        assertEquals(newBookExchange.getReader(), reader);
    }

    @Test
    public void openExchange() throws Exception {
        reader.setId(4);
        Book book = repository.findBookById(2);
        book.setCondition(Condition.RETURNED_TO_OWNER);
        BookExchange bookExchange = new BookExchange(-1, book, (Reader)repository.findUserById(4));
        BookExchange newBookExchange = reader.openExchange(repository, bookExchange);
        assertNotNull(newBookExchange.getOpenExchangeDate());
        assertNull(newBookExchange.getReader());
    }

    @Test
    public void closeExchangeAndTakeBook() throws Exception {
        reader.setId(4);
        Book book = repository.findBookById(2);
        book.setCondition(Condition.ON_EXCHANGE);
        BookExchange bookExchange = new BookExchange(-1, book, (Reader)repository.findUserById(4), new Date());
        BookExchange newBookExchange = reader.closeExchangeAndTakeBook(repository, bookExchange);
        assertNotNull(newBookExchange.getOpenExchangeDate());
        assertNotNull(newBookExchange.getStartDate());
        assertNotNull(newBookExchange.getEndDate());
        assertEquals(newBookExchange.getReader(), reader);
    }
}