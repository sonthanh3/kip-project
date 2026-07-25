package io.kip.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;


public class WeeklyReport {
    private Instant startDate;
    private Instant endDate;
    private BigDecimal totalSpent;
    private int transactionCount;
    private List<Transaction> transaction;
    private Map<String, BigDecimal> categoryBreakdown;
    private String kipperFeedback;
    private BigDecimal previousWeekTotal;
    private BigDecimal spendingChange;
    private List<Transaction> anomalies;

    public WeeklyReport(Instant startDate, Instant endDate, BigDecimal totalSpent, int transactionCount, List<Transaction> transaction, Map<String, BigDecimal> categoryBreakdown, String kipperFeedback, BigDecimal previousWeekTotal, BigDecimal spendingChange, List<Transaction> anomalies){
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalSpent = totalSpent;
        this.transactionCount = transactionCount;
        this.transaction = transaction;
        this.categoryBreakdown = categoryBreakdown;
        this.kipperFeedback = kipperFeedback;
        this.previousWeekTotal = previousWeekTotal;
        this.spendingChange = spendingChange;
        this.anomalies = anomalies;

    }

    public Instant getStartDate() {return startDate;}
    public Instant getEndDate() {return endDate;}
    public BigDecimal getTotalSpent() {return totalSpent;}
    public int getTransactionCount() {return transactionCount;}
    public List<Transaction> getTransaction() {return transaction;}
    public Map<String, BigDecimal> getCategoryBreakdown() {return categoryBreakdown;}
    public String getKipperFeedback() { return kipperFeedback;}
    public BigDecimal getPreviousWeekTotal() {return previousWeekTotal;}
    public BigDecimal getSpendingChange() {return spendingChange;}
    public List<Transaction> getAnomalies() {return anomalies;}

}
