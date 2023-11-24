package com.example.animalchipization.repository;

import com.example.animalchipization.domain.VisitedLocation;
import com.example.animalchipization.dto.VisitedLocationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitedLocationsRepository extends JpaRepository<VisitedLocation, Long> {
    @Query("select v from VisitedLocation v where v.animal.id = :animalId")
    public List<VisitedLocation> findAllByAnimal(Long animalId);
}
