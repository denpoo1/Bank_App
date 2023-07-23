package com.onlinebank.services;

import com.onlinebank.models.Customer;
import com.onlinebank.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomerById(Integer id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public void updateCustomer(Customer updatedCustomer) {
        if (customerRepository.existsById(updatedCustomer.getId())) {
            Customer existingCustomer = customerRepository.getOne(updatedCustomer.getId());

            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPhone(updatedCustomer.getPhone());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            existingCustomer.setUsername(updatedCustomer.getUsername());

            customerRepository.save(existingCustomer);
        } else {
            throw new EntityNotFoundException("Customer with ID " + updatedCustomer.getId() + " not found.");
        }
    }
}
