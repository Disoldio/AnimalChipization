package com.example.animalchipization.dto;

import com.example.animalchipization.domain.Animal;
import com.example.animalchipization.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitedLocationDTO {

    private Long id;
    private LocalDateTime dateTimeOfVisitLocationPoint;
    private Long locationPointId;

}
