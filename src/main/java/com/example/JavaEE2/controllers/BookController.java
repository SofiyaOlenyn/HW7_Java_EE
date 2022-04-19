package com.example.JavaEE2.controllers;

import com.example.JavaEE2.model.Book;
import com.example.JavaEE2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
public class BookController {

    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getMain(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "main";
    }

    @ResponseBody
    @RequestMapping(value = "/add-book", method = RequestMethod.POST)
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        boolean successfullyAdded = bookService.addBook(book);
        return ResponseEntity.status(successfullyAdded ? 200 : 400)
                .body(successfullyAdded ? "The book was added successfully" : "Try again");
    }

    @ResponseBody
    @RequestMapping(value = "/get-books", method = RequestMethod.GET)
    public ResponseEntity<Collection<Book>> getBooks(@RequestParam(name = "getBy", required = false) String search) {
        if (search == null) {
            return ResponseEntity.status(200)
                    .body(bookService.getAllBooks());
        }
        List<Book> result = bookService.searchBooks(search);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/book/{isbn}")
    public String getBook(@PathVariable String isbn, Model model) {
        Book book = bookService.getBookByIsbn(isbn);
        model.addAttribute("book", book);
        return "book";
    }
}
