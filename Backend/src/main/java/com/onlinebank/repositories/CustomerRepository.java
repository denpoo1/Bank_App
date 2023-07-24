package com.onlinebank.repositories;

import com.onlinebank.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername (String username);

    boolean existsByUsername (String username);
}
