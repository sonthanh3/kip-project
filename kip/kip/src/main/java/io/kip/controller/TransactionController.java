package io.kip.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kip.model.Snapshot;
import io.kip.model.Transaction;
import io.kip.model.WeeklyReport;
import io.kip.service.TransactionService;



@RestController
@RequestMapping("/linked-accounts")
public class TransactionController{

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable UUID id){
        return transactionService.getTransactionsByLinkedAccount(id);
    }

    @GetMapping("/{id}/snapshot")
    public Snapshot getSnapshot(@PathVariable UUID id) {
        return transactionService.getSnapshot(id);
    }

    @GetMapping("/{id}/weekly-report")
    public WeeklyReport getWeeklyReport(@PathVariable UUID id) {
        return transactionService.getWeeklyReport(id);
    }
    
    
}

