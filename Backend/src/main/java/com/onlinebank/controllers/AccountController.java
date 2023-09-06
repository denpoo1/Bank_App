package com.onlinebank.controllers;

import com.onlinebank.dto.request.AccountRequest;
import com.onlinebank.dto.response.AccountResponse;
import com.onlinebank.dto.response.AccountUpdateResponse;
import com.onlinebank.dto.response.TransactionResponse;
import com.onlinebank.models.AccountModel;
import com.onlinebank.models.CustomerModel;
import com.onlinebank.models.PiggyBankModel;
import com.onlinebank.models.TransactionModel;
import com.onlinebank.services.AccountService;
import com.onlinebank.services.CustomerService;
import com.onlinebank.services.StorageService;
import com.onlinebank.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Denis Durbalov
 */
@RestController
@RequestMapping("/accounts")
@Tag(name = "Аккаунты", description = "Операции связанные с аккаунтами")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    private final StorageService storageService;

    @Autowired
    public AccountController(AccountService accountService, CustomerService customerService, TransactionService transactionService, StorageService storageService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.storageService = storageService;
    }

    @GetMapping
    @Operation(summary = "Получить список всех аккаунтов", description = "Возвращает список всех аккаунтов")
    public List<AccountResponse> getAccounts() {
        List<AccountModel> accountModels = accountService.getAllAccounts();
        List<AccountResponse> accountResponses = new ArrayList<>();
        for (AccountModel accountModel : accountModels) {
            accountResponses.add(new AccountResponse(accountModel));
        }
        return accountResponses;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию об аккаунте", description = "Возвращает информацию об аккаунте по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный запрос", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "404", description = "Аккаунт не найден")
    public ResponseEntity<Object> getAccount(@PathVariable("id") int id) {
        AccountModel accountModel = accountService.getAccountById(id);
        if (accountModel != null) {
            return ResponseEntity.ok(new AccountResponse(accountModel));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with id " + id + " not found");
        }
    }

    @PostMapping
    @Operation(summary = "Создать новый аккаунт", description = "Создает новый аккаунт на основе данных в теле запроса")
    @ApiResponse(responseCode = "201", description = "Аккаунт успешно создан", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    public ResponseEntity<Object> createAccount(@RequestBody @Valid AccountRequest accountRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        CustomerModel customerModel = customerService.getCustomerById(accountRequest.getCustomerId());
        System.out.println(accountRequest.getCustomerId());
        if (customerModel == null)
            return ResponseEntity.badRequest().body("Customer with id " + accountRequest.getCustomerId() + " don't found");
        AccountModel accountModel = accountRequest.toAccount(customerModel);
        List<PiggyBankModel> piggyBankModels = new ArrayList<>();
        piggyBankModels.add(new PiggyBankModel(accountModel, accountRequest.getDate(), 0));
        accountModel.setPiggyBankModels(piggyBankModels);
        accountService.saveAccount(accountModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponse(accountModel));
    }

    @GetMapping("/{id}/transaction")
    @Operation(summary = "Получить транзакции аккаунта", description = "Возвращает список транзакций для аккаунта по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный запрос", content = @Content(schema = @Schema(implementation = TransactionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Аккаунт не найден")
    public ResponseEntity<Object> getAccountTransactions(@PathVariable("id") int accountId) {
        AccountModel accountModel = accountService.getAccountById(accountId);
        if (accountModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with id " + accountId + " not found");
        }

        List<TransactionModel> transactionModels = transactionService.getAccountTransactions(accountId);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (TransactionModel transactionModel : transactionModels) {
            transactionResponses.add(new TransactionResponse(transactionModel));
        }

        return ResponseEntity.ok(transactionResponses);
    }


    @GetMapping("/{id}/{start_day}/{end_day}")
    @Operation(summary = "Получить транзакции аккаунта по дате", description = "Возвращает список транзакций для аккаунта между указанными датами")
    @ApiResponse(responseCode = "200", description = "Успешный запрос", content = @Content(schema = @Schema(implementation = TransactionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Аккаунт не найден или транзакции не найдены")
    public ResponseEntity<Object> getAccountTransactionsByDays(@PathVariable("id") int id, @PathVariable("start_day") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDay,
                                                               @PathVariable("end_day") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDay) {
        AccountModel accountModel = accountService.getAccountById(id);
        if (accountModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with id " + id + " not found");
        List<TransactionModel> transactionModels = transactionService.getAccountTransactionsByDays(id, id, startDay, endDay);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        if (transactionModels.isEmpty())
            return ResponseEntity.badRequest().body("Transactions between " + startDay + " - " + endDay + " don't found");
        for (TransactionModel transactionModel : transactionModels) {
            transactionResponses.add(new TransactionResponse(transactionModel));
        }
        return ResponseEntity.ok().body(transactionResponses);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить аккаунт", description = "Удаляет аккаунт по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Аккаунт успешно удален")
    @ApiResponse(responseCode = "404", description = "Аккаунт не найден")
    public ResponseEntity<Object> deleteAccount(@PathVariable("id") int id) {
        AccountModel accountModel = accountService.getAccountById(id);
        if (accountModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with id " + id + " not found");
        }

        accountService.deleteAccountById(id);
        return ResponseEntity.ok("Account with id " + id + " has been deleted successfully.");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию об аккаунте", description = "Обновляет информацию об аккаунте по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Информация об аккаунте успешно обновлена", content = @Content(schema = @Schema(implementation = AccountUpdateResponse.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    @ApiResponse(responseCode = "404", description = "Аккаунт или пользователь не найден")
    public ResponseEntity<Object> updateAccount(@PathVariable("id") int id, @RequestBody @Valid AccountRequest accountRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        AccountModel existingAccountModel = accountService.getAccountById(id);
        if (existingAccountModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with id " + id + " not found");
        }

        CustomerModel customerModel = customerService.getCustomerById(accountRequest.getCustomerId());
        if (customerModel == null) {
            return ResponseEntity.badRequest().body("Customer with id " + accountRequest.getCustomerId() + " not found");
        }
        existingAccountModel = accountRequest.toAccount(customerModel);
        existingAccountModel.setId(id);

        accountService.saveAccount(existingAccountModel);
        return ResponseEntity.ok(new AccountUpdateResponse(existingAccountModel));
    }

    @PostMapping("/{account_id}/upload-avatar")
    @Operation(summary = "Загрузить аватарку", description = "Загружает аватарку на сервер")
    @ApiResponse(responseCode = "200", description = "Файл успешно загружен")
    @ApiResponse(responseCode = "400", description = "Ошибка при загрузке файла")
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "Аватарка для загрузки")
            @RequestParam("file") MultipartFile file,

            @PathVariable("account_id") int accountId
    ) {
        try {
            return new ResponseEntity<>(storageService.uploadFile(file, accountId), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download-avatar")
    @Operation(summary = "Скачать аватарку пользователя", description = "Скачивает аватарку по его URL")
    @ApiResponse(responseCode = "200", description = "Файл успешно скачан")
    @ApiResponse(responseCode = "404", description = "Файл не найден")
    public ResponseEntity<byte[]> downloadFile(
            @Parameter(description = "URL файла для скачивания")
            @RequestParam("file-url") String fileUrl
    ) {
        byte[] data = storageService.downloadFile(fileUrl);
        return ResponseEntity
                .ok()
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileUrl + "\"")
                .body(data);
    }

    @DeleteMapping("/delete-avatar/{account-id}")
    @Operation(summary = "Удалить аватарку пользователя", description = "Удаляет файл по его URL")
    @ApiResponse(responseCode = "200", description = "Файл успешно удален")
    @ApiResponse(responseCode = "404", description = "Файл не найден")
    public ResponseEntity<String> deleteFile(
            @Parameter(description = "URL файла для удаления")
            @RequestParam("file-url") String fileUrl,
            @PathVariable("account-id") int id
    ) {
        AccountModel existingAccountModel = accountService.getAccountById(id);
        if (existingAccountModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with id " + id + " not found");
        }
        existingAccountModel.setAvatarUrl("DEFAULT");
        accountService.saveAccount(existingAccountModel);
        return new ResponseEntity<>(storageService.deleteFile(fileUrl), HttpStatus.OK);
    }
}
