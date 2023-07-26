package com.onlinebank.repositories;

import com.onlinebank.models.CreditCardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Denis Durbalov
 */
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardModel, Integer> {
}
