package com.onlinebank.controllers;

import com.onlinebank.dto.request.PiggyBankDepositAndWithrawRequest;
import com.onlinebank.dto.request.PiggyBankRequest;
import com.onlinebank.dto.response.PiggyBankResponse;
import com.onlinebank.models.AccountModel;
import com.onlinebank.models.PiggyBankModel;
import com.onlinebank.services.AccountService;
import com.onlinebank.services.PiggyBankService;
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
public class PiggyBankController {

    private final PiggyBankService piggyBankService;
    private final AccountService accountService;

    @Autowired
    public PiggyBankController(PiggyBankService piggyBankService, AccountService accountService) {
        this.piggyBankService = piggyBankService;
        this.accountService = accountService;
    }

    @GetMapping
    public List<PiggyBankResponse> getPiggyBanks() {
        List<PiggyBankResponse> piggyBankResponses = new ArrayList<>();
        List<PiggyBankModel> piggyBankModels = piggyBankService.getAllPiggyBanks();
        for (PiggyBankModel piggyBankModel : piggyBankModels) {
            piggyBankResponses.add(new PiggyBankResponse(piggyBankModel));
        }
        return piggyBankResponses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPiggyBank(@PathVariable("id") int id) {
        PiggyBankModel piggyBankModel = piggyBankService.getPiggyBankById(id);
        if (piggyBankModel == null) return ResponseEntity.badRequest().body("PiggyBank with id " + id + " don't found");
        else return ResponseEntity.ok().body(new PiggyBankResponse(piggyBankModel));
    }

    @PostMapping
    public ResponseEntity<Object> createPiggyBank(@RequestBody @Valid PiggyBankRequest piggyBankRequest) {
        AccountModel accountModel = accountService.getAccountById(piggyBankRequest.getAccountID());
        if (accountModel == null)
            return ResponseEntity.badRequest().body("Account with id " + piggyBankRequest.getAccountID() + " don't found");
        PiggyBankModel piggyBankModel = piggyBankRequest.toPiggyBank(accountModel);
        piggyBankService.savePiggyBank(piggyBankModel);
        return ResponseEntity.ok().body(new PiggyBankResponse(piggyBankModel));
    }

    @PostMapping("/{id}/withraw")
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
