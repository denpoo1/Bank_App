package com.onlinebank.repositories;

import com.onlinebank.models.CashBackCardNumberModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Denis Durbalov
 */
@Repository
public interface CashBackCardNumberRepository extends JpaRepository<CashBackCardNumberModel, Integer> {
}
