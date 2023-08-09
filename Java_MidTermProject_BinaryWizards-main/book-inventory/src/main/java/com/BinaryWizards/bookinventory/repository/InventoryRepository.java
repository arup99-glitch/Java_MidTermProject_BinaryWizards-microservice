package com.BinaryWizards.bookinventory.repository;

import com.BinaryWizards.bookinventory.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<ItemEntity,Long> {

}
