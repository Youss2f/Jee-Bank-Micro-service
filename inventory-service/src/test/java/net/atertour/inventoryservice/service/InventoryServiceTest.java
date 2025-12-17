package net.atertour.inventoryservice.service;

import net.atertour.inventoryservice.entities.Product;
import net.atertour.inventoryservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id("123-uuid")
                .name("Laptop")
                .price(1200.0)
                .quantity(10)
                .build();
    }

    @Test
    void shouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        List<Product> result = inventoryService.allProducts();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Laptop");
    }

    @Test
    void shouldReturnProductById() {
        when(productRepository.findById("123-uuid")).thenReturn(Optional.of(product));
        Product result = inventoryService.productById("123-uuid");
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("123-uuid");
    }

    @Test
    void shouldSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product result = inventoryService.saveProduct(product);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Laptop");
    }

    @Test
    void shouldDeleteProduct() {
        doNothing().when(productRepository).deleteById(anyString());
        inventoryService.deleteProduct("123-uuid");
        verify(productRepository, times(1)).deleteById("123-uuid");
    }
}
