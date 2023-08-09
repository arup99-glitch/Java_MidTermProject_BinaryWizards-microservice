package com.BinaryWizards.bookinventory.model;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemByIDsResponseModel {

    private boolean found;
    private List<ItemModel> items;

}
