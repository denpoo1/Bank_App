package com.onlinebank.controllers;

import com.onlinebank.dto.CreditCardResponse;
import com.onlinebank.dto.CustomerRequest;
import com.onlinebank.dto.CustomerResponse;
import com.onlinebank.models.CreditCard;
import com.onlinebank.models.Customer;
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
        List<Customer> customerList = customerService.getAllCustomers();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customerList) {
            customerResponses.add(new CustomerResponse(customer));
        }
        return customerResponses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable("id") int id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(new CustomerResponse(customer));
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
        Customer customer = customerRequest.toCustomer();
        customerService.saveCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerResponse(customer));
    }

    @GetMapping("/{id}/credit-cards")
    public ResponseEntity<Object> getCustomerCreditCards(@PathVariable("id") int id) {
        System.out.println("Hello");
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return ResponseEntity.badRequest().body("Customer with id " + id + " not found");
        } else {
            List<CreditCard> creditCards = customer.getAccount().getCreditCards();
            List<CreditCardResponse> creditCardResponses = new ArrayList<>();
            for(CreditCard creditCard : creditCards) {
                creditCardResponses.add(new CreditCardResponse(creditCard));
            }
            return ResponseEntity.ok().body(creditCardResponses);
        }
    }
}
