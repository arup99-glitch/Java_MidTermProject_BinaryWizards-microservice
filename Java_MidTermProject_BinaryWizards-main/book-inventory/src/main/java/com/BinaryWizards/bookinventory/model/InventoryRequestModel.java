package com.BinaryWizards.bookinventory.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequestModel {

    private ItemModel item;

}
