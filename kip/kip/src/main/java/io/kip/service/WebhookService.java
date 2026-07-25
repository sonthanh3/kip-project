package io.kip.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.kip.model.LinkedAccount;
import io.kip.model.Transaction;
import io.kip.model.TransactionStatus;
import io.kip.model.WebhookPayload;
import io.kip.repository.LinkedAccountRepository;
import io.kip.repository.TransactionRepository;

@Service
public class WebhookService {
    
    private final TransactionRepository transactionRepository;
    private final LinkedAccountRepository linkedAccountRepository;

    public WebhookService(TransactionRepository transactionRepository, LinkedAccountRepository linkedAccountRepository){
        this.transactionRepository = transactionRepository;
        this.linkedAccountRepository = linkedAccountRepository;
        
    }

    public Transaction processWebhook(WebhookPayload payload){
        Optional<Transaction> existing = transactionRepository.findByExternalId(payload.getId().toString());

        if(existing.isPresent()) return existing.get();

        LinkedAccount linkedAccount = linkedAccountRepository
        .findByExternalId(payload.getBankAccount().getId().toString())
        .orElseGet(() -> {
        LinkedAccount newAccount = new LinkedAccount();
        newAccount.setExternalId(payload.getBankAccount().getId().toString());
        newAccount.setAccountNickname("Linked Account");
        newAccount.setLinkedAt(Instant.now());
        newAccount.setBalance(payload.getBankAccount().getBalance());
        return linkedAccountRepository.save(newAccount);
    });

    linkedAccount.setBalance(payload.getBankAccount().getBalance());
    linkedAccountRepository.save(linkedAccount);

        Transaction transaction = new Transaction();
        transaction.setExternalId(payload.getId().toString());
        transaction.setAmount(payload.getAmount());
        transaction.setMerchantName(payload.getMerchantName());
        transaction.setType(payload.getTransactionType());
        transaction.setDescription(payload.getDescription());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setLinkedAccount(linkedAccount);
        transaction.setTransactionDate(Instant.now());
        transaction.setSyncedAt(Instant.now());

        return transactionRepository.save(transaction);

    }
}    

    