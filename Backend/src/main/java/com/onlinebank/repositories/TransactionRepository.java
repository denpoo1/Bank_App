package com.onlinebank.repositories;

import com.onlinebank.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Denis Durbalov
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {

    List<TransactionModel> findAllByToAccountId(int toAccountId);

    List<TransactionModel> findAllByFromAccountId(int fromAccountId);

    List<TransactionModel> findAllByToAccountIdAndDateBetween(int toAccountId, Date startDate, Date endDate);

    List<TransactionModel> findAllByFromAccountIdAndDateBetween(int fromAccountId, Date startDate, Date endDate);

}
