package net.atertour.inventoryservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.atertour.inventoryservice.entities.Product;
import net.atertour.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void shouldGetAllProducts() throws Exception {
        when(inventoryService.allProducts()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

    @Test
    void shouldGetProductById() throws Exception {
        when(inventoryService.productById(anyString())).thenReturn(product);

        mockMvc.perform(get("/api/products/{id}", "123-uuid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123-uuid"))
                .andExpect(jsonPath("$.price").value(1200.0));
    }

    @Test
    void shouldSaveProduct() throws Exception {
        when(inventoryService.saveProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }
}
