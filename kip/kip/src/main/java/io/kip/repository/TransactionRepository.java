package io.kip.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.kip.model.LinkedAccount;
import io.kip.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Optional<Transaction> findByExternalId(String externalId);

    List<Transaction> findByLinkedAccount(LinkedAccount linkedAccount);
}
