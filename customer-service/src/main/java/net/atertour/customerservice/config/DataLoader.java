package net.atertour.customerservice.config;

import net.atertour.customerservice.entities.Customer;
import net.atertour.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            // Initialize with sample data
            customerRepository.save(Customer.builder()
                    .name("Mohamed")
                    .email("mohamed@gmail.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .name("Imane")
                    .email("imane@gmail.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .name("Yassine")
                    .email("yassine@gmail.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .name("Hassan")
                    .email("hassan@gmail.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .name("Alice")
                    .email("alice@tech.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .name("Bob")
                    .email("bob@builder.com")
                    .build());

            // Display all customers
            customerRepository.findAll().forEach(c -> {
                System.out.println("====================");
                System.out.println("ID: " + c.getId());
                System.out.println("Name: " + c.getName());
                System.out.println("Email: " + c.getEmail());
                System.out.println("====================");
            });
        };
    }
}
