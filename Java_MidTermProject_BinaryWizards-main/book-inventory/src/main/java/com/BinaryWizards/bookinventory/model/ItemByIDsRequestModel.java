package com.BinaryWizards.bookinventory.model;

import lombok.*;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemByIDsRequestModel {

    private List<Long> ids;

}
