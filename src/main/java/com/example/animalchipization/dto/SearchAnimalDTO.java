package com.example.animalchipization.dto;

import com.example.animalchipization.domain.Gender;
import com.example.animalchipization.domain.LifeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class SearchAnimalDTO {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @Min(1)
    private Integer chipperId;
    @Min(1)
    private Long chippingLocationId;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @Enumerated(value = EnumType.STRING)
    private LifeStatus lifeStatus;
    @Min(0)
    private Integer from = 0;
    @Min(1)
    private Integer size = 10;
}
