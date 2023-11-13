package com.example.animalchipization.dto;

import com.example.animalchipization.domain.AnimalType;
import com.example.animalchipization.domain.Gender;
import com.example.animalchipization.domain.LifeStatus;
import com.example.animalchipization.domain.VisitedLocation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
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
    @ManyToOne
    private Integer chipperId;
    @Min(1)
    @NotNull
    private Long chippingLocationId;
    private LocalDateTime deathDateTime;
    @NotNull
    @NotEmpty
    @JsonProperty("animalTypes")
    private List<@Min(1) Long> animalTypesIds;
    @JsonProperty("visitedLocations")
    private List<Long> visitedLocationsIsd;
}
