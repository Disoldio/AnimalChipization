package com.example.animalchipization.dto;

import com.example.animalchipization.domain.AnimalType;
import com.example.animalchipization.domain.Gender;
import com.example.animalchipization.domain.LifeStatus;
import com.example.animalchipization.domain.VisitedLocation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AnimalDTO {
    private Long id;
    private Float weight;
    private Float length;
    private Float height;
    private Gender gender;
    private LifeStatus lifeStatus;
    private LocalDateTime chippingDateTime;
    private Integer chipperId;
    private Long chippingLocationId;
    private LocalDateTime deathDateTime;
    private List<Long> animalTypes;
    private List<Long> visitedLocations;
}
