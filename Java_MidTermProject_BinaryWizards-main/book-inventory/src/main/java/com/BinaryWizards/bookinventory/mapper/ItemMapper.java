package com.BinaryWizards.bookinventory.mapper;

import com.BinaryWizards.bookinventory.entity.ItemEntity;
import com.BinaryWizards.bookinventory.model.ItemModel;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ItemMapper {
    public ItemModel mapToItemModel(ItemEntity itemEntity){
        return ItemModel.builder()
                .id(itemEntity.getId())
                .price(itemEntity.getPrice())
                .quantity(itemEntity.getQuantity())
                .build();
    }
}
