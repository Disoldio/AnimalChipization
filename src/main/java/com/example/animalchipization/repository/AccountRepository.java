package com.example.animalchipization.repository;

import com.example.animalchipization.domain.Account;
import com.example.animalchipization.domain.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getAccountByEmail(String email);
}
