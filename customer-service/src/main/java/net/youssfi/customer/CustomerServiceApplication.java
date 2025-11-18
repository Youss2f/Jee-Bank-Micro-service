package net.youssfi.customer;

import net.youssfi.customer.entities.Customer;
import net.youssfi.customer.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository){
        return args -> {
            customerRepository.save(new Customer(null, "Youssef", "youssef@gmail.com"));
            customerRepository.save(new Customer(null, "Ahmed", "ahmed@gmail.com"));
            customerRepository.save(new Customer(null, "Ali", "ali@gmail.com"));
        };
    }
}
