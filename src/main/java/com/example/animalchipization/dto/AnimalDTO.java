package com.example.animalchipization.dto;

import com.example.animalchipization.domain.AnimalType;
import com.example.animalchipization.domain.Gender;
import com.example.animalchipization.domain.LifeStatus;
import com.example.animalchipization.domain.VisitedLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {
    private Long id;
    @Min(1)
    @NotNull
    private Float weight;
    @Min(1)
    @NotNull
    private Float length;
    @Min(1)
    @NotNull
    private Float height;
    @NotNull
    private Gender gender;
    private LifeStatus lifeStatus;
    private LocalDateTime chippingDateTime;
    @Min(1)
    @NotNull
    private Integer chipperId;
    @Min(1)
    @NotNull
    private Long chippingLocationId;
    private LocalDateTime deathDateTime;
    @Min(1)
    @NotNull
    private List<Long> animalTypes;
    private List<Long> visitedLocations;
}
