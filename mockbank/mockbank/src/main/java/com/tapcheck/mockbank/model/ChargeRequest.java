package com.tapcheck.mockbank.model;

import java.math.BigDecimal;
import java.util.UUID;

public class ChargeRequest {
    private UUID accountId;
    private BigDecimal amount;
    private String merchantName;

    public ChargeRequest() {}

    public UUID getAccountId() { 
        return accountId; 
    }

    public BigDecimal getAmount() { 
        return amount; 
    }

    public String getMerchantName(){
        return merchantName;
    }

    public void setAccountId(UUID accountId) { 
        this.accountId = accountId; 
    }
    
    public void setAmount(BigDecimal amount) { 
        this.amount = amount; 
    }

    public void setMerchantName(String merchantName){
        this.merchantName = merchantName;
    }
}