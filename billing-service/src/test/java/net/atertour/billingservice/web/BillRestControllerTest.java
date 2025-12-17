package net.atertour.billingservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.atertour.billingservice.dto.BillRequest;
import net.atertour.billingservice.dto.ProductItemRequest;
import net.atertour.billingservice.entities.Bill;
import net.atertour.billingservice.exception.CustomerNotFoundException;
import net.atertour.billingservice.exception.GlobalExceptionHandler;
import net.atertour.billingservice.service.BillService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BillRestController.class)
@Import(GlobalExceptionHandler.class)
class BillRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BillService billService;

    @Test
    @DisplayName("POST /bills - Should create bill with valid request")
    void createBill_WithValidRequest_ShouldReturn200() throws Exception {
        // Arrange
        BillRequest request = new BillRequest();
        request.setCustomerId(1L);
        request.setProductItems(Arrays.asList(
                new ProductItemRequest("prod-1", 2)));

        Bill bill = Bill.builder()
                .id(1L)
                .customerId(1L)
                .total(200.0)
                .date(LocalDate.now())
                .build();

        when(billService.createBill(anyLong(), any())).thenReturn(bill);

        // Act & Assert
        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.total").value(200.0));
    }

    @Test
    @DisplayName("POST /bills - Should return 400 when customerId is null")
    void createBill_WithNullCustomerId_ShouldReturn400() throws Exception {
        // Arrange
        BillRequest request = new BillRequest();
        request.setCustomerId(null);
        request.setProductItems(Arrays.asList(
                new ProductItemRequest("prod-1", 2)));

        // Act & Assert
        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }

    @Test
    @DisplayName("POST /bills - Should return 400 when productItems is empty")
    void createBill_WithEmptyProductItems_ShouldReturn400() throws Exception {
        // Arrange
        BillRequest request = new BillRequest();
        request.setCustomerId(1L);
        request.setProductItems(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("POST /bills - Should return 404 when customer not found")
    void createBill_WithNonExistentCustomer_ShouldReturn404() throws Exception {
        // Arrange
        BillRequest request = new BillRequest();
        request.setCustomerId(999L);
        request.setProductItems(Arrays.asList(
                new ProductItemRequest("prod-1", 2)));

        when(billService.createBill(anyLong(), any()))
                .thenThrow(new CustomerNotFoundException(999L));

        // Act & Assert
        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Customer Not Found"));
    }
}
