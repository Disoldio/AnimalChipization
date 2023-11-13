package com.example.animalchipization.repository;

import com.example.animalchipization.domain.VisitedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitedLocationsRepository extends JpaRepository<VisitedLocation, Long> {
}
