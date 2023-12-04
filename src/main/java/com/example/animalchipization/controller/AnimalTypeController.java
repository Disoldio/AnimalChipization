package com.example.animalchipization.controller;

import com.example.animalchipization.dto.AnimalDTO;
import com.example.animalchipization.dto.ChangeTypeDTO;
import com.example.animalchipization.repository.AnimalRepository;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.service.AnimalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/animals/{animalId}/types")
public class AnimalTypeController {

    @Autowired
    private AnimalTypeService animalTypeService;

    @PostMapping("/{typeId}")
    public ResponseEntity<AnimalDTO> addTypeToAnimal(@PathVariable @Min(1) Long animalId, @PathVariable @Min(1) Long typeId){
        return new ResponseEntity(animalTypeService.addTypeToAnimal(animalId, typeId), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AnimalDTO> updateTypeToAnimal(@PathVariable @Min(1) Long animalId, @RequestBody @Valid ChangeTypeDTO changeTypeDTO){
        return ResponseEntity.ok(animalTypeService.updateTypeToAnimal(animalId, changeTypeDTO));
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<AnimalDTO> deleteTypeToAnimal(@PathVariable @Min(1) Long animalId, @PathVariable @Min(1) Long typeId){
        return ResponseEntity.ok(animalTypeService.deleteTypeToAnimal(animalId, typeId));
    }
}
