package com.example.animalchipization.service;

import com.example.animalchipization.domain.Account;
import com.example.animalchipization.dto.AccountDTO;
import com.example.animalchipization.dto.SearchAccountDTO;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.repository.AccountRepository;
import com.example.animalchipization.repository.EntityRepository;
import com.example.animalchipization.util.CriteriaManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityRepository entityRepository;

    @Autowired
    private CriteriaManager criteriaManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountDTO createAccount(AccountDTO dto){
        Account account = new Account();
        modelMapper.map(dto, account);

        String password = passwordEncoder.encode(account.getPassword());
        account.setPassword(password);

        account = accountRepository.save(account);
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

        return accountDTO;
    }
    public List<AccountDTO> getAllAccount(){
        return null;
    }
    public AccountDTO getById(Long id){
        return accountRepository.findById(id)
                .map(type -> modelMapper.map(type, AccountDTO.class))
                .orElseThrow(NotFoundException::new);
    }

    public List<AccountDTO> search(SearchAccountDTO dto){
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", dto.getFirstName());
        map.put("lastName", dto.getLastName());
        map.put("email", dto.getEmail());
        CriteriaQuery<Account> accountCriteriaQuery = criteriaManager.buildCriteria(Account.class, map);

        List<Account> list = entityRepository.list(accountCriteriaQuery, dto.getFrom(), dto.getSize());
        List<AccountDTO> listDTO = list.stream().map(account -> modelMapper.map(account, AccountDTO.class)).toList();

        return listDTO;
    }

    public AccountDTO updateAccount(Long id, AccountDTO accountDTO){
        Account account = accountRepository.getById(id);

        account.setLastName(accountDTO.getLastName());
        account.setFirstName(accountDTO.getFirstName());
        account.setEmail(accountDTO.getEmail());
        account.setPassword(accountDTO.getPassword());

        Account saveAccount = accountRepository.save(account);
        AccountDTO dto = modelMapper.map(saveAccount, AccountDTO.class);
        return dto;
    }
}
