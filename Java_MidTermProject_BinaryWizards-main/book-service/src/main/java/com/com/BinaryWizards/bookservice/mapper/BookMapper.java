package com.com.BinaryWizards.bookservice.mapper;

import com.com.BinaryWizards.bookservice.entity.BookEntity;
import com.com.BinaryWizards.bookservice.dto.BookDto;
import com.com.BinaryWizards.bookservice.model.ItemModel;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto mapToBookModel(BookEntity book, ItemModel itemModel) {
        return BookDto.builder()
                .bookId(book.getBookId())
                .bookName(book.getBookName())
                .authorName(book.getAuthorName())
                .genre(book.getGenre())
                .quantity(itemModel.getQuantity())
                .price(itemModel.getPrice())
                .build();
    }

}
