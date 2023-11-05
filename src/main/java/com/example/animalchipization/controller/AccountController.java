package com.example.animalchipization.controller;

import com.example.animalchipization.dto.AccountDTO;
import com.example.animalchipization.dto.SearchAccountDTO;
import com.example.animalchipization.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public AccountDTO getById(@PathVariable @Min(1) Long accountId){
        return accountService.getById(accountId);
    }

    @GetMapping("/search")
    public List<AccountDTO> search(@Valid SearchAccountDTO searchAccount){
        return accountService.search(searchAccount);
    }

    @PutMapping("/{accountId}")
    public AccountDTO updateAccount(@PathVariable @Min(1) Long accountId, @Valid @RequestBody AccountDTO accountDTO){
        return accountService.updateAccount(accountId, accountDTO);
    }
}
