package com.com.BinaryWizards.bookservice.service.impl;

import com.com.BinaryWizards.bookservice.dto.BookDto;
import com.com.BinaryWizards.bookservice.entity.BookEntity;
import com.com.BinaryWizards.bookservice.mapper.BookMapper;
import com.com.BinaryWizards.bookservice.model.*;
import com.com.BinaryWizards.bookservice.repository.BookRepository;
import com.com.BinaryWizards.bookservice.service.BookService;
import com.com.BinaryWizards.bookservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import javax.validation.Valid;
import java.util.*;

@Service
@RequiredArgsConstructor

public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final JwtService jwtService;
    private final BookRepository bookRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public ResponseEntity<?> createBook(@Valid BookDto bookRequestModel) {
        BookEntity book = BookEntity.builder()
                .bookName(bookRequestModel.getBookName())
                .genre(bookRequestModel.getGenre())
                .authorName(bookRequestModel.getAuthorName())
                .build();
        BookEntity savedBook = bookRepository.save(book);
        try {
            ItemModel itemModel = ItemModel.builder()
                    .id(savedBook.getBookId())
                    .price(bookRequestModel.getPrice())
                    .quantity(bookRequestModel.getQuantity())
                    .build();
            InventoryResponseModel inventoryResponseModel = webClientBuilder.build()
                    .post()
                    .uri("http://localhost:8080/book-inventory/create")
                    .header("Authorization", jwtService.getJwtToken())
                    .bodyValue(InventoryRequestModel.builder()
                            .item(itemModel)
                            .build())
                    .retrieve()
                    .bodyToMono(InventoryResponseModel.class)
                    .block();
            if (inventoryResponseModel != null && inventoryResponseModel.isSuccess()) {
                ItemModel savedItemModel = inventoryResponseModel.getItem();
                if (savedItemModel != null) {
                    return new ResponseEntity<>(BookResponseModel.builder()
                            .success(true)
                            .message(List.of("Book Created"))
                            .book(bookMapper.mapToBookModel(savedBook, savedItemModel))
                            .build(), HttpStatus.CREATED);
                }
            }
        } catch (Exception e) {
            bookRepository.delete(savedBook);
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(BookResponseModel.builder()
                .success(false)
                .message(List.of("Book Creation failed"))
                .book(bookRequestModel)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> updateBook(Long id, BookDto bookRequestModel) {
        Optional<BookEntity> bookById = bookRepository.findById(id);
        if (bookById.isEmpty()) {
            return new ResponseEntity<>(BookResponseModel.builder()
                    .success(false)
                    .message(List.of("Book not found"))
                    .book(bookRequestModel)
                    .build(), HttpStatus.BAD_REQUEST);
        }
        BookEntity book = bookById.get();
        if (StringUtils.hasText(bookRequestModel.getBookName())) {
            book.setBookName(bookRequestModel.getBookName());
        }
        if (StringUtils.hasText(bookRequestModel.getAuthorName())) {
            book.setAuthorName(bookRequestModel.getAuthorName());
        }
        if (StringUtils.hasText(bookRequestModel.getGenre())) {
            book.setGenre(bookRequestModel.getGenre());
        }
        BookEntity oldBookEntity = BookEntity.builder()
                .bookId(book.getBookId())
                .bookName(book.getBookName())
                .authorName(book.getAuthorName())
                .genre(book.getGenre())
                .build();
        BookEntity savedBook = bookRepository.save(book);
        try {
            InventoryRequestModel inventoryRequestModel = InventoryRequestModel.builder()
                    .item(ItemModel.builder()
                            .quantity(bookRequestModel.getQuantity())
                            .price(bookRequestModel.getPrice())
                            .build())
                    .build();

            String bookId = String.valueOf(id);
            InventoryResponseModel inventoryResponseModel = webClientBuilder.build()
                    .patch()
                    .uri("http://localhost:8080/book-inventory/update/{bookId}", bookId)
                    .header("Authorization", jwtService.getJwtToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(inventoryRequestModel)
                    .retrieve()
                    .bodyToMono(InventoryResponseModel.class).block();
            if (inventoryResponseModel != null && inventoryResponseModel.isSuccess()) {
                ItemModel savedItemModel = inventoryResponseModel.getItem();
                if (savedItemModel != null) {
                    return new ResponseEntity<>(BookResponseModel.builder()
                            .success(true)
                            .message(List.of("Book Updated"))
                            .book(bookMapper.mapToBookModel(savedBook, savedItemModel))
                            .build(), HttpStatus.CREATED);
                }
            }
        } catch (Exception e) {
            bookRepository.save(oldBookEntity);
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(BookResponseModel.builder()
                .success(false)
                .message(List.of("Book Updating failed"))
                .book(bookRequestModel)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> deleteBook(Long id) {
        Optional<BookEntity> bookById = bookRepository.findById(id);
        if (bookById.isEmpty()) {
            return new ResponseEntity<>(BookResponseModel.builder()
                    .success(false)
                    .message(List.of("No book found with this id!"))
                    .build(), HttpStatus.NOT_FOUND);
        }
        try {
            InventoryResponseModel inventoryResponseModel = webClientBuilder.build()
                    .delete()
                    .uri("http://localhost:8080/book-inventory/delete/{id}", id)
                    .header("Authorization", jwtService.getJwtToken())
                    .retrieve()
                    .bodyToMono(InventoryResponseModel.class).block();
            if (inventoryResponseModel != null && inventoryResponseModel.isSuccess()) {
                ItemModel deletedItem = inventoryResponseModel.getItem();
                if (deletedItem != null) {
                    bookRepository.delete(bookById.get());
                    return new ResponseEntity<>(BookResponseModel.builder()
                            .success(true)
                            .message(List.of("Book Deleted"))
                            .build(), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(BookResponseModel.builder()
                .success(false)
                .message(List.of("Book Deletion failed"))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> getAllBooks() {
        List<BookEntity> books = bookRepository.findAll();
        List<Long> bookIds = books.stream().map(BookEntity::getBookId).toList();
        try {
            InventoryResponseModel inventoryResponseModel = webClientBuilder.build()
                    .post()
                    .uri("http://localhost:8080/book-inventory")
                    .header("Authorization", jwtService.getJwtToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(
                            InventoryRequestModel.builder()
                                    .ids(bookIds)
                                    .build()
                    ).retrieve()
                    .bodyToMono(InventoryResponseModel.class).block();
            if (inventoryResponseModel != null && inventoryResponseModel.isFound()) {
                List<ItemModel> retrievedItems = inventoryResponseModel.getItems();
                if (retrievedItems != null) {
                    Map<Long, ItemModel> items = new HashMap<>(retrievedItems.size());
                    for (ItemModel itemModel : retrievedItems) {
                        items.put(itemModel.getId(), itemModel);
                    }
                    List<BookDto> bookDtos = new ArrayList<>();
                    for (BookEntity book : books) {
                        bookDtos.add(bookMapper.mapToBookModel(book, items.get(book.getBookId())));
                    }

                    return new ResponseEntity<>(BookResponseModel.builder()
                            .found(true)
                            .message(List.of("Books found"))
                            .count(bookDtos.size())
                            .books(bookDtos)
                            .build(), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(BookResponseModel.builder()
                .found(false)
                .message(List.of("No Book found"))
                .build(), HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<?> getBookById(Long id) {
        Optional<BookEntity> bookById = bookRepository.findById(id);
        if (bookById.isEmpty()) {
            return new ResponseEntity<>(BookResponseModel.builder()
                    .found(false)
                    .message(List.of("Book not found"))
                    .build(), HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookById.get();
        String bookId = String.valueOf(id);
        try {
            InventoryResponseModel inventoryResponseModel = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8080/book-inventory/{bookId}", bookId)
                    .header("Authorization", jwtService.getJwtToken())
                    .retrieve()
                    .bodyToMono(InventoryResponseModel.class).block();
            if (inventoryResponseModel != null && inventoryResponseModel.isFound()) {
                ItemModel retrievedItem = inventoryResponseModel.getItem();
                if (retrievedItem != null) {
                    return new ResponseEntity<>(BookResponseModel.builder()
                            .success(true)
                            .message(List.of("Book found"))
                            .book(bookMapper.mapToBookModel(bookEntity, retrievedItem))
                            .build(), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>(BookResponseModel.builder()
                .found(false)
                .message(List.of("Book not found"))
                .build(), HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<?> buyBook(BuyRequestModel buyRequestModel) {
        Optional<BookEntity> bookById = bookRepository.findById(buyRequestModel.getBookId());
        if (bookById.isEmpty()) {
            return new ResponseEntity<>(BuyResponseModel.builder()
                    .success(false)
                    .message("Book not found!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookById.get();
        InventoryRequestModel inventoryRequestModel = InventoryRequestModel.builder()
                .item(ItemModel.builder()
                        .quantity(buyRequestModel.getQuantity())
                        .id(buyRequestModel.getBookId())
                        .build())
                .build();
        try {
            InventoryResponseModel inventoryResponseModel = webClientBuilder.build()
                    .patch()
                    .uri("http://localhost:8080/book-inventory/buy")
                    .header("Authorization", jwtService.getJwtToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(inventoryRequestModel)
                    .retrieve()
                    .bodyToMono(InventoryResponseModel.class)
                    .block();
            if (inventoryResponseModel != null && inventoryResponseModel.isSuccess()) {
                ItemModel savedItemModel = inventoryResponseModel.getItem();
                if (savedItemModel != null) {
                    return new ResponseEntity<>(BookResponseModel.builder()
                            .success(true)
                            .message(List.of("Order has been placed successfully!"))
                            .book(bookMapper.mapToBookModel(bookEntity, savedItemModel))
                            .build(), HttpStatus.ACCEPTED);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(BookResponseModel.builder()
                    .success(true)
                    .message(List.of(e.getMessage()))
                    .build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(BookResponseModel.builder()
                .success(true)
                .message(List.of("Order not placed"))
                .build(), HttpStatus.BAD_REQUEST);

    }

}
