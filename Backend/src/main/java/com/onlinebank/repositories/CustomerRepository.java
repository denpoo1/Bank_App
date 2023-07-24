package com.onlinebank.repositories;

import com.onlinebank.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Denis Durbalov
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Integer> {
    Optional<CustomerModel> findByUsername(String username);

    boolean existsByUsername(String username);
}
