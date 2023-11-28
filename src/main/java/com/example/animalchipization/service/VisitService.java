package com.example.animalchipization.service;

import com.example.animalchipization.domain.Animal;
import com.example.animalchipization.domain.Location;
import com.example.animalchipization.domain.VisitedLocation;
import com.example.animalchipization.dto.VisitedLocationDTO;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.repository.AnimalRepository;
import com.example.animalchipization.repository.LocationRepository;
import com.example.animalchipization.repository.VisitedLocationsRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisitService {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private VisitedLocationsRepository visitedLocationsRepository;

    @Autowired
    private LocationRepository locationRepository;

    public List<VisitedLocationDTO> allVisitedLocation(Long id){
        return visitedLocationsRepository.findAllByAnimal(id).stream()
                .map(entity -> new VisitedLocationDTO(entity.getId(), entity.getVisitTime(), entity.getLocation().getId()))
                .toList();

    }

    public VisitedLocationDTO updateVisitedLocation(Long animalId, VisitedLocationDTO dto){
        VisitedLocation visitedLocation = visitedLocationsRepository.findById(dto.getId())
                .orElseThrow(NotFoundException::new);
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(NotFoundException::new);
        Location location = locationRepository.findById(dto.getLocationPointId())
                .orElseThrow(NotFoundException::new);
        Long chippingId = animal.getChippingLocationId().getId();
        List<VisitedLocation> sortedVisitedLocations = animal.getVisitedLocations().stream()
                .sorted((vl1, vl2) -> vl2.getId().compareTo(vl1.getId()))
                .toList();

        if(!sortedVisitedLocations.contains(visitedLocation)){
            throw new NotFoundException();
        }
        if(chippingId.equals(dto.getLocationPointId())){
            VisitedLocation firstVisitedLocation = sortedVisitedLocations.get(0);
            if(firstVisitedLocation.getId().equals(dto.getId())){
                throw new DataIntegrityViolationException("");
            }
        }
//        if(checkCollision(sortedVisitedLocations, visitedLocation, location)){
//            throw new DataIntegrityViolationException("Совпадает с предыдущим и/или со следующим");
//        }

        int index = sortedVisitedLocations.indexOf(visitedLocation);
        visitedLocation = sortedVisitedLocations.get(index);
        visitedLocation.setLocation(location);
        validateLocations(sortedVisitedLocations);
        VisitedLocation saveLocation = visitedLocationsRepository.save(visitedLocation);
        return new VisitedLocationDTO(saveLocation.getId(), saveLocation.getVisitTime(), saveLocation.getLocation().getId());
    }

    public void deleteVisitedLocation(Long animalId, Long visitedPointId){
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("AnimalId is not found " + animalId));

        VisitedLocation visitedLocation = visitedLocationsRepository.findById(visitedPointId)
                .orElseThrow(() -> new NotFoundException("VisitedPointId is not found " + visitedPointId));

        if(!animal.getVisitedLocations().contains(visitedLocation)){
            throw new NotFoundException("Animal " + animalId + " has no visit id:" + visitedPointId);
        }
        animal.getVisitedLocations().remove(visitedLocation);
        validateLocations(animal.getVisitedLocations());
        visitedLocationsRepository.deleteById(visitedPointId);
    }

    private void validateLocations(List<VisitedLocation> visitedLocationList){
        for (int i = 1; i < visitedLocationList.size()-2; i++) {
            Location prev = visitedLocationList.get(i-1).getLocation();
            Location current = visitedLocationList.get(i).getLocation();
            Location next = visitedLocationList.get(i+1).getLocation();

            if(current.equals(prev) || current.equals(next)){
                throw new DataIntegrityViolationException("Previous or next equals current");
            }
        }
    }

    private boolean checkCollision(List<VisitedLocation> sortedVisitedLocations, VisitedLocation visitedLocation, Location location){
        int i = sortedVisitedLocations.indexOf(visitedLocation);
        if(i > 0){
            if(sortedVisitedLocations.get(i-1).getLocation().equals(location)){
                return true;
            }
        }
        if(i < sortedVisitedLocations.size()-1){
            if(sortedVisitedLocations.get(i+1).getLocation().equals(location)){
                return true;
            }
        }
        return false;
    }
}
