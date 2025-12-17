package net.atertour.inventoryservice.web;

import net.atertour.inventoryservice.entities.Product;
import net.atertour.inventoryservice.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    private final InventoryService inventoryService;

    public ProductRestController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return inventoryService.allProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return inventoryService.productById(id);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return inventoryService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        return inventoryService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        inventoryService.deleteProduct(id);
    }
}
