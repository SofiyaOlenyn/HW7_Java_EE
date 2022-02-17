package com.example.JavaEE2.service;

import com.example.JavaEE2.model.Book;
import com.example.JavaEE2.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public boolean addBook(final Book newBook) {
        boolean isUnique = bookNotExists(newBook);
        if (isUnique) {
            bookRepository.save(newBook);
        }
        return isUnique;
    }

    public ArrayList<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    public ArrayList<Book> searchBooks(final String string) {

        ArrayList<Book> books = getAllBooks();
        ArrayList<Book> resultArrList = new ArrayList<>();
        for (Book book : books) {
            if (book.getIsbn().toLowerCase().contains(string.toLowerCase()) ||
                    book.getTitle().toLowerCase().contains(string.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(string.toLowerCase())) {
                resultArrList.add(book);
            }
        }
        return resultArrList;
    }

    private boolean bookNotExists(Book newBook) {
        ArrayList<Book> books = getAllBooks();
        for (Book book : books) {
            if (book.getIsbn().equals(newBook.getIsbn())) {
                return false;
            }
        }
        return true;
    }

}

