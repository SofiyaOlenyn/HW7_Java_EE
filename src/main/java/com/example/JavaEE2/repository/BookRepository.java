package com.example.JavaEE2.repository;

import com.example.JavaEE2.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByIsbn(String isbn);

    List<Book> findAllByTitleAndIsbn( String search);
}
