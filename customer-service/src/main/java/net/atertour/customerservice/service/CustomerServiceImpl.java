package net.atertour.customerservice.service;

import net.atertour.customerservice.entities.Customer;
import net.atertour.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> allCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer customerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = customerById(id);
        if (customer.getName() != null)
            existing.setName(customer.getName());
        if (customer.getEmail() != null)
            existing.setEmail(customer.getEmail());
        return customerRepository.save(existing);
    }
}
