package com.onlinebank.repositories;

import com.onlinebank.models.CashBackCardNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashBackCardNumberRepository extends JpaRepository<CashBackCardNumber, Integer> {
}
