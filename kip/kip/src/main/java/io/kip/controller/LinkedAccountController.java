package io.kip.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kip.model.LinkedAccount;
import io.kip.repository.LinkedAccountRepository;

@RestController
@RequestMapping("/linked-accounts")
public class LinkedAccountController {

    private final LinkedAccountRepository linkedAccountRepository;

    public LinkedAccountController(LinkedAccountRepository linkedAccountRepository){
        this.linkedAccountRepository = linkedAccountRepository;
    }

    @GetMapping
    public List<LinkedAccount> getAllLinkedAccounts(){
        return linkedAccountRepository.findAll();
    }
    
}
