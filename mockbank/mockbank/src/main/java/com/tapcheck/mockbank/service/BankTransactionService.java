package com.tapcheck.mockbank.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tapcheck.mockbank.model.BankAccount;
import com.tapcheck.mockbank.model.BankTransaction;
import com.tapcheck.mockbank.model.TransactionStatus;
import com.tapcheck.mockbank.repository.BankAccountRepository;
import com.tapcheck.mockbank.repository.BankTransactionRepository;


@Service
public class BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final RestTemplate restTemplate;

    public BankTransactionService(BankTransactionRepository bankTransactionRepository, BankAccountRepository bankAccountRepository, RestTemplate restTemplate) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.restTemplate = restTemplate;
    }

    public BankTransaction createTransaction(BankTransaction bankTransaction) {
        return bankTransactionRepository.save(bankTransaction);
    }

    public BankTransaction chargeTransaction(UUID accountId, BigDecimal amount, String merchantName) {
        BankTransaction bankTransaction = new BankTransaction();
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow();
        bankTransaction.setBankAccount(account);
        bankTransaction.setAmount(amount);
        bankTransaction.setMerchantName(merchantName);
        bankTransaction.setStatus(TransactionStatus.PENDING);

        BankTransaction saved = bankTransactionRepository.save(bankTransaction);
   
        try {
            restTemplate.postForObject("http://localhost:8081/webhooks/mockbank", saved, String.class);
        } catch (Exception e) {
            System.err.println("Webhook failed: " + e.getMessage());
        }

        return saved;
          
    }

}