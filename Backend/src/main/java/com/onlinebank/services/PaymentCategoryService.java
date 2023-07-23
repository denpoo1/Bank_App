package com.onlinebank.services;

import com.onlinebank.models.PaymentCategory;
import com.onlinebank.repositories.PaymentCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentCategoryService {
    private final PaymentCategoryRepository paymentCategoryRepository;

    @Autowired
    public PaymentCategoryService(PaymentCategoryRepository paymentCategoryRepository) {
        this.paymentCategoryRepository = paymentCategoryRepository;
    }

    public List<PaymentCategory> getAllPaymentCategories() {
        return paymentCategoryRepository.findAll();
    }

    public PaymentCategory getPaymentCategoryById(Integer id) {
        return paymentCategoryRepository.findById(id).orElse(null);
    }

    public void savePaymentCategory(PaymentCategory paymentCategory) {
        paymentCategoryRepository.save(paymentCategory);
    }

    public void deletePaymentCategoryById(Integer id) {
        paymentCategoryRepository.deleteById(id);
    }

    @Transactional
    public void updatePaymentCategory(PaymentCategory updatedPaymentCategory) {
        if (paymentCategoryRepository.existsById(updatedPaymentCategory.getId())) {
            PaymentCategory existingPaymentCategory = paymentCategoryRepository.getOne(updatedPaymentCategory.getId());

            existingPaymentCategory.setName(updatedPaymentCategory.getName());
            existingPaymentCategory.setAmount(updatedPaymentCategory.getAmount());
            existingPaymentCategory.setMaxAmount(updatedPaymentCategory.getMaxAmount());

            paymentCategoryRepository.save(existingPaymentCategory);
        } else {
            throw new EntityNotFoundException("PaymentCategory with ID " + updatedPaymentCategory.getId() + " not found.");
        }
    }
}
