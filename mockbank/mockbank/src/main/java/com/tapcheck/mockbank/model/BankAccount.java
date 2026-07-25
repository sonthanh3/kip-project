package com.tapcheck.mockbank.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    
    @Id  
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String ownerName;
    private BigDecimal balance;
    private String accountNumber;
    private Instant createdAt;
    
    public BankAccount(){}

    public BankAccount(UUID id, String ownerName, BigDecimal balance, String accountNumber, Instant createdAt){
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
