package com.example.animalchipization.controller;

import com.example.animalchipization.dto.AnimalDTO;
import com.example.animalchipization.dto.SearchAnimalDTO;
import com.example.animalchipization.dto.VisitedLocationDTO;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.logging.Handler;

@Validated
@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @Autowired
    private VisitService visitService;

    @PostMapping("/{animalId}/locations/{locationId}")
    public ResponseEntity<VisitedLocationDTO> addVisitedLocation(@PathVariable @Min(1) Long animalId, @PathVariable @Min(1) Long locationId){
        VisitedLocationDTO visitedLocationDTO = animalService.addVisitedLocation(animalId, locationId);

        return new ResponseEntity<>(visitedLocationDTO, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<AnimalDTO> createAnimal(@RequestBody @Valid AnimalDTO dto){
        AnimalDTO animalDTO = animalService.createAnimal(dto);

        return new ResponseEntity<>(animalDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{animalId}")
    public AnimalDTO getById(@PathVariable @Min(1) Long animalId){
        return animalService.getById(animalId);
    }

    @GetMapping("/{animalId}/locations")
    public ResponseEntity<List<VisitedLocationDTO>> allVisitedLocation(@PathVariable @Min(1) Long animalId){
        return  ResponseEntity.ok(visitService.allVisitedLocation(animalId));
    }

    @GetMapping("/search")
    public List<AnimalDTO> search(@Valid SearchAnimalDTO searchAnimalDTO){
        return animalService.search(searchAnimalDTO);
    }

    @PutMapping("/{animalId}")
    public AnimalDTO updateAnimal(@PathVariable @Min(1) Long animalId, @Valid @RequestBody AnimalDTO animalDTO){
        return animalService.updateAnimal(animalId, animalDTO);
    }

    @PutMapping("/{animalId}/locations")
    public VisitedLocationDTO updateVisitedLocation(@Valid @RequestBody VisitedLocationDTO visitedLocationDTO,@PathVariable @Min(1) Long animalId){
        return visitService.updateVisitedLocation(animalId, visitedLocationDTO);
    }

    @DeleteMapping("/{animalId}")
    public void deleteAnimal(@PathVariable @Min(1) Long animalId){
        animalService.deleteAnimal(animalId);
    }

    @DeleteMapping("/{animalId}/locations/{visitedPointId}")
    public void deleteVisitedLocation(@PathVariable @Min(1) Long animalId, @PathVariable @Min(1) Long visitedPointId ){
        visitService.deleteVisitedLocation(animalId, visitedPointId);
    }
}
