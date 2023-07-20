package com.onlinebank.services;

import com.onlinebank.models.Transaction;
import com.onlinebank.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void deleteTransactionById(Integer id) {
        transactionRepository.deleteById(id);
    }

    @Transactional
    public void updateTransaction(Transaction updatedTransaction) {
        if (transactionRepository.existsById(updatedTransaction.getId())) {
            // Получаем текущую версию сущности из базы данных
            Transaction existingTransaction = transactionRepository.getOne(updatedTransaction.getId());

            existingTransaction.setDate(updatedTransaction.getDate());
            existingTransaction.setAmount(updatedTransaction.getAmount());
            existingTransaction.setLeftoverAmount(updatedTransaction.getLeftoverAmount());
            existingTransaction.setToAccountId(updatedTransaction.getToAccountId());
            existingTransaction.setFromAccountId(updatedTransaction.getFromAccountId());

            transactionRepository.save(existingTransaction);
        } else {
            throw new EntityNotFoundException("Transaction with ID " + updatedTransaction.getId() + " not found.");
        }
    }

    public List<Transaction> getAccountTransactions(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionRepository.findAllByToAccountId(accountId));
        transactions.addAll(transactionRepository.findAllByFromAccountId(accountId));
        return transactions;
    }
}
