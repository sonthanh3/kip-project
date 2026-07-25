package io.kip.model;

import java.math.BigDecimal;
import java.util.UUID;

public class BankAccountRef {
    private UUID id;
    private BigDecimal balance;

    public BankAccountRef() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public BigDecimal getBalance(){
        return balance;
    }

    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }
}