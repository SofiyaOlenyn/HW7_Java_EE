package com.example.JavaEE2.controller;

import com.example.JavaEE2.controllers.BookController;
import com.example.JavaEE2.model.Book;
import com.example.JavaEE2.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookService bookService;

    private static ArrayList<Book> db = new ArrayList<>();

    @BeforeEach
    private void init() {
        db.add(Book.builder().isbn("12345").title("book1").author("author1").build());
        db.add(Book.builder().isbn("12346").title("book2").author("author2").build());
        db.add(Book.builder().isbn("12347").title("book3").author("author3").build());
    }

    @Test
    void getBooks_isOk() throws Exception {

        String value = objectMapper.writeValueAsString(db);
        Mockito.when(bookService.getAllBooks()).thenReturn(db);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/get-books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(value));

        Mockito.verify(bookService, Mockito.times(1)).getAllBooks();
    }

    @Test
    public void getBooks_searchByTitle_isOk() throws Exception {

        String search = "book2";

        String expected = objectMapper.writeValueAsString(db);
        Mockito.when(bookService.searchBooks(search)).thenReturn(db);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/get-books").queryParam("getBy", search)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));

        Mockito.verify(bookService, Mockito.times(1)).searchBooks(search);
    }


    @Test
    public void addBook_isOk() throws Exception {

        Book book = Book.builder().isbn("11111").title("book4").author("author4").build();
        String valueAsString = objectMapper.writeValueAsString(book);

        Mockito
                .when(bookService.addBook(book))
                .thenReturn(true);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/add-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("The book was added successfully"));
    }

    @Test
    public void addBook_status400() throws Exception {

        Book book = Book.builder().isbn("12346").title("book2").author("author2").build();

        String valueAsString = objectMapper.writeValueAsString(book);

        Mockito
                .when(bookService.addBook(book))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/add-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().string("Try again"));
    }

}
