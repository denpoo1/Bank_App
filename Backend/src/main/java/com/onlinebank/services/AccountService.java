package com.onlinebank.services;

import com.onlinebank.models.Account;
import com.onlinebank.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public void deleteAccountById(Integer id) {
        accountRepository.deleteById(id);
    }

    @Transactional
    public void updateAccount(Account updatedAccount) {
        if (accountRepository.existsById(updatedAccount.getId())) {
            // Получаем текущую версию сущности из базы данных
            Account existingAccount = accountRepository.getOne(updatedAccount.getId());

            existingAccount.setDate(updatedAccount.getDate());
            existingAccount.setCustomer(updatedAccount.getCustomer());
            existingAccount.setTransactionRoundingPercentage(updatedAccount.getTransactionRoundingPercentage());

            accountRepository.save(existingAccount);
        } else {
            throw new EntityNotFoundException("Account with ID " + updatedAccount.getId() + " not found.");
        }
    }
}
