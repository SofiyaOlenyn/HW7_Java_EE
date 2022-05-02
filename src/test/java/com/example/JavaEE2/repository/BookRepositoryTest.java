package com.example.JavaEE2.repository;

import com.example.JavaEE2.model.Book;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class BookRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldFindByIsbn() {

        Book book1 = (Book.builder().isbn("isbn1").title("Tittle_1").author("author_1").build());
        Book book2 = (Book.builder().isbn("isbn2").title("Tittle_2").author("author_2").build());

        em.persist(book1);
        em.persist(book2);
        em.flush();

        Book result = bookRepository.findByIsbn(book1.getIsbn());

        Assert.assertEquals(result,book1);

    }

    @Test
    void shouldFindWihFilters() {

        Book book1 = (Book.builder().isbn("isbn1").title("Tittle_1").author("author_1").build());
        Book book2 = (Book.builder().isbn("isbn2").title("Tittle_2").author("author_2").build());
        Book book3 = (Book.builder().isbn("1").title("1").author("1").build());

        em.persist(book1);
        em.persist(book2);
        em.persist(book3);
        em.flush();

        List<Book> result = bookRepository.findAllWhereTitleLikeOrAuthorLikeOrIsbnLike("author");

        Assert.assertEquals(result,List.of(book1, book2));

    }

    @Test
    void shouldCreateBook() {

        Book book1 = (Book.builder().isbn("bla").title("bla").author("bla").build());

        bookRepository.save(book1);

        Book result = bookRepository.findByIsbn(book1.getIsbn());

        Assert.assertEquals(result,book1);

    }
}
