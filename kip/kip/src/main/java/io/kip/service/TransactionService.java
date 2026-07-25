package io.kip.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.kip.model.LinkedAccount;
import io.kip.model.Snapshot;
import io.kip.model.Transaction;
import io.kip.model.TransactionStatus;
import io.kip.model.WeeklyReport;
import io.kip.repository.LinkedAccountRepository;
import io.kip.repository.TransactionRepository;

@Service
public class TransactionService{

    private final TransactionRepository transactionRepository;
    private final LinkedAccountRepository linkedAccountRepository;
    private final KipperService kipperService;

    public TransactionService(TransactionRepository transactionRepository, LinkedAccountRepository linkedAccountRepository, KipperService kipperService){
        this.transactionRepository = transactionRepository;
        this.linkedAccountRepository = linkedAccountRepository;
        this.kipperService = kipperService;
    }

    public List<Transaction> getTransactionsByLinkedAccount(UUID linkedAccountId){
        LinkedAccount account = linkedAccountRepository.findById(linkedAccountId).orElseThrow();
        return transactionRepository.findByLinkedAccount(account);
    }

    public Snapshot getSnapshot(UUID linkedAccountId){
        LinkedAccount account = linkedAccountRepository.findById(linkedAccountId).orElseThrow();
        List<Transaction> transactions = transactionRepository.findByLinkedAccount(account);

        BigDecimal pendingTotal = transactions.stream()
            .filter(t -> t.getStatus() == TransactionStatus.PENDING)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal balance = account.getBalance();
        BigDecimal safeToSpend = balance.subtract(pendingTotal);    

        return new Snapshot(balance, pendingTotal, safeToSpend);
    }

    public WeeklyReport getWeeklyReport(UUID linkedAccountId){
        LinkedAccount account = linkedAccountRepository.findById(linkedAccountId).orElseThrow();
        List<Transaction> allTransactions = transactionRepository.findByLinkedAccount(account);

        Instant sevenDaysAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        Instant now = Instant.now();

        List<Transaction> weeklyTransactions = allTransactions.stream().filter(t -> t.getTransactionDate() != null && t.getTransactionDate().isAfter(sevenDaysAgo)).collect(Collectors.toList());

        BigDecimal totalSpent = weeklyTransactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> categoryBreakdown = weeklyTransactions.stream()
            .collect(Collectors.groupingBy(
            t -> t.getType() != null ? t.getType() : "Uncategorized",
            Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));
        
        Instant fourteenDaysAgo = Instant.now().minus(14, ChronoUnit.DAYS);  
        List<Transaction> previousWeekTransactions = allTransactions.stream()
            .filter(t -> t.getTransactionDate() != null 
            && t.getTransactionDate().isAfter(fourteenDaysAgo)
            && t.getTransactionDate().isBefore(sevenDaysAgo))
            .collect(Collectors.toList());  

        BigDecimal previousWeekTotal = previousWeekTransactions.stream()
        .map(Transaction::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);    

        BigDecimal spendingChange = BigDecimal.ZERO;
        if (previousWeekTotal.compareTo(BigDecimal.ZERO) > 0) {
        spendingChange = totalSpent.subtract(previousWeekTotal)
        .divide(previousWeekTotal, 2, RoundingMode.HALF_UP)
        .multiply(new BigDecimal("100"));
        }
        
        BigDecimal average = weeklyTransactions.isEmpty() ? BigDecimal.ZERO :
        totalSpent.divide(new BigDecimal(weeklyTransactions.size()), 2, RoundingMode.HALF_UP);

        List<Transaction> anomalies = weeklyTransactions.stream()
        .filter(t -> t.getAmount().compareTo(average.multiply(new BigDecimal("3"))) > 0)
        .collect(Collectors.toList());    

        String kipperFeedback = kipperService.getFeedback(totalSpent, weeklyTransactions.size(), categoryBreakdown, previousWeekTotal, anomalies.size());

        return new WeeklyReport(sevenDaysAgo, now, totalSpent, weeklyTransactions.size(), 
        weeklyTransactions, categoryBreakdown, kipperFeedback, 
        previousWeekTotal, spendingChange, anomalies);
    }

}