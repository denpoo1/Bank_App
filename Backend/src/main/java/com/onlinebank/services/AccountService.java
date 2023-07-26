package com.onlinebank.services;

import com.onlinebank.models.AccountModel;
import com.onlinebank.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Denis Durbalov
 */
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountModel> getAllAccounts() {
        return accountRepository.findAll();
    }

    public AccountModel getAccountById(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void saveAccount(AccountModel accountModel) {
        accountRepository.save(accountModel);
    }

    public void deleteAccountById(Integer id) {
        accountRepository.deleteById(id);
    }

    @Transactional
    public void updateAccount(AccountModel updatedAccountModel) {
        if (accountRepository.existsById(updatedAccountModel.getId())) {
            // Получаем текущую версию сущности из базы данных
            AccountModel existingAccountModel = accountRepository.getOne(updatedAccountModel.getId());

            existingAccountModel.setDate(updatedAccountModel.getDate());
            existingAccountModel.setCustomerModel(updatedAccountModel.getCustomerModel());
            existingAccountModel.setTransactionRoundingPercentage(updatedAccountModel.getTransactionRoundingPercentage());

            accountRepository.save(existingAccountModel);
        } else {
            throw new EntityNotFoundException("Account with ID " + updatedAccountModel.getId() + " not found.");
        }
    }
}
