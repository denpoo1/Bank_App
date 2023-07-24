package com.onlinebank.repositories;

import com.onlinebank.models.PiggyBankModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Denis Durbalov
 */
@Repository
public interface PiggyBankRepository extends JpaRepository<PiggyBankModel, Integer> {
}
