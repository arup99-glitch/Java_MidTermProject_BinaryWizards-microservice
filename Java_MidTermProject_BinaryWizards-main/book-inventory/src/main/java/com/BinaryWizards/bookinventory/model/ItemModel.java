package com.BinaryWizards.bookinventory.model;

import com.BinaryWizards.bookinventory.validator.ErrorMessageConstants;
import lombok.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemModel {

    @NotNull(message = ErrorMessageConstants.INVENTORY_ID_EMPTY)
    private Long id;
    private Double price;
    private Integer quantity;

}
