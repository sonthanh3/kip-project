package com.tapcheck.mockbank.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tapcheck.mockbank.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    BankAccount findByAccountNumber(String accountNumber);
    
}
