package net.atertour.billingservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    @ElementCollection
    private List<ProductItem> productItems;
    private double total;
    private LocalDate date;

    @Transient
    private net.atertour.billingservice.model.Customer customer;
}
