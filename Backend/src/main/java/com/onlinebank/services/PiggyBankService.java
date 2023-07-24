package com.onlinebank.services;

import com.onlinebank.models.PiggyBankModel;
import com.onlinebank.repositories.PiggyBankRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Denis Durbalov
 */
@Service
public class PiggyBankService {
    private final PiggyBankRepository piggyBankRepository;

    @Autowired
    public PiggyBankService(PiggyBankRepository piggyBankRepository) {
        this.piggyBankRepository = piggyBankRepository;
    }

    public List<PiggyBankModel> getAllPiggyBanks() {
        return piggyBankRepository.findAll();
    }

    public PiggyBankModel getPiggyBankById(Integer id) {
        return piggyBankRepository.findById(id).orElse(null);
    }

    public void savePiggyBank(PiggyBankModel piggyBankModel) {
        piggyBankRepository.save(piggyBankModel);
    }

    public void deletePiggyBankById(Integer id) {
        piggyBankRepository.deleteById(id);
    }

    @Transactional
    public void updatePiggyBank(PiggyBankModel updatedPiggyBankModel) {
        if (piggyBankRepository.existsById(updatedPiggyBankModel.getId())) {
            PiggyBankModel existingPiggyBankModel = piggyBankRepository.getOne(updatedPiggyBankModel.getId());

            existingPiggyBankModel.setCreatedAt(updatedPiggyBankModel.getCreatedAt());
            existingPiggyBankModel.setAmount(updatedPiggyBankModel.getAmount());

            piggyBankRepository.save(existingPiggyBankModel);
        } else {
            throw new EntityNotFoundException("PiggyBank with ID " + updatedPiggyBankModel.getId() + " not found.");
        }
    }
}
