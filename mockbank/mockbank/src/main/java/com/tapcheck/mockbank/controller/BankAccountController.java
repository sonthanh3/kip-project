package com.tapcheck.mockbank.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tapcheck.mockbank.model.BankAccount;
import com.tapcheck.mockbank.service.BankAccountService;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:5173")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public BankAccount createAccount(@RequestBody BankAccount bankAccount) {
        // Logic to create a bank account
        return bankAccountService.createAccount(bankAccount);
    }
    
}
