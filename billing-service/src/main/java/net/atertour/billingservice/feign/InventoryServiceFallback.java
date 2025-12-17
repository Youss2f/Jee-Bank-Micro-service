package net.atertour.billingservice.feign;

import net.atertour.billingservice.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Fallback implementation for InventoryServiceClient
 * Used when the inventory-service is unavailable (Circuit Breaker)
 */
@Component
public class InventoryServiceFallback implements InventoryServiceClient {

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceFallback.class);

    @Override
    public Product getProduct(String id) {
        log.warn("Inventory service is unavailable. Fallback triggered for product ID: {}", id);
        // Return null to indicate product not found - will be handled by exception
        return null;
    }
}
