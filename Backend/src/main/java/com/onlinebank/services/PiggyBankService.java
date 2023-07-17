package com.onlinebank.services;

import com.onlinebank.models.PiggyBank;
import com.onlinebank.repositories.PiggyBankRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiggyBankService {
    private final PiggyBankRepository piggyBankRepository;

    @Autowired
    public PiggyBankService(PiggyBankRepository piggyBankRepository) {
        this.piggyBankRepository = piggyBankRepository;
    }

    public List<PiggyBank> getAllPiggyBanks() {
        return piggyBankRepository.findAll();
    }

    public PiggyBank getPiggyBankById(Integer id) {
        return piggyBankRepository.findById(id).orElse(null);
    }

    public void savePiggyBank(PiggyBank piggyBank) {
        piggyBankRepository.save(piggyBank);
    }

    public void deletePiggyBankById(Integer id) {
        piggyBankRepository.deleteById(id);
    }

    @Transactional
    public void updatePiggyBank(PiggyBank updatedPiggyBank) {
        if (piggyBankRepository.existsById(updatedPiggyBank.getId())) {
            PiggyBank existingPiggyBank = piggyBankRepository.getOne(updatedPiggyBank.getId());

            existingPiggyBank.setCreatedAt(updatedPiggyBank.getCreatedAt());
            existingPiggyBank.setAmount(updatedPiggyBank.getAmount());

            piggyBankRepository.save(existingPiggyBank);
        } else {
            throw new EntityNotFoundException("PiggyBank with ID " + updatedPiggyBank.getId() + " not found.");
        }
    }
}
