package com.BinaryWizards.bookinventory.controllers;

import com.BinaryWizards.bookinventory.model.*;
import com.BinaryWizards.bookinventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // Create books
    @PostMapping("/create")
    public ResponseEntity<?> createItem(@Valid @RequestBody InventoryRequestModel inventoryRequestModel) {
        return inventoryService.createItem(inventoryRequestModel.getItem());
    }

    // Get all books
    @PostMapping
    public ResponseEntity<?> getAllItem(@RequestBody ItemByIDsRequestModel itemByIDsRequestModel) {
        return inventoryService.getAllBooksById(itemByIDsRequestModel.getIds());
    }

    // Get a particular book by book-id
    @GetMapping("/{bookId}")
    public ResponseEntity<?> getItem(@PathVariable Long bookId) {
        return inventoryService.getItem(bookId);
    }

    // Update a particular book by book-id
    @PatchMapping("/update/{bookId}")
    public ResponseEntity<?> updateItem(@PathVariable Long bookId, @RequestBody InventoryRequestModel inventoryRequestModel) {
        return inventoryService.updateBook(bookId, inventoryRequestModel.getItem());
    }

    // Delete a particular book by book-id
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long bookId) {
        return inventoryService.deleteItem(bookId);
    }

    @PatchMapping("/buy")
    public ResponseEntity<?> buyItem(@RequestBody InventoryRequestModel inventoryRequestModel) {
        return inventoryService.buyItem(inventoryRequestModel);
    }

}
