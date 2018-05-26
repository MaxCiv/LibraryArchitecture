package com.maxciv;

import com.maxciv.businesslogic.entities.Book;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting...");
        Book book = new Book(0, "Title", "Author", "Publisher", 1963);
        List<Book> requiredBooks = new ArrayList<>();
        requiredBooks.add(book);
//        Librarian01 librarian = new Librarian01(0, "Zero", requiredBooks, new ArrayList<>(), new ArrayList<>());
//        book.setTitle("NewTitle");
//        System.out.println(librarian.getRequiredBooks().get(0).getTitle());
    }
}
