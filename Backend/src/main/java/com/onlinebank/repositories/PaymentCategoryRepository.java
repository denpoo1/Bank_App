package com.onlinebank.repositories;

import com.onlinebank.models.PaymentCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Denis Durbalov
 */
@Repository
public interface PaymentCategoryRepository extends JpaRepository<PaymentCategoryModel, Integer> {
}
