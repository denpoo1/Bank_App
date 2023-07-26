package com.onlinebank.controllers;

import com.onlinebank.dto.request.TransactionRequest;
import com.onlinebank.dto.response.TransactionResponse;
import com.onlinebank.models.AccountModel;
import com.onlinebank.models.TransactionModel;
import com.onlinebank.services.AccountService;
import com.onlinebank.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping()
    @Operation(summary = "Получить список всех транзакций", description = "Получает список всех транзакций")
    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionModel.class))))
    public List<TransactionModel> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить транзакцию по ID", description = "Получает транзакцию по указанному ID")
    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = TransactionModel.class)))
    @ApiResponse(responseCode = "404", description = "Транзакция не найдена")
    public ResponseEntity<Object> getTransaction(@PathVariable("id") int id) {
        TransactionModel transactionModel = transactionService.getTransactionById(id);
        if (transactionModel != null) {
            return ResponseEntity.ok(transactionModel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction with id " + id + " don't found");
        }
    }

    @PostMapping
    @Operation(summary = "Создать новую транзакцию", description = "Создает новую транзакцию на основе переданных данных")
    @ApiResponse(responseCode = "201", description = "Транзакция успешно создана", content = @Content(schema = @Schema(implementation = TransactionResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    public ResponseEntity<Object> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        TransactionModel transactionModel = transactionRequest.toTransaction();
        System.out.println(transactionRequest.getToAccountId());
        transactionService.saveTransaction(transactionModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponse(transactionModel));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить транзакцию", description = "Удаляет транзакцию с указанным ID")
    @ApiResponse(responseCode = "200", description = "Транзакция успешно удалена")
    @ApiResponse(responseCode = "404", description = "Транзакция не найдена")
    public ResponseEntity<Object> deleteTransaction(@PathVariable("id") int id) {
        TransactionModel transactionModel = transactionService.getTransactionById(id);
        if (transactionModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction with id " + id + " not found");
        }

        transactionService.deleteTransactionById(id);
        return ResponseEntity.ok("Transaction with id " + id + " has been deleted successfully.");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию о транзакции", description = "Обновляет информацию о транзакции на основе переданных данных")
    @ApiResponse(responseCode = "200", description = "Информация о транзакции успешно обновлена", content = @Content(schema = @Schema(implementation = TransactionResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    @ApiResponse(responseCode = "404", description = "Транзакция не найдена")
    public ResponseEntity<Object> updateTransaction(@PathVariable("id") int id,
                                                    @RequestBody @Valid TransactionRequest transactionRequest,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        TransactionModel existingTransactionModel = transactionService.getTransactionById(id);
        if (existingTransactionModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction with id " + id + " not found");
        }

        AccountModel accountModelTo = accountService.getAccountById(transactionRequest.getToAccountId());
        if (accountModelTo == null) return ResponseEntity.badRequest().body("toAccountId with id " + transactionRequest.getToAccountId() + " not found");
        AccountModel accountModelFrom = accountService.getAccountById(transactionRequest.getFromAccountId());
        if (accountModelFrom == null) return ResponseEntity.badRequest().body("fromAccountId with id " + transactionRequest.getFromAccountId() + " not found");
        existingTransactionModel = transactionRequest.toTransaction();
        existingTransactionModel.setId(id);

        transactionService.saveTransaction(existingTransactionModel);
        return ResponseEntity.ok(new TransactionResponse(existingTransactionModel));
    }

}
