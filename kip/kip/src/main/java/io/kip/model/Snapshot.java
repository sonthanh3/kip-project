package io.kip.model;

import java.math.BigDecimal;

public class Snapshot{
    private BigDecimal balance;
    private BigDecimal pendingTotal;
    private BigDecimal safeToSpend;

    public Snapshot(BigDecimal balance, BigDecimal pendingTotal, BigDecimal safeToSpend) {
        this.balance = balance;
        this.pendingTotal = pendingTotal;
        this.safeToSpend = safeToSpend;
    }

    public BigDecimal getBalance() { return balance; }
    public BigDecimal getPendingTotal() { return pendingTotal; }
    public BigDecimal getSafeToSpend() { return safeToSpend; }

}
