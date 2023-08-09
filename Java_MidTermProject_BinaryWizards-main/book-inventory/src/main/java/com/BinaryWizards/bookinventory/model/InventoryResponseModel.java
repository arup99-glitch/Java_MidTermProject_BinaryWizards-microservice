package com.BinaryWizards.bookinventory.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryResponseModel {

    private Boolean success;
    private Boolean found;
    private List<String> message;
    private ItemModel item;
    private List<ItemModel> items;

}
