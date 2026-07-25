package com.tapcheck.mockbank.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tapcheck.mockbank.model.BankTransaction;
import com.tapcheck.mockbank.model.ChargeRequest;
import com.tapcheck.mockbank.service.BankTransactionService;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class BankTransactionController {

    private final BankTransactionService bankTransactionService;

    public BankTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @PostMapping
    public BankTransaction createTransaction(@RequestBody BankTransaction bankTransaction) {
        // Logic to create a bank transaction
        return bankTransactionService.createTransaction(bankTransaction);
    }

    @PostMapping("/charge")
    public BankTransaction chargeTransaction(@RequestBody ChargeRequest chargeRequest) {
        // Logic to charge a bank transaction
        return bankTransactionService.chargeTransaction(chargeRequest.getAccountId(), chargeRequest.getAmount(), chargeRequest.getMerchantName());
    }
}