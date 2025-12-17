package net.atertour.customerservice;

import net.atertour.customerservice.config.CustomerConfigParams;
import net.atertour.customerservice.entities.Customer;
import net.atertour.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(CustomerConfigParams.class)
public class CustomerServiceApplication {

        public static void main(String[] args) {
                SpringApplication.run(CustomerServiceApplication.class, args);
        }

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
