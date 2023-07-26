package com.onlinebank.services;

import com.onlinebank.models.CustomerModel;
import com.onlinebank.repositories.CustomerRepository;
import com.onlinebank.security.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Denis Durbalov
 */
@Service
public class CustomerDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerDetailService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CustomerModel> customer = customerRepository.findByUsername(username);
        if (customer.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomerDetails(customer.get());
    }
}
