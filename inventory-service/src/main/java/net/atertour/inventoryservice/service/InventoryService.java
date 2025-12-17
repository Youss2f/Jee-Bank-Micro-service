package net.atertour.inventoryservice.service;

import net.atertour.inventoryservice.entities.Product;
import java.util.List;

public interface InventoryService {
    List<Product> allProducts();

    Product productById(String id);

    Product saveProduct(Product product);

    void deleteProduct(String id);

    Product updateProduct(String id, Product product);
}
