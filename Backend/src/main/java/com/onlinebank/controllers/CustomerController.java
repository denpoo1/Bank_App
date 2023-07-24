package com.onlinebank.controllers;

import com.onlinebank.dto.response.CreditCardResponse;
import com.onlinebank.dto.request.CustomerRequest;
import com.onlinebank.dto.response.CustomerResponse;
import com.onlinebank.models.CreditCardModel;
import com.onlinebank.models.CustomerModel;
import com.onlinebank.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Denis Durbalov
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<CustomerResponse> getCustomers() {
        List<CustomerModel> customerModelList = customerService.getAllCustomers();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (CustomerModel customerModel : customerModelList) {
            customerResponses.add(new CustomerResponse(customerModel));
        }
        return customerResponses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable("id") int id) {
        CustomerModel customerModel = customerService.getCustomerById(id);
        if (customerModel != null) {
            return ResponseEntity.ok(new CustomerResponse(customerModel));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with id " + id + " not found");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid CustomerRequest customerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        CustomerModel customerModel = customerRequest.toCustomer();
        customerService.saveCustomer(customerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerResponse(customerModel));
    }

    @GetMapping("/{id}/credit-cards")
    public ResponseEntity<Object> getCustomerCreditCards(@PathVariable("id") int id) {
        System.out.println("Hello");
        CustomerModel customerModel = customerService.getCustomerById(id);
        if (customerModel == null) {
            return ResponseEntity.badRequest().body("Customer with id " + id + " not found");
        } else {
            List<CreditCardModel> creditCardModels = customerModel.getAccountModel().getCreditCardModels();
            List<CreditCardResponse> creditCardResponses = new ArrayList<>();
            for (CreditCardModel creditCardModel : creditCardModels) {
                creditCardResponses.add(new CreditCardResponse(creditCardModel));
            }
            return ResponseEntity.ok().body(creditCardResponses);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") int id) {
        CustomerModel customerModel = customerService.getCustomerById(id);
        if (customerModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with id " + id + " not found");
        }

        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer with id " + id + " has been deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable("id") int id, @RequestBody @Valid CustomerRequest customerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        CustomerModel existingCustomerModel = customerService.getCustomerById(id);
        if (existingCustomerModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with id " + id + " not found");
        }
        existingCustomerModel = customerRequest.toCustomer();
        existingCustomerModel.setId(id);

        customerService.saveCustomer(existingCustomerModel);
        return ResponseEntity.ok(new CustomerResponse(existingCustomerModel));
    }


}
