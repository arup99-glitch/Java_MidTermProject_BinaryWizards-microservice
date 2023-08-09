package com.com.BinaryWizards.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {

    private Long bookId;
    private String bookName;
    private String authorName;
    private String genre;
    private Double price;
    private Integer quantity;

}

