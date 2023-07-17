package com.onlinebank.services;

import com.onlinebank.models.CashBackCardNumber;
import com.onlinebank.repositories.CashBackCardNumberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CashBackCardNumberService {
    private final CashBackCardNumberRepository cardNumberRepository;

    @Autowired
    public CashBackCardNumberService(CashBackCardNumberRepository cardNumberRepository) {
        this.cardNumberRepository = cardNumberRepository;
    }

    public List<CashBackCardNumber> getAllCashBackCardNumbers() {
        return cardNumberRepository.findAll();
    }

    public CashBackCardNumber getCashBackCardNumberById(Integer id) {
        return cardNumberRepository.findById(id).orElse(null);
    }

    public void saveCashBackCardNumber(CashBackCardNumber cardNumber) {
        cardNumberRepository.save(cardNumber);
    }

    public void deleteCashBackCardNumberById(Integer id) {
        cardNumberRepository.deleteById(id);
    }

    @Transactional
    public void updateCashBackCardNumber(CashBackCardNumber updateCashBackCardNumber) {
        CashBackCardNumber existingCardNumber = cardNumberRepository.findById(updateCashBackCardNumber.getId())
                .orElseThrow(() -> new EntityNotFoundException("CashBackCardNumber with ID " + updateCashBackCardNumber.getId() + " not found."));

        existingCardNumber.setName(updateCashBackCardNumber.getName());
        existingCardNumber.setDescription(updateCashBackCardNumber.getDescription());
        existingCardNumber.setAccountNumber(updateCashBackCardNumber.getAccountNumber());
        existingCardNumber.setCashbackPercentage(updateCashBackCardNumber.getCashbackPercentage());

        cardNumberRepository.save(existingCardNumber);
    }
}
