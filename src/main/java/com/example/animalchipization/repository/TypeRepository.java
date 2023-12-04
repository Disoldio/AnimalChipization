package com.example.animalchipization.repository;

import com.example.animalchipization.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Optional<Type> findByType(String type);
    List<Type> findAllByIdIn(List<Long> ids);
}
