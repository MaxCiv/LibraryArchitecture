package com.maxciv.gui.facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.service.googlebooks.GoogleBook;
import com.maxciv.service.googlebooks.GoogleBooksService;
import com.maxciv.storage.MappersRepository;

import java.util.List;

public class ServiceFacade {

    private MappersRepository repository;

    public ServiceFacade() {
        this.repository = new MappersRepository();
    }

    // Http Server
    public String getAllBooksAsJson() throws NotFoundException {
        List<Book> books = repository.getAllBooks();
        if (books == null) throw new NotFoundException("All books not found.");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(books);
    }

    // Google Books
    public List<GoogleBook> searchGoogleBooksByTitle(String title) {
        return new GoogleBooksService().getBooksByTitle(title);
    }

    public List<GoogleBook> searchGoogleBooksByAuthor(String author) {
        return new GoogleBooksService().getBooksByAuthor(author);
    }
}
