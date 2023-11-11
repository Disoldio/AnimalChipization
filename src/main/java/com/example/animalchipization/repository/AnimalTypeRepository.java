package com.example.animalchipization.repository;

import com.example.animalchipization.domain.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {
    Optional<AnimalType> findByType(String type);
}
