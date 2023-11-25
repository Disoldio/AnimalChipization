package com.example.animalchipization.dto;

import com.example.animalchipization.domain.Animal;
import com.example.animalchipization.domain.Location;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitedLocationDTO {
    @Min(1)
    @JsonProperty("id")
    @JsonAlias({"id", "visitedLocationPointId"})
    private Long id;
    private LocalDateTime dateTimeOfVisitLocationPoint;
    @Min(1)
    private Long locationPointId;

}
