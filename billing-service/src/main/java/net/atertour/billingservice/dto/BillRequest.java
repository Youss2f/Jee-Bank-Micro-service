package net.atertour.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class BillRequest {
    private Long customerId;
    private List<ProductItemRequest> productItems;
}
