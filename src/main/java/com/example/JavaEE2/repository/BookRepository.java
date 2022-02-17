package com.example.JavaEE2.repository;

import com.example.JavaEE2.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Slf4j
@Component
public class BookRepository {

    private static final ArrayList<Book> BOOKS_DB = initDB();

    public Book save(final Book book) {
        BOOKS_DB.add(book);
        return book;
    }


    public ArrayList<Book> getAll() {
        return BOOKS_DB;
    }


    private static ArrayList<Book> initDB() {
        final ArrayList<Book> book_db = new ArrayList<>();
        book_db.add(Book.builder().isbn("12345").title("book1").author("author1").build());
        book_db.add(Book.builder().isbn("12346").title("book2").author("author2").build());
        book_db.add(Book.builder().isbn("12347").title("book3").author("author3").build());
        return book_db;
    }
}
