package net.atertour.billingservice.exception;

/**
 * Exception thrown when a product is not found
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super("Product not found with ID: " + productId);
    }
}
