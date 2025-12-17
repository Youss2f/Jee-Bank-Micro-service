package net.atertour.customerservice.repository;

import net.atertour.customerservice.entities.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should save and find customer by ID")
    void saveAndFindById_ShouldWork() {
        // Arrange - clear any seeded data first
        customerRepository.deleteAll();

        Customer customer = Customer.builder()
                .name("Test User")
                .email("test@example.com")
                .build();

        // Act
        Customer saved = customerRepository.save(customer);
        entityManager.flush();
        entityManager.clear();

        Optional<Customer> found = customerRepository.findById(saved.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test User");
        assertThat(found.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("Should return empty when customer not found")
    void findById_WithNonExistentId_ShouldReturnEmpty() {
        // Act
        Optional<Customer> found = customerRepository.findById(99999L);

        // Assert
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should save multiple customers")
    void save_MultipleCustomers_ShouldPersist() {
        // Arrange
        long initialCount = customerRepository.count();

        Customer customer1 = Customer.builder()
                .name("User 1")
                .email("user1@example.com")
                .build();
        Customer customer2 = Customer.builder()
                .name("User 2")
                .email("user2@example.com")
                .build();

        // Act
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        entityManager.flush();

        // Assert - should have 2 more than initial
        assertThat(customerRepository.count()).isEqualTo(initialCount + 2);
    }

    @Test
    @DisplayName("Should delete customer")
    void delete_ShouldRemoveCustomer() {
        // Arrange
        Customer customer = Customer.builder()
                .name("To Delete")
                .email("delete@example.com")
                .build();
        Customer saved = customerRepository.save(customer);
        entityManager.flush();

        // Act
        customerRepository.deleteById(saved.getId());
        entityManager.flush();

        // Assert
        Optional<Customer> found = customerRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}
