package com.onlinebank.controllers;

import com.onlinebank.dto.response.CreditCardResponse;
import com.onlinebank.dto.request.CustomerRequest;
import com.onlinebank.dto.response.CustomerResponse;
import com.onlinebank.models.CreditCardModel;
import com.onlinebank.models.CustomerModel;
import com.onlinebank.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Клиенты", description = "Управление клиентами")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    @Operation(summary = "Получить список клиентов", description = "Получает список всех клиентов в системе")
    @ApiResponse(responseCode = "200", description = "Список клиентов", content = @Content(schema = @Schema(implementation = CustomerResponse.class)))
    public List<CustomerResponse> getCustomers() {
        List<CustomerModel> customerModelList = customerService.getAllCustomers();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (CustomerModel customerModel : customerModelList) {
            customerResponses.add(new CustomerResponse(customerModel));
        }
        return customerResponses;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить клиента по ID", description = "Получает информацию о клиенте по его уникальному ID")
    @ApiResponse(responseCode = "200", description = "Информация о клиенте", content = @Content(schema = @Schema(implementation = CustomerResponse.class)))
    @ApiResponse(responseCode = "404", description = "Клиент не найден")
    public ResponseEntity<Object> getCustomer(@PathVariable("id") int id) {
        CustomerModel customerModel = customerService.getCustomerById(id);
        if (customerModel != null) {
            return ResponseEntity.ok(new CustomerResponse(customerModel));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with id " + id + " not found");
        }
    }

    @PostMapping
    @Operation(summary = "Создать нового клиента", description = "Создает нового клиента в системе")
    @ApiResponse(responseCode = "201", description = "Клиент успешно создан", content = @Content(schema = @Schema(implementation = CustomerResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
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
    @Operation(summary = "Получить список кредитных карт клиента", description = "Получает список кредитных карт, привязанных к клиенту")
    @ApiResponse(responseCode = "200", description = "Список кредитных карт", content = @Content(schema = @Schema(implementation = CreditCardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    @ApiResponse(responseCode = "404", description = "Клиент не найден")
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
    @Operation(summary = "Удалить клиента", description = "Удаляет клиента из системы по его уникальному ID")
    @ApiResponse(responseCode = "200", description = "Клиент успешно удален")
    @ApiResponse(responseCode = "404", description = "Клиент не найден")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") int id) {
        CustomerModel customerModel = customerService.getCustomerById(id);
        if (customerModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with id " + id + " not found");
        }

        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer with id " + id + " has been deleted successfully.");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию о клиенте", description = "Обновляет информацию о клиенте по его уникальному ID")
    @ApiResponse(responseCode = "200", description = "Информация о клиенте успешно обновлена", content = @Content(schema = @Schema(implementation = CustomerResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    @ApiResponse(responseCode = "404", description = "Клиент не найден")
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
