package com.example.animalchipization.controller;


import com.example.animalchipization.dto.AccountDTO;
import com.example.animalchipization.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private AccountService accountService;
    @PostMapping
    public AccountDTO registrationAccount(AccountDTO accountDTO){
        AccountDTO account = accountService.createAccount(accountDTO);

        return account;
    }
}
