package com.com.BinaryWizards.bookservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemModel {

    private Long id;
    private Double price;
    private Integer quantity;

}
