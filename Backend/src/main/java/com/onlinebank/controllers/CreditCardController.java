package com.onlinebank.controllers;

import com.onlinebank.dto.request.CreditCardRequest;
import com.onlinebank.dto.response.AccountResponse;
import com.onlinebank.dto.response.CreditCardResponse;
import com.onlinebank.models.AccountModel;
import com.onlinebank.models.CreditCardModel;
import com.onlinebank.services.AccountService;
import com.onlinebank.services.CreditCardService;
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
@RequestMapping("/credit-cards")
@Tag(name = "", description = "")
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final AccountService accountService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService, AccountService accountService) {
        this.creditCardService = creditCardService;
        this.accountService = accountService;
    }

    @GetMapping
    @Operation(summary = "Получить все кредитные карты", description = "Получает список всех кредитных карт в системе")
    @ApiResponse(responseCode = "200", description = "Список кредитных карт", content = @Content(schema = @Schema(implementation = CreditCardResponse.class)))
    public List<CreditCardResponse> getCreditCards() {
        List<CreditCardModel> creditCardModels = creditCardService.getAllCreditCards();
        List<CreditCardResponse> creditCardResponses = new ArrayList<>();
        for (CreditCardModel creditCardModel : creditCardModels) {
            creditCardResponses.add(new CreditCardResponse(creditCardModel));
        }
        return creditCardResponses;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить кредитную карту по ID", description = "Получает информацию о кредитной карте по её уникальному ID")
    @ApiResponse(responseCode = "200", description = "Информация о кредитной карте", content = @Content(schema = @Schema(implementation = CreditCardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Кредитная карта не найдена")
    public ResponseEntity<Object> getCreditCard(@PathVariable("id") int id) {
        CreditCardModel creditCardModel = creditCardService.getCreditCardById(id);
        if (creditCardModel != null) {
            return ResponseEntity.ok(new CreditCardResponse(creditCardModel));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit card with id " + id + " not found");
        }
    }

    @PostMapping
    @Operation(summary = "Создать новую кредитную карту", description = "Создает новую кредитную карту для указанного счета")
    @ApiResponse(responseCode = "201", description = "Кредитная карта успешно создана", content = @Content(schema = @Schema(implementation = CreditCardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    public ResponseEntity<Object> createCreditCard(@RequestBody @Valid CreditCardRequest creditCardRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        AccountModel accountModel = accountService.getAccountById(creditCardRequest.getAccountId());
        if (accountModel == null)
            return ResponseEntity.badRequest().body("Account with id " + creditCardRequest.getAccountId() + " don't found");
        CreditCardModel creditCardModel = creditCardRequest.toCreditCard(accountModel);
        creditCardService.saveCreditCard(creditCardModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreditCardResponse(creditCardModel));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить кредитную карту", description = "Удаляет кредитную карту по её уникальному ID")
    @ApiResponse(responseCode = "200", description = "Кредитная карта успешно удалена")
    @ApiResponse(responseCode = "404", description = "Кредитная карта не найдена")
    public ResponseEntity<Object> deleteCreditCard(@PathVariable("id") int id) {
        CreditCardModel creditCardModel = creditCardService.getCreditCardById(id);
        if (creditCardModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit card with id " + id + " not found");
        }

        creditCardService.deleteCreditCardById(id);
        return ResponseEntity.ok("Credit card with id " + id + " has been deleted successfully.");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию о кредитной карте", description = "Обновляет информацию о кредитной карте по её уникальному ID")
    @ApiResponse(responseCode = "200", description = "Информация о кредитной карте успешно обновлена", content = @Content(schema = @Schema(implementation = CreditCardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    @ApiResponse(responseCode = "404", description = "Кредитная карта не найдена")
    public ResponseEntity<Object> updateCreditCard(@PathVariable("id") int id, @RequestBody @Valid CreditCardRequest creditCardRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        CreditCardModel existingCreditCardModel = creditCardService.getCreditCardById(id);
        if (existingCreditCardModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit card with id " + id + " not found");
        }

        AccountModel accountModel = accountService.getAccountById(creditCardRequest.getAccountId());
        if (accountModel == null) {
            return ResponseEntity.badRequest().body("Account with id " + creditCardRequest.getAccountId() + " not found");
        }

        existingCreditCardModel = creditCardRequest.toCreditCard(accountModel);
        existingCreditCardModel.setId(id);

        creditCardService.saveCreditCard(existingCreditCardModel);
        return ResponseEntity.ok(new CreditCardResponse(existingCreditCardModel));
    }

}
