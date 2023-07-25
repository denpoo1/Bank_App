package com.onlinebank.controllers;

import com.onlinebank.dto.request.PiggyBankDepositAndWithrawRequest;
import com.onlinebank.dto.request.PiggyBankRequest;
import com.onlinebank.dto.response.PiggyBankResponse;
import com.onlinebank.models.AccountModel;
import com.onlinebank.models.PiggyBankModel;
import com.onlinebank.services.AccountService;
import com.onlinebank.services.PiggyBankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/piggy-banks")
@Tag(name = "Копилка", description = "Операции для копилки")
public class PiggyBankController {

    private final PiggyBankService piggyBankService;
    private final AccountService accountService;

    @Autowired
    public PiggyBankController(PiggyBankService piggyBankService, AccountService accountService) {
        this.piggyBankService = piggyBankService;
        this.accountService = accountService;
    }

    @GetMapping
    @Operation(summary = "Получить список копилок", description = "Получает список всех доступных копилок")
    @ApiResponse(responseCode = "200", description = "Список копилок успешно получен", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PiggyBankResponse.class))))
    public List<PiggyBankResponse> getPiggyBanks() {
        List<PiggyBankResponse> piggyBankResponses = new ArrayList<>();
        List<PiggyBankModel> piggyBankModels = piggyBankService.getAllPiggyBanks();
        for (PiggyBankModel piggyBankModel : piggyBankModels) {
            piggyBankResponses.add(new PiggyBankResponse(piggyBankModel));
        }
        return piggyBankResponses;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о копилке", description = "Получает информацию о копилке по её уникальному ID")
    @ApiResponse(responseCode = "200", description = "Информация о копилке успешно получена", content = @Content(schema = @Schema(implementation = PiggyBankResponse.class)))
    @ApiResponse(responseCode = "400", description = "Копилка с указанным ID не найдена")
    public ResponseEntity<Object> getPiggyBank(@PathVariable("id") int id) {
        PiggyBankModel piggyBankModel = piggyBankService.getPiggyBankById(id);
        if (piggyBankModel == null) return ResponseEntity.badRequest().body("PiggyBank with id " + id + " don't found");
        else return ResponseEntity.ok().body(new PiggyBankResponse(piggyBankModel));
    }

    @PostMapping
    @Operation(summary = "Создать новую копилку", description = "Создает новую копилку на основе переданных данных")
    @ApiResponse(responseCode = "200", description = "Копилка успешно создана", content = @Content(schema = @Schema(implementation = PiggyBankResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    public ResponseEntity<Object> createPiggyBank(@RequestBody @Valid PiggyBankRequest piggyBankRequest) {
        AccountModel accountModel = accountService.getAccountById(piggyBankRequest.getAccountID());
        if (accountModel == null)
            return ResponseEntity.badRequest().body("Account with id " + piggyBankRequest.getAccountID() + " don't found");
        PiggyBankModel piggyBankModel = piggyBankRequest.toPiggyBank(accountModel);
        piggyBankService.savePiggyBank(piggyBankModel);
        return ResponseEntity.ok().body(new PiggyBankResponse(piggyBankModel));
    }

    @PostMapping("/{id}/withraw")
    @Operation(summary = "Снять средства с копилки", description = "Снимает указанную сумму с копилки")
    @ApiResponse(responseCode = "200", description = "Средства успешно сняты", content = @Content(schema = @Schema(implementation = PiggyBankResponse.class)))
    @ApiResponse(responseCode = "400", description = "Копилка с указанным ID не найдена или недостаточно средств")
    public ResponseEntity<Object> makeDeposit(@PathVariable("id") int id, @RequestBody @Valid PiggyBankDepositAndWithrawRequest piggyBankDepositAndWithrawRequest) {
        PiggyBankModel piggyBankModel = piggyBankService.getPiggyBankById(id);
        if (piggyBankModel == null) return ResponseEntity.badRequest().body("PiggyBank with id " + id + " don't found");
        else if (piggyBankDepositAndWithrawRequest.getAmount() > piggyBankModel.getAmount())
            return ResponseEntity.badRequest().body("Requested amount exceeds the available balance in the piggy bank.");
        else {
            piggyBankModel.setAmount(piggyBankModel.getAmount() - piggyBankDepositAndWithrawRequest.getAmount());
            piggyBankService.savePiggyBank(piggyBankModel);
            return ResponseEntity.ok().body(new PiggyBankResponse(piggyBankModel));
        }
    }

    @PostMapping("/{id}/deposit")
    @Operation(summary = "Внести средства на копилку", description = "Вносит указанную сумму на копилку")
    @ApiResponse(responseCode = "200", description = "Средства успешно внесены", content = @Content(schema = @Schema(implementation = PiggyBankResponse.class)))
    @ApiResponse(responseCode = "400", description = "Копилка с указанным ID не найдена")
    public ResponseEntity<Object> makeWithraw(@PathVariable("id") int id, @RequestBody @Valid PiggyBankDepositAndWithrawRequest piggyBankDepositAndWithrawRequest) {
        PiggyBankModel piggyBankModel = piggyBankService.getPiggyBankById(id);
        if (piggyBankModel == null) return ResponseEntity.badRequest().body("PiggyBank with id " + id + " don't found");
        else {
            piggyBankModel.setAmount(piggyBankModel.getAmount() + piggyBankDepositAndWithrawRequest.getAmount());
            piggyBankService.savePiggyBank(piggyBankModel);
            System.out.println(piggyBankModel.getId());
            return ResponseEntity.ok().body(new PiggyBankResponse(piggyBankModel));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию о копилке", description = "Обновляет информацию о копилке на основе переданных данных")
    @ApiResponse(responseCode = "200", description = "Информация о копилке успешно обновлена", content = @Content(schema = @Schema(implementation = PiggyBankResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    public ResponseEntity<Object> updatePiggyBank(@PathVariable("id") int id,
                                                  @RequestBody @Valid PiggyBankRequest piggyBankRequest,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        PiggyBankModel existingPiggyBankModel = piggyBankService.getPiggyBankById(id);
        if (existingPiggyBankModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PiggyBank with id " + id + " not found");
        }
        AccountModel accountModel = accountService.getAccountById(piggyBankRequest.getAccountID());
        if (accountModel == null)
            return ResponseEntity.badRequest().body("Account with id " + piggyBankRequest.getAccountID() + " not found");
        existingPiggyBankModel = piggyBankRequest.toPiggyBank(accountModel);
        existingPiggyBankModel.setId(id);
        piggyBankService.savePiggyBank(existingPiggyBankModel);
        return ResponseEntity.ok(new PiggyBankResponse(existingPiggyBankModel));
    }

}
