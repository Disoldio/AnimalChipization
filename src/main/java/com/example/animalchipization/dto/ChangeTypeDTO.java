package com.example.animalchipization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTypeDTO {
    @Min(1)
    @NotNull
    private Long oldTypeId;
    @Min(1)
    @NotNull
    private Long newTypeId;
}
