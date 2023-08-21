package com.onlinebank.repositories;

import com.onlinebank.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Denis Durbalov
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {

    @Query("SELECT t FROM TransactionModel t WHERE t.toCardId = :toCardId")
    List<TransactionModel> findAllByToCardId(@Param("toCardId") int toCardId);

    @Query("SELECT t FROM TransactionModel t WHERE t.fromCardId = :fromCardId")
    List<TransactionModel> findAllByFromCardId(@Param("fromCardId") int fromCardId);

    @Query("SELECT t FROM TransactionModel t WHERE t.toCardId = :toCardId AND t.date BETWEEN :startDate AND :endDate")
    List<TransactionModel> findAllByToCardIdAndDateBetween(@Param("toCardId") int toCardId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT t FROM TransactionModel t WHERE t.fromCardId = :fromCardId AND t.date BETWEEN :startDate AND :endDate")
    List<TransactionModel> findAllByFromCardIdAndDateBetween(@Param("fromCardId") int fromCardId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

