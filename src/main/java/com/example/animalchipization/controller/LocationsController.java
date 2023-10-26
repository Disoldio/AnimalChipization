package com.example.animalchipization.controller;

import com.example.animalchipization.dto.LocationDTO;
import com.example.animalchipization.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/locations")
public class LocationsController {
    @Autowired
    private LocationService locationsService;

    @GetMapping("/{pointId}")
    public LocationDTO getById(@PathVariable @Min(1) Long pointId){
        return locationsService.getById(pointId);
    }
}
