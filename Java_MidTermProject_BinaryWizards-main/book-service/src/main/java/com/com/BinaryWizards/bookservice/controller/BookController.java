package com.com.BinaryWizards.bookservice.controller;

import com.com.BinaryWizards.bookservice.model.BuyRequestModel;
import com.com.BinaryWizards.bookservice.model.BookRequestModel;
import com.com.BinaryWizards.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<?> newBook(@RequestBody BookRequestModel bookRequestModel) {
        System.out.println(bookRequestModel.getBook());
        return bookService.createBook(bookRequestModel.getBook());
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookRequestModel bookRequestModel) {
        return bookService.updateBook(id, bookRequestModel.getBook());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id) {
        return bookService.deleteBook(Long.parseLong(id));
    }

    @GetMapping("book/all")
    public ResponseEntity<?> allBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable String bookId) {
        return bookService.getBookById(Long.parseLong(bookId));
    }

    @PatchMapping("/book/buy")
    public ResponseEntity<?> buyBook(@RequestBody BuyRequestModel buyRequestModel) {
        return bookService.buyBook(buyRequestModel);
    }

}
