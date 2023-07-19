package com.onlinebank.controllers;

import com.onlinebank.dto.PiggyBankRequest;
import com.onlinebank.dto.PiggyBankResponse;
import com.onlinebank.models.Account;
import com.onlinebank.models.PiggyBank;
import com.onlinebank.services.AccountService;
import com.onlinebank.services.PiggyBankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        List<PiggyBank> piggyBanks = piggyBankService.getAllPiggyBanks();
        for (PiggyBank piggyBank : piggyBanks) {
            piggyBankResponses.add(new PiggyBankResponse(piggyBank));
        }
        return piggyBankResponses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPiggyBank(@PathVariable("id") int id) {
        PiggyBank piggyBank = piggyBankService.getPiggyBankById(id);
        if (piggyBank == null) return ResponseEntity.badRequest().body("PiggyBank with id " + id + " don't found");
        else return ResponseEntity.ok().body(new PiggyBankResponse(piggyBank));
    }

    @PostMapping
    public ResponseEntity<Object> createPiggyBank(@RequestBody @Valid PiggyBankRequest piggyBankRequest) {
        Account account = accountService.getAccountById(piggyBankRequest.getAccountID());
        if (account == null)
            return ResponseEntity.badRequest().body("Account with id " + piggyBankRequest.getAccountID() + " don't found");
        PiggyBank piggyBank = piggyBankRequest.toPiggyBank(account);
        piggyBankService.savePiggyBank(piggyBank);
        return ResponseEntity.ok().body(new PiggyBankResponse(piggyBank));
    }
}
