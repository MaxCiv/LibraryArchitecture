package com.maxciv.service.googlebooks;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GoogleBooksServiceTest {

    private GoogleBooksService googleBooksService = new GoogleBooksService();

    @Test
    public void getBooksByTitle() {
        String query = "java";
        List<GoogleBook> googleBooks = googleBooksService.getBooksByTitle(query);
        assertTrue("Books by Title '" + query + "' not found (Google Books).", googleBooks.size() > 0);
    }

    @Test
    public void getBooksByAuthor() {
        String query = "Tolkien";
        List<GoogleBook> googleBooks = googleBooksService.getBooksByAuthor(query);
        assertTrue("Books by Author '" + query + "' not found (Google Books).", googleBooks.size() > 0);
    }
}