package com.maxciv.service.googlebooks;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.util.ArrayList;
import java.util.List;

public class GoogleBooksService {

    private static final String APPLICATION_NAME = "LibraryApp/1.0";
    private static final String API_KEY = "AIzaSyDs4OzPHNkL_FG5IM5Ig7miI44VbRo9_WE";

    public GoogleBooksService() {
    }

    public List<GoogleBook> getBooksByTitle(String query) {
        // Query format: "[<author|isbn|intitle>:]<query>"
        String prefix = "intitle:";
        query = prefix + query;

        List<GoogleBook> googleBooks = new ArrayList<>();
        try {
            googleBooks = queryGoogleBooks(query);
            return googleBooks;
        } catch (Exception e) {
            e.printStackTrace();
            return googleBooks;
        }
    }

    public List<GoogleBook> getBooksByAuthor(String query) {
        // Query format: "[<author|isbn|intitle>:]<query>"
        String prefix = "author:";
        query = prefix + query;

        List<GoogleBook> googleBooks = new ArrayList<>();
        try {
            googleBooks = queryGoogleBooks(query);
            return googleBooks;
        } catch (Exception e) {
            e.printStackTrace();
            return googleBooks;
        }
    }

    private List<GoogleBook> queryGoogleBooks(String query) throws Exception {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        // Set up Books client.
        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
                .build();

        // Set query string and filter only Google eBooks.
        Books.Volumes.List volumesList = books.volumes().list(query);
        volumesList.setFilter("ebooks");

        List<GoogleBook> googleBooks = new ArrayList<>();

        // Execute the query.
        Volumes volumes = volumesList.execute();
        if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
            return googleBooks;
        }

        for (Volume volume : volumes.getItems()) {
            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();

            String title, author, publisher, publishYear;
            title = volumeInfo.getTitle();
            author = (volumeInfo.getAuthors() != null && !volumeInfo.getAuthors().isEmpty()) ? String.join(", ", volumeInfo.getAuthors()) : "";
            publisher = volumeInfo.getPublisher();
            publishYear = volumeInfo.getPublishedDate();
            googleBooks.add(new GoogleBook(title, author, publisher, publishYear));
        }

        return googleBooks;
    }
}
