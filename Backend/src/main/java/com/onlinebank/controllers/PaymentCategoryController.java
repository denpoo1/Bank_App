package com.onlinebank.controllers;

import com.onlinebank.dto.PaymentCategoryRequest;
import com.onlinebank.dto.PaymentCategoryResponse;
import com.onlinebank.models.PaymentCategory;
import com.onlinebank.services.PaymentCategoryService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/payment-categories")
public class PaymentCategoryController {

    private final PaymentCategoryService paymentCategoryService;

    @Autowired
    public PaymentCategoryController(PaymentCategoryService paymentCategoryService) {
        this.paymentCategoryService = paymentCategoryService;
    }

    @GetMapping
    public List<PaymentCategoryResponse> getPaymentCategories() {
        List<PaymentCategory> paymentCategories = paymentCategoryService.getAllPaymentCategories();
        List<PaymentCategoryResponse> paymentCategoryResponses = new ArrayList<>();
        for (PaymentCategory paymentCategory : paymentCategories) {
            paymentCategoryResponses.add(new PaymentCategoryResponse(paymentCategory));
        }
        return paymentCategoryResponses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPaymentCategory(@PathVariable("id") int id) {
        PaymentCategory paymentCategory = paymentCategoryService.getPaymentCategoryById(id);
        if (paymentCategory != null) {
            return ResponseEntity.ok(new PaymentCategoryResponse(paymentCategory));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment category with id " + id + " not found");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createPaymentCategory(@RequestBody @Valid PaymentCategoryRequest paymentCategoryRequest,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        PaymentCategory paymentCategory = paymentCategoryRequest.toPaymentCategory();
        paymentCategoryService.savePaymentCategory(paymentCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PaymentCategoryResponse(paymentCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePaymentCategory(@PathVariable("id") int id) {
        PaymentCategory paymentCategory = paymentCategoryService.getPaymentCategoryById(id);
        if (paymentCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment category with id " + id + " not found");
        }

        paymentCategoryService.deletePaymentCategoryById(id);
        return ResponseEntity.ok("Payment category with id " + id + " has been deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePaymentCategory(@PathVariable("id") int id,
                                                        @RequestBody @Valid PaymentCategoryRequest paymentCategoryRequest,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        PaymentCategory existingPaymentCategory = paymentCategoryService.getPaymentCategoryById(id);
        if (existingPaymentCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment category with id " + id + " not found");
        }

        existingPaymentCategory = paymentCategoryRequest.toPaymentCategory();
        existingPaymentCategory.setId(id);

        paymentCategoryService.savePaymentCategory(existingPaymentCategory);
        return ResponseEntity.ok(new PaymentCategoryResponse(existingPaymentCategory));
    }
}
