package com.tapcheck.mockbank.service;

import org.springframework.stereotype.Service;

import com.tapcheck.mockbank.model.BankAccount;
import com.tapcheck.mockbank.repository.BankAccountRepository;


@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    
    }

    public BankAccount createAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    
}
