package com.onlinebank.services;

import com.onlinebank.models.CustomerModel;
import com.onlinebank.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Denis Durbalov
 */
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerModel getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void saveCustomer(CustomerModel customerModel) {
        customerModel.setPassword(passwordEncoder.encode(customerModel.getPassword()));
        customerRepository.save(customerModel);
    }

    public void deleteCustomerById(Integer id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public void updateCustomer(CustomerModel updatedCustomerModel) {
        if (customerRepository.existsById(updatedCustomerModel.getId())) {
            CustomerModel existingCustomerModel = customerRepository.getOne(updatedCustomerModel.getId());

            existingCustomerModel.setFirstName(updatedCustomerModel.getFirstName());
            existingCustomerModel.setLastName(updatedCustomerModel.getLastName());
            existingCustomerModel.setEmail(updatedCustomerModel.getEmail());
            existingCustomerModel.setPhone(updatedCustomerModel.getPhone());
            existingCustomerModel.setAddress(updatedCustomerModel.getAddress());
            existingCustomerModel.setPassword(updatedCustomerModel.getPassword());
            existingCustomerModel.setUsername(updatedCustomerModel.getUsername());

            customerRepository.save(existingCustomerModel);
        } else {
            throw new EntityNotFoundException("Customer with ID " + updatedCustomerModel.getId() + " not found.");
        }
    }

    public CustomerModel getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found"));
    }

    public boolean existCustomer(String username) {
        return customerRepository.existsByUsername(username);
    }
}
