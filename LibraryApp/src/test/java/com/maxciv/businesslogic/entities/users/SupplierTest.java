package com.maxciv.businesslogic.entities.users;

import com.maxciv.businesslogic.Condition;
import com.maxciv.businesslogic.Status;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.storage.MockRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class SupplierTest {

    private static MockRepository repository = new MockRepository();
    private Supplier supplier = new Supplier(-1, "test supplier", "1", "test name supplier");

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void finishOrder() throws Exception {
        BookOrder bookOrder = new BookOrder(-1, repository.findBookById(1), supplier, new Date());
        BookOrder finishedBookOrder = supplier.finishOrder(repository, bookOrder);
        assertNotNull(finishedBookOrder.getEndDate());
        assertEquals(finishedBookOrder.getBook().getStatus(), Status.LIBRARY);
        assertEquals(finishedBookOrder.getBook().getCondition(), Condition.IN_LIBRARY);
    }

    @Test
    public void denyOrder() throws Exception {
        BookOrder bookOrder = new BookOrder(-1, repository.findBookById(1), supplier, new Date());
        BookOrder finishedBookOrder = supplier.denyOrder(repository, bookOrder);
        assertNotNull(finishedBookOrder.getEndDate());
        assertEquals(finishedBookOrder.getBook().getCondition(), Condition.NOT_AVAILABLE);
    }
}