package com.com.BinaryWizards.bookservice.service;

import com.com.BinaryWizards.bookservice.dto.BookDto;
import com.com.BinaryWizards.bookservice.model.BuyRequestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BookService {

    /**
     * Create Book
     * @param requestModel accepts BookModel
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> createBook(BookDto requestModel);

    /**
     * Update Book
     * @param id accepts long ID
     * @param requestModel accepts BookModel
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> updateBook(Long id, BookDto requestModel);

    /**
     * Delete Book
     * @param id accepts long ID
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> deleteBook(Long id);

    /**
     * Get All Books
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> getAllBooks();

    /**
     * Get Book by ID
     * @param id accepts long ID
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> getBookById(Long id);

    /**
     * Buy Book
     * @param buyRequestModel accepts BuyRequestModel
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> buyBook(BuyRequestModel buyRequestModel);

}
