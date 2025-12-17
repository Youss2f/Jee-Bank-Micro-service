package net.atertour.billingservice.feign;

import net.atertour.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INVENTORY-SERVICE", fallback = InventoryServiceFallback.class)
public interface InventoryServiceClient {
    @GetMapping("/products/{id}")
    Product getProduct(@PathVariable("id") String id);
}
