package com.example.animalchipization.dto;

import com.example.animalchipization.domain.AnimalType;
import com.example.animalchipization.domain.Gender;
import com.example.animalchipization.domain.LifeStatus;
import com.example.animalchipization.domain.VisitedLocation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {
    private Long id;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Float weight;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Float length;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
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
    @NotNull
    @JsonProperty("animalTypes")
    private List<Long> animalTypesIds;
    private List<Long> visitedLocations;
}
