package io.kip.model;


import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String email;
    private String passwordHash;
    private String fullName;
    private Instant createAt;

    public User() {}

    public User(UUID id, String email, String passwordHash, String fullName, Instant createAt){
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.createAt = createAt;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail(){
        return email;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public String getFullName(){
        return fullName;
    }
    public Instant getCreateAt(){
        return createAt;
    }

}
