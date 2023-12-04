package com.example.animalchipization.controller;

import com.example.animalchipization.dto.TypeDTO;
import com.example.animalchipization.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/animals/types")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @PostMapping
    public ResponseEntity<TypeDTO> createType(@RequestBody @Valid TypeDTO typeDTO){
        TypeDTO type = typeService.createType(typeDTO);

        return new ResponseEntity<>(type, HttpStatus.CREATED);
    }
    @GetMapping("/{typeId}")
    public TypeDTO getById(@PathVariable @Min(1) Long typeId){
        return typeService.getById(typeId);
    }

    @PutMapping("/{typeId}")
    public TypeDTO updateAnimal(@PathVariable @Min(1) Long typeId, @Valid @RequestBody TypeDTO typeDTO){
        return typeService.updateAnimalType(typeId, typeDTO);
    }

    @DeleteMapping("/{typeId}")
    public void deleteAnimal(@PathVariable @Min(1) Long typeId){
        typeService.deleteAnimalType(typeId);
    }

}
