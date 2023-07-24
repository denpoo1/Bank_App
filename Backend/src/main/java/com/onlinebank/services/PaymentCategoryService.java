package com.onlinebank.services;

import com.onlinebank.models.PaymentCategoryModel;
import com.onlinebank.repositories.PaymentCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Denis Durbalov
 */
@Service
public class PaymentCategoryService {
    private final PaymentCategoryRepository paymentCategoryRepository;

    @Autowired
    public PaymentCategoryService(PaymentCategoryRepository paymentCategoryRepository) {
        this.paymentCategoryRepository = paymentCategoryRepository;
    }

    public List<PaymentCategoryModel> getAllPaymentCategories() {
        return paymentCategoryRepository.findAll();
    }

    public PaymentCategoryModel getPaymentCategoryById(Integer id) {
        return paymentCategoryRepository.findById(id).orElse(null);
    }

    public void savePaymentCategory(PaymentCategoryModel paymentCategoryModel) {
        paymentCategoryRepository.save(paymentCategoryModel);
    }

    public void deletePaymentCategoryById(Integer id) {
        paymentCategoryRepository.deleteById(id);
    }

    @Transactional
    public void updatePaymentCategory(PaymentCategoryModel updatedPaymentCategoryModel) {
        if (paymentCategoryRepository.existsById(updatedPaymentCategoryModel.getId())) {
            PaymentCategoryModel existingPaymentCategoryModel = paymentCategoryRepository.getOne(updatedPaymentCategoryModel.getId());

            existingPaymentCategoryModel.setName(updatedPaymentCategoryModel.getName());
            existingPaymentCategoryModel.setAmount(updatedPaymentCategoryModel.getAmount());
            existingPaymentCategoryModel.setMaxAmount(updatedPaymentCategoryModel.getMaxAmount());

            paymentCategoryRepository.save(existingPaymentCategoryModel);
        } else {
            throw new EntityNotFoundException("PaymentCategory with ID " + updatedPaymentCategoryModel.getId() + " not found.");
        }
    }
}
