package com.onlinebank.repositories;

import com.onlinebank.models.PiggyBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiggyBankRepository extends JpaRepository<PiggyBank, Integer> {
}
