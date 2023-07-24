package com.onlinebank.services;

import com.onlinebank.models.CreditCardModel;
import com.onlinebank.repositories.CreditCardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Denis Durbalov
 */
@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public List<CreditCardModel> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public CreditCardModel getCreditCardById(Integer id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    public void saveCreditCard(CreditCardModel creditCardModel) {
        creditCardRepository.save(creditCardModel);
    }

    public void deleteCreditCardById(Integer id) {
        creditCardRepository.deleteById(id);
    }

    @Transactional
    public void updateCreditCard(CreditCardModel updatedCreditCardModel) {
        CreditCardModel existingCreditCardModel = creditCardRepository.findById(updatedCreditCardModel.getId())
                .orElseThrow(() -> new EntityNotFoundException("CreditCard with ID " + updatedCreditCardModel.getId() + " not found."));

        existingCreditCardModel.setCardNumber(updatedCreditCardModel.getCardNumber());
        existingCreditCardModel.setCvv(updatedCreditCardModel.getCvv());
        existingCreditCardModel.setBillingAddress(updatedCreditCardModel.getBillingAddress());
        existingCreditCardModel.setCreditLimit(updatedCreditCardModel.getCreditLimit());
        existingCreditCardModel.setBalance(updatedCreditCardModel.getBalance());
        existingCreditCardModel.setCreatedAt(updatedCreditCardModel.getCreatedAt());
        existingCreditCardModel.setExpirationDate(updatedCreditCardModel.getExpirationDate());

        // Важно обновить связь с аккаунтом, если она была изменена
        existingCreditCardModel.setAccountModel(updatedCreditCardModel.getAccountModel());

        creditCardRepository.save(existingCreditCardModel);
    }
}
