package com.example.animalchipization.service;

import com.example.animalchipization.domain.Account;
import com.example.animalchipization.dto.AccountDTO;
import com.example.animalchipization.dto.SearchAccountDTO;
import com.example.animalchipization.exception.AlreadyExistException;
import com.example.animalchipization.exception.InaccessibleEntityException;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.repository.AccountRepository;
import com.example.animalchipization.repository.EntityRepository;
import com.example.animalchipization.util.CriteriaManager;
import com.example.animalchipization.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        try {
            account = accountRepository.save(account);
        } catch (Exception e) {
            throw new AlreadyExistException();
        }
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
        SecurityUtil.getCurrentUserEmail().ifPresent(email -> {
            Account currentAccount = accountRepository.getAccountByEmail(email);
            if(!id.equals(currentAccount.getId())){
                throw new InaccessibleEntityException();
            }
        });

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new InaccessibleEntityException());

        account.setLastName(accountDTO.getLastName());
        account.setFirstName(accountDTO.getFirstName());
        account.setEmail(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));

        Account saveAccount = accountRepository.save(account);
        AccountDTO dto = modelMapper.map(saveAccount, AccountDTO.class);
        return dto;
    }

    public void deleteAccount(Long id){
        String currentUserName = new String();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new InaccessibleEntityException());

        if(!currentUserName.equals(account.getEmail())){
            throw new InaccessibleEntityException();
        }

        accountRepository.delete(account);
    }
}
