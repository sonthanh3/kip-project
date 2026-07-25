package com.tapcheck.mockbank.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tapcheck.mockbank.model.BankAccount;
import com.tapcheck.mockbank.model.BankTransaction;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, UUID> {
    List<BankTransaction> findByBankAccount(BankAccount bankAccount);
    
    
}

