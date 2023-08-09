package com.BinaryWizards.bookinventory.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEntity {

    @Id
    private Long id;
    private double price;
    private int quantity;

}
