package net.atertour.customerservice.service;

import net.atertour.customerservice.entities.Customer;
import java.util.List;

public interface CustomerService {
    List<Customer> allCustomers();

    Customer customerById(Long id);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Long id);

    Customer updateCustomer(Long id, Customer customer);
}
