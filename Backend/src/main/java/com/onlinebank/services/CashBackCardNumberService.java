package com.onlinebank.services;

import com.onlinebank.models.CashBackCardNumberModel;
import com.onlinebank.repositories.CashBackCardNumberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Denis Durbalov
 */
@Service
public class CashBackCardNumberService {
    private final CashBackCardNumberRepository cardNumberRepository;

    @Autowired
    public CashBackCardNumberService(CashBackCardNumberRepository cardNumberRepository) {
        this.cardNumberRepository = cardNumberRepository;
    }

    public List<CashBackCardNumberModel> getAllCashBackCardNumbers() {
        return cardNumberRepository.findAll();
    }

    public CashBackCardNumberModel getCashBackCardNumberById(Integer id) {
        return cardNumberRepository.findById(id).orElse(null);
    }

    public void saveCashBackCardNumber(CashBackCardNumberModel cardNumber) {
        cardNumberRepository.save(cardNumber);
    }

    public void deleteCashBackCardNumberById(Integer id) {
        cardNumberRepository.deleteById(id);
    }

    @Transactional
    public void updateCashBackCardNumber(CashBackCardNumberModel updateCashBackCardNumberModel) {
        CashBackCardNumberModel existingCardNumber = cardNumberRepository.findById(updateCashBackCardNumberModel.getId())
                .orElseThrow(() -> new EntityNotFoundException("CashBackCardNumber with ID " + updateCashBackCardNumberModel.getId() + " not found."));

        existingCardNumber.setName(updateCashBackCardNumberModel.getName());
        existingCardNumber.setDescription(updateCashBackCardNumberModel.getDescription());
        existingCardNumber.setAccountNumber(updateCashBackCardNumberModel.getAccountNumber());
        existingCardNumber.setCashbackPercentage(updateCashBackCardNumberModel.getCashbackPercentage());

        cardNumberRepository.save(existingCardNumber);
    }
}
