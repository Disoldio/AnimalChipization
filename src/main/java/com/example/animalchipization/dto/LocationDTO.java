package com.example.animalchipization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Long id;
    @NotNull
    @Min(-90)
    @Max(90)
    private Double latitude;
    @NotNull
    @Min(-180)
    @Max(180)
    private Double longitude;
}
