package io.kip.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.kip.model.LinkedAccount;

public interface LinkedAccountRepository extends JpaRepository<LinkedAccount, UUID> {
    Optional<LinkedAccount> findByExternalId(String externalId);
    
}
