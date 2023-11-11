package com.example.animalchipization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalTypeDTO{

    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String type;
}
