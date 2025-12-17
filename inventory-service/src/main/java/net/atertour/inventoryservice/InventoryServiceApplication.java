package net.atertour.inventoryservice;

import net.atertour.inventoryservice.entities.Product;
import net.atertour.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class InventoryServiceApplication {

        public static void main(String[] args) {
                SpringApplication.run(InventoryServiceApplication.class, args);
        }

}
