package io.kip.model;

import java.math.BigDecimal;
import java.util.UUID;

public class WebhookPayload {
    private UUID id;
    private BankAccountRef bankAccount;
    private BigDecimal amount;
    private String merchantName;
    private String transactionType;
    private String description;
    private String status;

    public WebhookPayload() {}

    public WebhookPayload(UUID id, BankAccountRef bankAccount, BigDecimal amount, String merchantName, String transactionType, String description, String status) {
        this.id = id;
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.merchantName = merchantName;
        this.transactionType = transactionType;
        this.description = description;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BankAccountRef getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountRef bankAccount) {
        this.bankAccount = bankAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}