package net.atertour.inventoryservice.service;

import net.atertour.inventoryservice.entities.Product;
import net.atertour.inventoryservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
    private final ProductRepository productRepository;

    public InventoryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product productById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product saveProduct(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID().toString());
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(String id, Product product) {
        Product existing = productById(id);
        if (product.getName() != null)
            existing.setName(product.getName());
        if (product.getPrice() != 0)
            existing.setPrice(product.getPrice());
        if (product.getQuantity() != 0)
            existing.setQuantity(product.getQuantity());
        return productRepository.save(existing);
    }
}
