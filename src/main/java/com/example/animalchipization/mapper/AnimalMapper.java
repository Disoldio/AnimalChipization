package com.example.animalchipization.mapper;

import com.example.animalchipization.domain.Animal;
import com.example.animalchipization.dto.AnimalDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AnimalMapper implements Mapper<Animal, AnimalDTO>{

    @Override
    public AnimalDTO toDto(Animal animal) {
        AnimalDTO animalDTO = new AnimalDTO(
                animal.getId(),
                animal.getWeight(),
                animal.getLength(),
                animal.getHeight(),
                animal.getGender(),
                animal.getLifeStatus(),
                animal.getChippingDateTime(),
                Optional.ofNullable(animal.getChipper())
                        .map(chipper -> chipper.getId())
                        .orElse(null),
                Optional.ofNullable(animal.getChippingLocation())
                        .map(visitLoc -> visitLoc.getId())
                        .orElse(null),
                animal.getDeathDateTime(),
                animal.getTypes().stream()
                        .map(type -> type.getId())
                        .toList(),
                animal.getVisitedLocations().stream()
                        .map(visitedLocation -> visitedLocation.getId())
                        .toList());
        return animalDTO;
    }
}
