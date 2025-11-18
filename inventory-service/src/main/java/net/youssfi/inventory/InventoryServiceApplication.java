package net.youssfi.inventory;

import net.youssfi.inventory.entities.Product;
import net.youssfi.inventory.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository){
        return args -> {
            productRepository.save(new Product(null, "Computer", 1200, 10));
            productRepository.save(new Product(null, "Printer", 300, 5));
            productRepository.save(new Product(null, "Smartphone", 800, 20));
        };
    }
}
