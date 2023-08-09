package com.com.BinaryWizards.bookservice.model;

import com.com.BinaryWizards.bookservice.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponseModel {

    private Boolean success;
    private Boolean found;
    private List<String> message;
    private BookDto book;
    private Integer count;
    private List<BookDto> books;

}
