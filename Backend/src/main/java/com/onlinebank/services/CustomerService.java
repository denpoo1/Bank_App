package com.onlinebank.services;

import com.onlinebank.models.Customer;
import com.onlinebank.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void saveCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
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

    public Customer getCustomerByUsername (String username) {
        return customerRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found"));
    }

    public boolean existCustomer (String username) {
        return customerRepository.existsByUsername(username);
    }
}
