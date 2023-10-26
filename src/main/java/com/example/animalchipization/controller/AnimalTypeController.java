package com.example.animalchipization.controller;

import com.example.animalchipization.dto.AnimalTypeDTO;
import com.example.animalchipization.repository.AnimalTypeRepository;
import com.example.animalchipization.service.AnimalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/animals/types")
public class AnimalTypeController {
    @Autowired
    private AnimalTypeService animalTypeService;

    @PostMapping
    public AnimalTypeDTO createType(AnimalTypeDTO animalTypeDTO){
        AnimalTypeDTO type = animalTypeService.createType(animalTypeDTO.getType());

        return type;
    }
    @GetMapping("/{typeId}")
    public AnimalTypeDTO getById(@PathVariable @Min(value = 1) Long typeId){
        return animalTypeService.getById(typeId);
    }

}
