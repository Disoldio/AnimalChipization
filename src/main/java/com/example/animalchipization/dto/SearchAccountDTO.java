package com.example.animalchipization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class SearchAccountDTO {
    private String firstName;
    private String lastName;
    private String email;
    @Min(0)
    private Integer from = 0;
    @Min(1)
    private Integer size = 10;
}
