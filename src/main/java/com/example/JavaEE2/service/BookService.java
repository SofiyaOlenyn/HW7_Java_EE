package com.example.JavaEE2.service;

import com.example.JavaEE2.model.Book;
import com.example.JavaEE2.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        bookRepository.save(Book.builder().id(1).isbn("isbn1").title("Tittle_1").author("author_1").build());
        bookRepository.save(Book.builder().id(2).isbn("isbn2").title("Tittle_2").author("author_2").build());
        bookRepository.save(Book.builder().id(3).isbn("isbn3").title("Tittle_3").author("author_3").build());
    }

    @Transactional
    public boolean addBook(final Book newBook) {
        boolean isUnique = bookNotExists(newBook);
        if (isUnique) {
            bookRepository.save(newBook);
        }
        return isUnique;
    }

    @Transactional
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public List<Book> searchBooks(final String s) {
        return bookRepository.findAllWhereTitleLikeOrAuthorLikeOrIsbnLike(s);
    }

    @Transactional
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    private boolean bookNotExists(Book newBook) {
       List<Book> books = getAllBooks();
        for (Book book : books) {
            if (book.getIsbn().equals(newBook.getIsbn())) {
                return false;
            }
        }
        return true;
    }
}
