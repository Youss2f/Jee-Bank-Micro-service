package net.atertour.billingservice.feign;

import net.atertour.billingservice.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Fallback implementation for CustomerServiceClient
 * Used when the customer-service is unavailable (Circuit Breaker)
 */
@Component
public class CustomerServiceFallback implements CustomerServiceClient {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceFallback.class);

    @Override
    public Customer getCustomer(Long id) {
        log.warn("Customer service is unavailable. Fallback triggered for customer ID: {}", id);
        // Return null to indicate customer not found - will be handled by exception
        return null;
    }
}
