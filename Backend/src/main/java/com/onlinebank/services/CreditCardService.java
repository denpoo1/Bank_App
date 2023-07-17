package com.onlinebank.services;

import com.onlinebank.models.CreditCard;
import com.onlinebank.repositories.CreditCardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public CreditCard getCreditCardById(Integer id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    public void saveCreditCard(CreditCard creditCard) {
        creditCardRepository.save(creditCard);
    }

    public void deleteCreditCardById(Integer id) {
        creditCardRepository.deleteById(id);
    }

    @Transactional
    public void updateCreditCard(CreditCard updatedCreditCard) {
        CreditCard existingCreditCard = creditCardRepository.findById(updatedCreditCard.getId())
                .orElseThrow(() -> new EntityNotFoundException("CreditCard with ID " + updatedCreditCard.getId() + " not found."));

        existingCreditCard.setCardNumber(updatedCreditCard.getCardNumber());
        existingCreditCard.setCvv(updatedCreditCard.getCvv());
        existingCreditCard.setBillingAddress(updatedCreditCard.getBillingAddress());
        existingCreditCard.setCreditLimit(updatedCreditCard.getCreditLimit());
        existingCreditCard.setBalance(updatedCreditCard.getBalance());
        existingCreditCard.setCreatedAt(updatedCreditCard.getCreatedAt());
        existingCreditCard.setExpirationDate(updatedCreditCard.getExpirationDate());

        // Важно обновить связь с аккаунтом, если она была изменена
        existingCreditCard.setAccount(updatedCreditCard.getAccount());

        creditCardRepository.save(existingCreditCard);
    }
}
