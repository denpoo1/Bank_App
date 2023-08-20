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

    List<TransactionModel> findAllByToCardId(int to_card_id);

    List<TransactionModel> findAllByFromCardId(int from_card_Id);

    List<TransactionModel> findAllByToCardIdAndDateBetween(int to_card_id, Date startDate, Date endDate);

    List<TransactionModel> findAllByFromCardIdAndDateBetween(int from_card_Id, Date startDate, Date endDate);

}

//    List<TransactionModel> findAllByTo_card_id(int to_card_id);
//
//    List<TransactionModel> findAllByFrom_card_Id(int from_card_Id);
//
//    List<TransactionModel> findAllByTo_card_idAndDateBetween(int to_card_id, Date startDate, Date endDate);
//
//    List<TransactionModel> findAllByFrom_card_IdAndDateBetween(int from_card_Id, Date startDate, Date endDate);

