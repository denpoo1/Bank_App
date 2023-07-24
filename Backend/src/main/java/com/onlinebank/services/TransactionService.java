package com.onlinebank.services;

import com.onlinebank.models.TransactionModel;
import com.onlinebank.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Denis Durbalov
 */
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionModel> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public TransactionModel getTransactionById(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public void saveTransaction(TransactionModel transactionModel) {
        transactionRepository.save(transactionModel);
    }

    public void deleteTransactionById(Integer id) {
        transactionRepository.deleteById(id);
    }

    @Transactional
    public void updateTransaction(TransactionModel updatedTransactionModel) {
        if (transactionRepository.existsById(updatedTransactionModel.getId())) {
            // Получаем текущую версию сущности из базы данных
            TransactionModel existingTransactionModel = transactionRepository.getOne(updatedTransactionModel.getId());

            existingTransactionModel.setDate(updatedTransactionModel.getDate());
            existingTransactionModel.setAmount(updatedTransactionModel.getAmount());
            existingTransactionModel.setLeftoverAmount(updatedTransactionModel.getLeftoverAmount());
            existingTransactionModel.setToAccountId(updatedTransactionModel.getToAccountId());
            existingTransactionModel.setFromAccountId(updatedTransactionModel.getFromAccountId());

            transactionRepository.save(existingTransactionModel);
        } else {
            throw new EntityNotFoundException("Transaction with ID " + updatedTransactionModel.getId() + " not found.");
        }
    }

    public List<TransactionModel> getAccountTransactions(int accountId) {
        List<TransactionModel> transactionModels = new ArrayList<>();
        transactionModels.addAll(transactionRepository.findAllByToAccountId(accountId));
        transactionModels.addAll(transactionRepository.findAllByFromAccountId(accountId));
        return transactionModels;
    }

    public List<TransactionModel> getAccountTransactionsByDays(int toAccountId, int fromAccountId, Date startDay, Date endDay) {
        List<TransactionModel> transactionModels = new ArrayList<>();
        transactionModels.addAll(transactionRepository.findAllByToAccountIdAndDateBetween(toAccountId, startDay, endDay));
        transactionModels.addAll(transactionRepository.findAllByFromAccountIdAndDateBetween(fromAccountId, startDay, endDay));
        return transactionModels;
    }
}
