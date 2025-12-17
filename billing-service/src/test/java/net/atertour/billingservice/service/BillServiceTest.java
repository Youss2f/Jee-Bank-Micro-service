package net.atertour.billingservice.service;

import net.atertour.billingservice.dto.ProductItemRequest;
import net.atertour.billingservice.entities.Bill;
import net.atertour.billingservice.exception.CustomerNotFoundException;
import net.atertour.billingservice.exception.ProductNotFoundException;
import net.atertour.billingservice.feign.CustomerServiceClient;
import net.atertour.billingservice.feign.InventoryServiceClient;
import net.atertour.billingservice.model.Customer;
import net.atertour.billingservice.model.Product;
import net.atertour.billingservice.repositories.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Mock
    private InventoryServiceClient inventoryServiceClient;

    @InjectMocks
    private BillService billService;

    private Customer testCustomer;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Test Customer");
        testCustomer.setEmail("test@example.com");

        testProduct = new Product();
        testProduct.setId("prod-1");
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0);
        testProduct.setQuantity(10);
    }

    @Test
    @DisplayName("Should create bill successfully with valid inputs")
    void createBill_WithValidInputs_ShouldReturnBill() {
        // Arrange
        Long customerId = 1L;
        List<ProductItemRequest> productItems = Arrays.asList(
                new ProductItemRequest("prod-1", 2));

        when(customerServiceClient.getCustomer(customerId)).thenReturn(testCustomer);
        when(inventoryServiceClient.getProduct("prod-1")).thenReturn(testProduct);
        when(billRepository.save(any(Bill.class))).thenAnswer(invocation -> {
            Bill bill = invocation.getArgument(0);
            bill.setId(1L);
            return bill;
        });

        // Act
        Bill result = billService.createBill(customerId, productItems);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCustomerId()).isEqualTo(customerId);
        assertThat(result.getProductItems()).hasSize(1);
        assertThat(result.getTotal()).isEqualTo(200.0); // 2 * 100.0
    }

    @Test
    @DisplayName("Should throw CustomerNotFoundException when customer not found")
    void createBill_WithInvalidCustomerId_ShouldThrowException() {
        // Arrange
        Long customerId = 999L;
        List<ProductItemRequest> productItems = Arrays.asList(
                new ProductItemRequest("prod-1", 2));

        when(customerServiceClient.getCustomer(customerId)).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> billService.createBill(customerId, productItems))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException when product not found")
    void createBill_WithInvalidProductId_ShouldThrowException() {
        // Arrange
        Long customerId = 1L;
        List<ProductItemRequest> productItems = Arrays.asList(
                new ProductItemRequest("invalid-product", 2));

        when(customerServiceClient.getCustomer(customerId)).thenReturn(testCustomer);
        when(inventoryServiceClient.getProduct("invalid-product")).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> billService.createBill(customerId, productItems))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("invalid-product");
    }

    @Test
    @DisplayName("Should calculate correct total for multiple products")
    void createBill_WithMultipleProducts_ShouldCalculateCorrectTotal() {
        // Arrange
        Long customerId = 1L;
        Product product2 = new Product();
        product2.setId("prod-2");
        product2.setName("Product 2");
        product2.setPrice(50.0);
        product2.setQuantity(5);

        List<ProductItemRequest> productItems = Arrays.asList(
                new ProductItemRequest("prod-1", 2), // 2 * 100 = 200
                new ProductItemRequest("prod-2", 3) // 3 * 50 = 150
        );

        when(customerServiceClient.getCustomer(customerId)).thenReturn(testCustomer);
        when(inventoryServiceClient.getProduct("prod-1")).thenReturn(testProduct);
        when(inventoryServiceClient.getProduct("prod-2")).thenReturn(product2);
        when(billRepository.save(any(Bill.class))).thenAnswer(invocation -> {
            Bill bill = invocation.getArgument(0);
            bill.setId(1L);
            return bill;
        });

        // Act
        Bill result = billService.createBill(customerId, productItems);

        // Assert
        assertThat(result.getTotal()).isEqualTo(350.0); // 200 + 150
        assertThat(result.getProductItems()).hasSize(2);
    }
}
