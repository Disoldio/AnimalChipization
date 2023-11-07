package com.example.animalchipization.controller;

import com.example.animalchipization.dto.*;
import com.example.animalchipization.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping("/{animalId}")
    public AnimalDTO getById(@PathVariable @Min(1) Long animalId){
        return animalService.getById(animalId);
    }

    @GetMapping("/search")
    public List<AnimalDTO> search(@Valid SearchAnimalDTO searchAnimalDTO){
        return animalService.search(searchAnimalDTO);
    }

    @PutMapping("/{animalId}")
    public AnimalDTO updateAnimal(@PathVariable @Min(1) Long animalId, @Valid @RequestBody AnimalDTO animalDTO){
        return animalService.updateAnimal(animalId, animalDTO);
    }

    @DeleteMapping("/{animalId}")
    public void deleteAnimal(@PathVariable @Min(1) Long animalId){
        animalService.deleteAnimal(animalId);
    }

}
