package com.BinaryWizards.bookinventory.service.impl;

import com.BinaryWizards.bookinventory.entity.ItemEntity;
import com.BinaryWizards.bookinventory.mapper.ItemMapper;
import com.BinaryWizards.bookinventory.model.*;
import com.BinaryWizards.bookinventory.repository.InventoryRepository;
import com.BinaryWizards.bookinventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
private final ItemMapper itemMapper;
    @Override
    public ResponseEntity<?> createItem(ItemModel itemModel) {
        Optional<ItemEntity> byId = inventoryRepository.findById(itemModel.getId());
        if (byId.isPresent()) {
            return new ResponseEntity<>(InventoryResponseModel.builder()
                    .success(false)
                    .message(List.of("Item with the id already exists"))
                    .item(itemModel)
                    .build(), HttpStatus.BAD_REQUEST);
        }
        ItemEntity newItem = ItemEntity.builder()
                .id(itemModel.getId())
                .price(itemModel.getPrice())
                .quantity(itemModel.getQuantity())
                .build();
        ItemEntity savedItem = inventoryRepository.save(newItem);

        return new ResponseEntity<>(InventoryResponseModel.builder()
                .success(true)
                .message(List.of("Item created successfully"))
                .item(itemMapper.mapToItemModel(savedItem))
                .build(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAllItems() {
        List<ItemEntity> items = inventoryRepository.findAll();
        if (items.size() > 0) {
            return new ResponseEntity<>(
                    ItemByIDsResponseModel.builder()
                            .found(true)
                            .items(
                                    items.stream()
                                            .map(item -> itemMapper.mapToItemModel(item))
                                            .collect(Collectors.toList())
                            )
                            .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ItemByIDsResponseModel.builder()
                .found(false)
                .build(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getItem(Long bookId) {
        Optional<ItemEntity> itemById = inventoryRepository.findById(bookId);
        if (itemById.isPresent()) {
            ItemEntity item = itemById.get();
            return new ResponseEntity<>(
                    InventoryResponseModel.builder()
                            .found(true)
                            .item(itemMapper.mapToItemModel(item))
                            .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                ItemByIDsResponseModel.builder()
                        .found(false)
                        .build(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> updateBook(Long bookId, ItemModel itemModel) {
        Optional<ItemEntity> inventoryEntityOptional = inventoryRepository.findById(bookId);

        if (inventoryEntityOptional.isPresent()) {
            ItemEntity itemEntity = inventoryEntityOptional.get();
            Double price = itemModel.getPrice();
            if (price != null && price > 0.0D) {
                itemEntity.setPrice(price);
            }
            Integer quantity = itemModel.getQuantity();
            if (quantity != null && quantity >= 0) {
                itemEntity.setPrice(quantity);
            }
            ItemEntity updatedItemEntity = inventoryRepository.save(itemEntity);
            return new ResponseEntity<>(
                    InventoryResponseModel.builder()
                            .success(true)
                            .item(itemMapper.mapToItemModel(updatedItemEntity))
                            .build(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(
                InventoryResponseModel.builder()
                        .success(false)
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @Override
    public ResponseEntity<?> deleteItem(Long bookId) {
        Optional<ItemEntity> book = inventoryRepository.findById(bookId);
        if (book.isPresent()) {
            ItemEntity item = book.get();
            inventoryRepository.delete(item);
            return new ResponseEntity<>(InventoryResponseModel.builder()
                    .success(true)
                    .item(itemMapper.mapToItemModel(item))
                    .build(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(InventoryResponseModel.builder()
                .success(false)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> getAllBooksById(List<Long> ids) {
        if (ids == null) {
            return new ResponseEntity<>(ItemByIDsResponseModel.builder()
                    .found(false)
                    .build(), HttpStatus.NOT_FOUND);
        }
        List<ItemEntity> allById = inventoryRepository.findAllById(ids);
        if (allById.isEmpty()) {
            return new ResponseEntity<>(ItemByIDsResponseModel.builder()
                    .found(false)
                    .build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ItemByIDsResponseModel.builder()
                .found(true)
                .items(
                        allById.stream()
                                .map(itemEntity -> itemMapper.mapToItemModel(itemEntity))
                                .collect(Collectors.toList())
                )
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> buyItem(InventoryRequestModel inventoryRequestModel) {
        ItemModel itemModel = inventoryRequestModel.getItem();
        Optional<ItemEntity> inventoryEntityOptional = inventoryRepository.findById(itemModel.getId());

        if (inventoryEntityOptional.isPresent()) {
            ItemEntity itemEntity = inventoryEntityOptional.get();
            Integer itemQuantity = inventoryEntityOptional.get().getQuantity(); // inventory quantity
            Integer quantity = itemModel.getQuantity(); // buy quantity

            if (quantity != null && quantity >= 0) {
                if (itemQuantity >= quantity) {
                    int newQuantity = itemQuantity - quantity;
                    itemEntity.setQuantity(newQuantity);
                    ItemEntity updatedItemEntity = inventoryRepository.save(itemEntity);

                    return new ResponseEntity<>(
                            InventoryResponseModel.builder()
                                    .success(true)
                                    .item(itemMapper.mapToItemModel(updatedItemEntity))
                                    .build(), HttpStatus.OK);
                }
                return new ResponseEntity<>(
                        InventoryResponseModel.builder()
                                .success(false)
                                .message(List.of("Not enough books are available in inventory."))
                                .build(), HttpStatus.BAD_REQUEST
                );
            }
            return new ResponseEntity<>(
                    InventoryResponseModel.builder()
                            .success(false)
                            .message(List.of("Quantity is invalid."))
                            .build(), HttpStatus.NOT_ACCEPTABLE
            );
        }

        return new ResponseEntity<>(
                InventoryResponseModel.builder()
                        .success(false)
                        .found(false)
                        .message(List.of("Book not found."))
                        .build(), HttpStatus.NOT_FOUND
        );
    }

}
