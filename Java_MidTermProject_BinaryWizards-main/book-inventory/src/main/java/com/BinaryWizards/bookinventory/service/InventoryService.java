package com.BinaryWizards.bookinventory.service;

import com.BinaryWizards.bookinventory.model.InventoryRequestModel;
import com.BinaryWizards.bookinventory.model.ItemModel;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface InventoryService {

    /**
     * Get all Books
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> getAllItems();

    /**
     * Get Book by ID
     * @param bookId accepts Long ID
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> getItem(Long bookId);

    /**
     * Update Book
     * @param bookId accepts Long ID
     * @param itemModel accepts ItemModel
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> updateBook(Long bookId, ItemModel itemModel);

    /**
     * Delete Book
     * @param bookId accepts Long ID
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> deleteItem(Long bookId);

    /**
     * Get all Books by ID
     * @param ids accepts List<Long>
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> getAllBooksById(List<Long> ids);

    /**
     * Create Book
     * @param itemModel accepts ItemModel
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> createItem(ItemModel itemModel);

    /**
     * Buy Book
     * @param inventoryRequestModel accepts InventoryRequestModel
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> buyItem(InventoryRequestModel inventoryRequestModel);

}
