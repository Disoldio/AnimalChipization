package com.example.animalchipization.controller;

import com.example.animalchipization.dto.AccountDTO;
import com.example.animalchipization.dto.AnimalTypeDTO;
import com.example.animalchipization.dto.LocationDTO;
import com.example.animalchipization.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/locations")
public class LocationsController {
    @Autowired
    private LocationService locationsService;

    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody @Valid LocationDTO dto){
        LocationDTO locationDTO = locationsService.createLocation(dto);

        return new ResponseEntity<>(locationDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{pointId}")
    public LocationDTO getById(@PathVariable @Min(1) Long pointId){
        return locationsService.getById(pointId);
    }

    @PutMapping("/{pointID}")
    public LocationDTO updatePoint(@PathVariable @Min(1) Long pointID, @Valid @RequestBody LocationDTO locationDTO){
        return locationsService.updatePoint(pointID, locationDTO);
    }

    @DeleteMapping("/{pointID}")
    public void deletePoint(@PathVariable @Min(1) Long pointID){
        locationsService.deletePoint(pointID);
    }
}
