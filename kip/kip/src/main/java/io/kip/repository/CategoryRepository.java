package io.kip.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.kip.model.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    
}


