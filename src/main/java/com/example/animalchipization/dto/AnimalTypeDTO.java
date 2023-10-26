package com.example.animalchipization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalTypeDTO{
    @Min(value = 1)
    private Long id;
    private String type;
}
