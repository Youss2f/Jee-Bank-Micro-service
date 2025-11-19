package net.atertour.billingservice.service;

import net.atertour.billingservice.dto.ProductItemRequest;
import net.atertour.billingservice.entities.Bill;
import net.atertour.billingservice.entities.ProductItem;
import net.atertour.billingservice.feign.CustomerServiceClient;
import net.atertour.billingservice.feign.InventoryServiceClient;
import net.atertour.billingservice.model.Customer;
import net.atertour.billingservice.model.Product;
import net.atertour.billingservice.repositories.BillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {
    private final BillRepository billRepository;
    private final CustomerServiceClient customerServiceClient;
    private final InventoryServiceClient inventoryServiceClient;

    public BillService(BillRepository billRepository, CustomerServiceClient customerServiceClient, InventoryServiceClient inventoryServiceClient) {
        this.billRepository = billRepository;
        this.customerServiceClient = customerServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public Bill createBill(Long customerId, List<ProductItemRequest> productItemRequests) {
        Customer customer = customerServiceClient.getCustomer(customerId);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        List<ProductItem> productItems = productItemRequests.stream().map(req -> {
            Product product = inventoryServiceClient.getProduct(req.getProductId());
            if (product == null) {
                throw new RuntimeException("Product not found: " + req.getProductId());
            }
            double total = product.getPrice() * req.getQuantity();
            return new ProductItem(req.getProductId(), req.getQuantity(), product.getPrice(), total);
        }).collect(Collectors.toList());

        double total = productItems.stream().mapToDouble(ProductItem::getTotal).sum();

        Bill bill = Bill.builder()
                .customerId(customerId)
                .productItems(productItems)
                .total(total)
                .date(LocalDate.now())
                .build();

        return billRepository.save(bill);
    }
}
