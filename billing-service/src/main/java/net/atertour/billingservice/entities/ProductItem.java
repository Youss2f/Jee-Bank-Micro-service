package net.atertour.billingservice.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductItem {
    private String productId;
    private int quantity;
    private double price;
    private double total;
    @jakarta.persistence.Transient
    private String productName;
}
