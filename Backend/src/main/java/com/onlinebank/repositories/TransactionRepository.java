package com.onlinebank.repositories;

import com.onlinebank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByToAccountId(int toAccountId);

    List<Transaction> findAllByFromAccountId(int fromAccountId);
    List<Transaction> findAllByToAccountIdAndDateBetween(int toAccountId, Date startDate, Date endDate);
    List<Transaction> findAllByFromAccountIdAndDateBetween(int fromAccountId, Date startDate, Date endDate);

}
