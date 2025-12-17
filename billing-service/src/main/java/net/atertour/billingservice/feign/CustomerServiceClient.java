package net.atertour.billingservice.feign;

import net.atertour.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE", fallback = CustomerServiceFallback.class)
public interface CustomerServiceClient {
    @GetMapping("/customers/{id}")
    Customer getCustomer(@PathVariable("id") Long id);
}
